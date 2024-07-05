package learn.blackjack.data;

import learn.blackjack.data.mappers.CardMapper;
import learn.blackjack.data.mappers.RoleMapper;
import learn.blackjack.model.Card;
import learn.blackjack.model.Role;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDatabaseRepo implements RoleRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Role> findAll () {
        String sql = """
                select *
                 from app_role;
                """;

        List<Role> allRoles = jdbcTemplate.query(
                sql,
                new RoleMapper());

        if( allRoles.size() > 0 ){
            return allRoles;
        }
        return null;
    }
}
