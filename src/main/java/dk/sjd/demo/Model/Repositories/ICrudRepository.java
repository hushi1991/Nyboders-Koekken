package dk.sjd.demo.Model.Repositories;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ICrudRepository<T> {

    public ArrayList<T> readAll();

}