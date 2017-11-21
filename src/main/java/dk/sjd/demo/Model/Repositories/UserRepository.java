package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository implements ICrudRepository {

    @Override
    public ArrayList<User> readAll() {
        ArrayList<User> users = new ArrayList<>();

        return users;
    }



}
