package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ShiftRepository implements IShiftRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public ArrayList<Shift> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts");
        ArrayList<Shift> shifts = new ArrayList<>();

        while(sqlRowSet.next()) {
            shifts.add(new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("shiftStart"), sqlRowSet.getString("shiftEnd"), sqlRowSet.getInt("hours")));
        }

        return shifts;
    }

    @Override
    public ArrayList<Shift> read(String name) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts WHERE name ='" + name + "'");
        ArrayList<Shift> shifts = new ArrayList<>();

        while(sqlRowSet.next()) {
            shifts.add(new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("shiftStart"), sqlRowSet.getString("shiftEnd"), sqlRowSet.getInt("hours")));
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
            return new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("shiftStart"), sqlRowSet.getString("shiftEnd"), sqlRowSet.getInt("hours"));
        }
        return new Shift();
    }

    @Override
    public void updateShift(Shift shift){

        jdbc.update("UPDATE shifts set name = '"+ shift.getName() +"', date = '"+ shift.getDate() +"', shiftStart = '"+ shift.getShiftStart() +"', shiftEnd = '"+ shift.getShiftEnd() +"', hours = '"+ shift.getHours() +"' WHERE id =" + shift.getId() +"");
    }

    @Override
    public void create(Shift s) {
        jdbc.update("INSERT INTO shifts (name, date, shiftStart, shiftEnd) VALUES('" + s.getName() +"', '"+ s.getDate() +"', '"+ s.getShiftStart() +"', '"+ s.getShiftEnd() +"')");
    }


}
