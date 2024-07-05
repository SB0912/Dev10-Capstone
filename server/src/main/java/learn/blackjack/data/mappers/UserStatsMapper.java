package learn.blackjack.data.mappers;

import learn.blackjack.model.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatsMapper implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser result = new AppUser();

        result.setAppUserId(rs.getInt("user_id"));
//        result.setEmail(rs.getString("email"));
        result.setTotalWins(rs.getInt("total_wins"));
        result.setTotalLosses(rs.getInt("total_losses"));
        result.setAccountValue(rs.getBigDecimal("currency_total"));
        result.setCurrencyId(rs.getInt("currency_id"));

        return result;
    }
}
