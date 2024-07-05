package learn.blackjack.data.mappers;

import learn.blackjack.model.AppUser;
import learn.blackjack.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role result = new Role();

        result.setRole( rs.getString("name"));
        result.setRoleId( rs.getInt("role_id"));

        return result;
    }
}
