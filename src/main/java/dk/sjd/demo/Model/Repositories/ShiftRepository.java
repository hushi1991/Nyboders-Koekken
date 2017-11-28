package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class ShiftRepository implements IShiftRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public ArrayList<Shift> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts");
        ArrayList<Shift> shifts = new ArrayList<>();

        while(sqlRowSet.next()) {
            shifts.add(new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date"), sqlRowSet.getTime("shiftStart"), sqlRowSet.getTime("shiftEnd"), sqlRowSet.getInt("hours")));
        }

        return shifts;
    }

    @Override
    public ArrayList<Shift> read(String name) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts WHERE name ='" + name + "'");
        ArrayList<Shift> shifts = new ArrayList<>();

        while(sqlRowSet.next()) {
            shifts.add(new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date"), sqlRowSet.getTime("shiftStart"), sqlRowSet.getTime("shiftEnd"), sqlRowSet.getInt("hours")));
        }

        return shifts;
    }

    @Override
    public void delete(int id) {

        jdbc.update("DELETE FROM shifts WHERE id=" + id + "");
    }

    @Override
    public Shift readSpecific(int id) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts WHERE id =" + id + "");

        if (sqlRowSet.next()){
            return new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date"), sqlRowSet.getTime("shiftStart"), sqlRowSet.getTime("shiftEnd"), sqlRowSet.getInt("hours"));
        }
        return new Shift();
    }

    @Override
    public Shift updateShift(Shift shift){

        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts WHERE id = '" +  shift.getId()  + "'");

        if(sqlRowSet.next()) {
            return new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date"), sqlRowSet.getTime("shiftStart"), sqlRowSet.getTime("shiftEnd"), sqlRowSet.getInt("hours"));
        }
        return null;
    }

}
