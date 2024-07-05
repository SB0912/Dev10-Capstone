package learn.blackjack.data;

import learn.blackjack.model.AppUser;

public interface UserStatsRepo {
    AppUser initialize (AppUser appUser);
    boolean updateUserStats(AppUser appUser);
}
