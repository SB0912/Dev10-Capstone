package learn.blackjack.domain;

import learn.blackjack.App;
import learn.blackjack.data.UserStatsRepo;
import learn.blackjack.data.UserRepo;
import learn.blackjack.model.AppUser;
import learn.blackjack.model.OutcomeUserDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserStatsService {

    @Autowired
    UserStatsRepo userStatsRepo;

    @Autowired
    UserRepo userRepo;


    public Result<AppUser> updateStats (String username, OutcomeUserDealer outcome){
        AppUser appUser = userRepo.loadUserByUsername(username);

        if (outcome.getCardListUsers().get(0).getDidWin()>0)
        {//Win
            appUser.setTotalWins(appUser.getTotalWins()+1);
            appUser.setTotalLosses(appUser.getTotalLosses());
        }
        else if (outcome.getCardListUsers().get(0).getDidWin()<0)
        {//Lose
            appUser.setTotalWins(appUser.getTotalWins());
            appUser.setTotalLosses(appUser.getTotalLosses()+1);
        }
        else if (outcome.getCardListUsers().get(0).getDidWin()==0)
        {//Draw
            appUser.setTotalWins(appUser.getTotalWins());
            appUser.setTotalLosses(appUser.getTotalLosses());
        }
        else {
            outcome.addMessage("something went wrong");
        }
        appUser.setAccountValue(appUser.getAccountValue()
                        .add(outcome.getCardListUsers().get(0).getBet()));

        Result<AppUser> result = validateFields(appUser);

        if (!result.isSuccess()){
            return result;
        }


        if (!userStatsRepo.updateUserStats(appUser)) {
            String msg = String.format("User ID: %s, not found", appUser.getAppUserId());
            result.addErrorMessage(msg);
        }



        return result;
    }


    public Result<AppUser> validateFields (AppUser appUser) {
        Result<AppUser> result = new Result<>();
        if (appUser == null){
            result.addErrorMessage("something went wrong with loading the app user.");
        }
        if (appUser.getAppUserId()==-1){
            result.addErrorMessage("something went wrong with loading the ID");
        }
        if (appUser.getTotalWins()==-1) {
            result.addErrorMessage("something went wrong with loading the total wins");
        }
        if (appUser.getTotalLosses()==-1) {
            result.addErrorMessage("something went wrong with loading the total losses");
        }
        if (appUser.getCurrencyId()==-1){
            result.addErrorMessage("something went wrong with loading the currency Id");
        }
        if (appUser.getAccountValue().equals(new BigDecimal(-1))) {
            result.addErrorMessage("something went wrong with loading the account balance");
        }
        result.setPayload(appUser);
        return result;
    }

}
