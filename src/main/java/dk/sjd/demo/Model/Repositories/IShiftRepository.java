package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Shift;

import java.util.ArrayList;

public interface IShiftRepository extends ICrudRepository {

    public ArrayList<Shift> read(String name);

    public void delete(int id);

    public void updateShift(Shift shift);

    public Shift readSpecific(int id);
}
