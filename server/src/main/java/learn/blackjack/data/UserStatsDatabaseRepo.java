package learn.blackjack.data;

import learn.blackjack.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;


@Repository
public class UserStatsDatabaseRepo implements UserStatsRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public AppUser initialize (AppUser appUser) {

        String sql = """
                insert into user_stats (user_id)
                values (?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, appUser.getAppUserId());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }

        return appUser;
    }

    @Override
    public boolean updateUserStats(AppUser appUser) {

        String sql = """
                update user_stats set
                total_wins = ?,
                total_losses = ?,
                currency_total = ?,
                currency_id = ?   
                where user_id = ?;
                """;


        return jdbcTemplate.update(sql,
                appUser.getTotalWins(),
                appUser.getTotalLosses(),
                appUser.getAccountValue(),
                appUser.getCurrencyId(),
                appUser.getAppUserId()) > 0;
    }

}
