package learn.blackjack.data;

import learn.blackjack.data.mappers.AppUserMapper;
import learn.blackjack.data.mappers.LeaderboardMapper;
import learn.blackjack.data.mappers.RoleMapper;
import learn.blackjack.data.mappers.UserStatsMapper;
import learn.blackjack.model.AppUser;
import learn.blackjack.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDatabaseRepo implements UserRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    UserStatsRepo userStatsRepo;

    @Override
    public AppUser loadUserByUsername(String username) {

        String sql = """
                select *
                from app_user     
                where username = ?;
                """;

        List<AppUser> matchingUsers = jdbcTemplate.query(
                sql,
                new AppUserMapper(),
                username);
        AppUser result = matchingUsers.stream().findFirst().orElse(null);

        String sqlRole = """
                select *
                from app_role
                where role_id = ?;
                """;
        List<Role> allRoles = jdbcTemplate.query(
                sqlRole,
                new RoleMapper(),
                result.getRoleId());
        result.setRole(allRoles.get(0).getRole());

        String sqlStats = """
                select user_id, total_wins, total_losses, currency_total, currency_id
                from user_stats
                where user_id = ?;
                """;
        AppUser allUserStats = jdbcTemplate.query(
                sqlStats,
                new UserStatsMapper(),
                result.getAppUserId()).stream().findFirst().orElse(null);

        result.setTotalWins(allUserStats.getTotalWins());
        result.setTotalLosses(allUserStats.getTotalLosses());
        result.setAccountValue(allUserStats.getAccountValue());
        result.setCurrencyId(allUserStats.getCurrencyId());


        if (matchingUsers.size() > 0 && allRoles.size() > 0) {
            //sky is falling case
//            if( matchingUsers.size() > 1 ){
//
//            }

            return result;
        }

        return null;
    }

    @Override
    public AppUser loginByUsername(String username) {

        String sql = """
                select *
                from app_user     
                where username = ?;
                """;

        List<AppUser> matchingUsers = jdbcTemplate.query(
                sql,
                new AppUserMapper(),
                username);
        AppUser result = matchingUsers.stream().findFirst().orElse(null);

        String sqlRole = """
                select *
                from app_role
                where role_id = ?;
                """;
        List<Role> allRoles = jdbcTemplate.query(
                sqlRole,
                new RoleMapper(),
                result.getRoleId());
        result.setRole(allRoles.get(0).getRole());

        if (matchingUsers.size() > 0 && allRoles.size() > 0) {
            //sky is falling case
//            if( matchingUsers.size() > 1 ){
//
//            }

            return result;
        }

        return null;
    }

    @Override
    public List<AppUser> findAllUsersAsAdmin() {

        String sqlStats = """
                select 
                 us.user_id,
                 au.email,
                 au.username, 
                 us.total_wins, 
                 us.total_losses, 
                 us.currency_total, 
                 us.currency_id 
                 from app_user au 
                 inner join user_stats us on us.user_id = au.user_id
                 order by us.total_wins desc;
                                """;
        List<AppUser> allUserStats = jdbcTemplate.query(
                sqlStats,
                new LeaderboardMapper());

        if (allUserStats.size() > 0) {

            return allUserStats;
        }
        return null;
    }

    @Override
    public AppUser createAsAnyone(AppUser appUser) {

        String sql = """
                insert into app_user (first_name, last_name, username, email, passhash, role_id, enabled)
                values (?,?,?,?,?,?,?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appUser.getFirstName());
            ps.setString(2, appUser.getLastName());
            ps.setString(3, appUser.getUsername());
            ps.setString(4, appUser.getEmail());
            ps.setString(5, appUser.getPasshash());
            ps.setInt(6, appUser.getRoleId());
            ps.setBoolean(7, appUser.isEnabled());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }

//        ADD A BRIDGE TABLE REPO CALL HERE

        appUser.setAppUserId(keyHolder.getKey().intValue());

        String sqlStats = """
                insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id)
                values (?,0,0,100,1);
                """;

        jdbcTemplate.update(sqlStats, appUser.getAppUserId());
        AppUser addedInfo = jdbcTemplate.query(
                """
                        select * from user_stats
                        where user_id = ?;
                           """,
                new UserStatsMapper(), appUser.getAppUserId()).stream().findFirst().orElse(null);

        appUser.setTotalWins(addedInfo.getTotalWins());
        appUser.setTotalLosses(addedInfo.getTotalLosses());
        appUser.setAccountValue(addedInfo.getAccountValue());
        appUser.setCurrencyId(addedInfo.getCurrencyId());

        return appUser;


    }

    @Override
    public boolean updateAsUser(AppUser appUser) {

        String sql = """
                update app_user set
                first_name = ?,
                last_name = ?,
                username = ?,
                email = ?,
                passhash = ?
                where user_id = ?;
                """;


        return jdbcTemplate.update(sql,
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getPasshash(),
                appUser.getAppUserId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteAsUser(int userId) {
        jdbcTemplate.update("delete from user_stats where user_id = ?;", userId);
        return jdbcTemplate.update("delete from app_user where user_id = ?;", userId) > 0;
    }
}