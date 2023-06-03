package ca.sheridancollege.musleho.ShineTennis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.musleho.ShineTennis.bean.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
@Configuration
public class UserRepo {
    private NamedParameterJdbcTemplate jdbc;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public void createUser(User user) throws DuplicateKeyException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "INSERT INTO sec_user (username, encryptedPassword, firstName, lastName, email, phone, homeAddress, enabled) " +
                       "VALUES (:username, :encPassword, :firstName, :lastName, :email, :phone, :homeAddress, 1);";
        params.addValue("username", user.getUsername());
        params.addValue("encPassword", encoder().encode(user.getEncPassword()));
        params.addValue("firstName", user.getFirstName());
        params.addValue("lastName", user.getLastName());
        params.addValue("email", user.getEmail());
        params.addValue("phone", user.getPhone());
        params.addValue("homeAddress", user.getHomeAddress());
        jdbc.update(query, params);
    }

    public ArrayList<String> getAllGuests() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        ArrayList<String> usernames = new ArrayList<>();
        String query = "SELECT * FROM sec_user JOIN user_role ON sec_user.userid = user_role.userId " +
                       "WHERE user_role.roleId = 2 ORDER BY username";
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            usernames.add((String) row.get("username"));
        }
        return usernames;
    }

    public User getUserById(UUID id) {
        ArrayList<User> users = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user WHERE userId=:id";
        params.addValue("id", id.toString());
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            User user = new User();
            user.setUserId(UUID.fromString((String) (row.get("userid"))));
            user.setUsername((String) (row.get("username")));
            user.setEncPassword((String) (row.get("encryptedPassword")));
            user.setFirstName((String) (row.get("firstName")));
            user.setLastName((String) (row.get("lastName")));
            user.setEmail((String) (row.get("email")));
            user.setPhone((String) (row.get("phone")));
            user.setHomeAddress((String) (row.get("homeAddress")));
            users.add(user);
        }
        return (users.size() > 0) ? users.get(0) : null;
    }

    public User getUserByName(String name) {
        ArrayList<User> users = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user WHERE username=:name";
        params.addValue("name", name);
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            User user = new User();
            user.setUserId(UUID.fromString((String) (row.get("userid"))));
            user.setUsername((String) (row.get("username")));
            user.setEncPassword((String) (row.get("encryptedPassword")));
            user.setFirstName((String) (row.get("firstName")));
            user.setLastName((String) (row.get("lastName")));
            user.setEmail((String) (row.get("email")));
            user.setPhone((String) (row.get("phone")));
            user.setHomeAddress((String) (row.get("homeAddress")));
            users.add(user);
        }
        return (users.size() > 0) ? users.get(0) : null;
    }

    public Long getRoleByName(String name) {
        ArrayList<Long> roles = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT roleid FROM sec_role WHERE rolename=:name";
        params.addValue("name", name);
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            roles.add((Long) row.get("roleId"));
        }
        return (roles.size() > 0) ? roles.get(0) : null;
    }

    public ArrayList<String> getRolesByUserId(UUID id) {
        ArrayList<String> roles = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT roleName FROM sec_role WHERE roleId in (SELECT roleId FROM user_role WHERE userId=:id);";
        params.addValue("id", id.toString());
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            roles.add((String) row.get("roleName"));
        }
        return roles;
    }

    public void giveRoleToUser(String username, String rawRoleName) {
        UUID userId = getUserByName(username).getUserId();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String roleName = "ROLE_" + rawRoleName;
        long roleId = getRoleByName(roleName);
        String query = "INSERT INTO user_role (userid, roleid) VALUES (:uid, :rid)";
        params.addValue("uid", userId.toString());
        params.addValue("rid", roleId);
        jdbc.update(query, params);
    }

    public ArrayList<String> getAllRoleNames() {
        ArrayList<String> roles = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "SELECT rolename FROM sec_role";
        List<Map<String, Object>> table = jdbc.queryForList(query, params);
        for (Map<String, Object> row : table) {
            roles.add((String) row.get("roleName"));
        }
        return roles;
    }
    
}
