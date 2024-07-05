package learn.blackjack.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import learn.blackjack.model.AppUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser result = new AppUser();

        result.setAppUserId( rs.getInt("user_id"));
        result.setUsername( rs.getString("username"));
        result.setFirstName(rs.getString("first_name"));
        result.setLastName(rs.getString("last_name"));
        result.setEmail(rs.getString("email"));
        result.setEnabled(rs.getBoolean("enabled"));
        result.setPasshash(rs.getString("passhash"));
        result.setRoleId(rs.getInt("role_id"));
// Change this based on our model.


        return result;
    }
}
