package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public ArrayList<User> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM  users");
        ArrayList<User> users = new ArrayList<>();

        while(sqlRowSet.next()) {
            users.add(new User(sqlRowSet.getInt("id"), sqlRowSet.getString("username"), sqlRowSet.getString("password"), sqlRowSet.getBoolean("admin")));
        }

        return users;
    }

    public User login(String username, String password) {
        ArrayList<User> users = readAll();
        for(User u : users) {
            if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }
}
