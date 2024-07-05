package learn.blackjack.data;

import learn.blackjack.model.AppUser;

import java.util.List;

public interface UserRepo {

    AppUser loadUserByUsername(String username);
    AppUser loginByUsername(String username);
    AppUser createAsAnyone (AppUser appUser);

    boolean updateAsUser(AppUser appUser);
    boolean deleteAsUser(int userId);
    List<AppUser> findAllUsersAsAdmin ();
}
