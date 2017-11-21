package dk.sjd.demo.Model.Repositories;

import java.util.ArrayList;

public interface ICrudRepository<T> {

    public ArrayList<T> readAll();
}
