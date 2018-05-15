package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.User;

import java.util.ArrayList;

public interface IUserRepository extends ICrudRepository {

    public User login(String username, String password);

}
