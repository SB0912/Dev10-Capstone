package learn.blackjack.domain;

import learn.blackjack.App;
import learn.blackjack.data.UserDatabaseRepo;
import learn.blackjack.data.UserRepo;
import learn.blackjack.data.UserStatsRepo;
import learn.blackjack.model.AppUser;
import learn.blackjack.model.OutcomeUserDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo repo;

    @Autowired
    UserStatsRepo statsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser matchingUser = repo.loadUserByUsername( username );

        //if we get back a null, there's not matching user
        //otherwise there is.

        if( matchingUser != null ) {
            return matchingUser;
        }

        throw new UsernameNotFoundException("Could not find user with name " + username);
    }

    public List<AppUser> getAllUsers(){
        return repo.findAllUsersAsAdmin();
    }


    public Result<AppUser> getSettingsByUsername (String username){
        Result<AppUser> privateLookupResult = new Result<>();

        if( username == null || username.isBlank() ){
            privateLookupResult.addErrorMessage("Missing username");
        }

        if( privateLookupResult.isSuccess() ){
            AppUser userSettings = repo.loadUserByUsername( username );

            privateLookupResult.setPayload(userSettings);
        }

        return privateLookupResult;
    }

    public Result<AppUser> createAsAnyone (AppUser appUser){
        Result<AppUser> result = validateFields(appUser);
        if (!result.isSuccess()){
            return result;
        }
        validateNoRepeats(appUser, result);
        if (!result.isSuccess()){
            return result;
        }


//        where, how should we sanitize this?
        appUser.setRoleId(1);
        appUser.setEnabled(true);
        appUser = repo.createAsAnyone(appUser);
        result.setPayload(appUser);

        return result;
    }

    public Result<AppUser> updateAsUser (AppUser appUser){

        Result<AppUser> result = validateFields(appUser);
        if (!result.isSuccess()) {
            return result;
        }

        if (!repo.updateAsUser(appUser)) {
            String msg = String.format("User ID: %s, not found", appUser.getAppUserId());
            result.addErrorMessage(msg);
        }

        return result;
    }

    public boolean deleteAsUser(int userId) {
        return repo.deleteAsUser(userId);
    }

    private Result<AppUser> validateFields (AppUser appUser){
        Result<AppUser> result = new Result<>();
        if (appUser == null){
            result.addErrorMessage("App User cannot be null");
            return result;
        }
        if (appUser.getUsername().isBlank()||appUser.getUsername().isEmpty()){
            result.addErrorMessage("Username must not be blank");
        }
        if (appUser.getPasshash().isEmpty()||appUser.getPasshash().isBlank()){
            result.addErrorMessage("Password did not register");
        }
        if (appUser.getFirstName().isBlank()){
            result.addErrorMessage("First Name is Required");
        }
        if (appUser.getLastName().isBlank()){
            result.addErrorMessage("Last name is required");
        }
        if (appUser.getEmail().isBlank()) {
            result.addErrorMessage("Email is required");
        }
        return  result;
    }

    private Result<AppUser> validateNoRepeats (AppUser appUser, Result<AppUser> result){
        List<AppUser> allUsers = repo.findAllUsersAsAdmin();
        if (allUsers == null){
            return result;
        }
        if (allUsers.stream().anyMatch(ap -> ap.getEmail().equals(appUser.getEmail()))){
            result.addErrorMessage("This email is already used in the database");
        }
        if (allUsers.stream().anyMatch(ap -> ap.getUsername().equals(appUser.getUsername()))){
            result.addErrorMessage("That username is already is use.");
        }
        return result;
    }


}
