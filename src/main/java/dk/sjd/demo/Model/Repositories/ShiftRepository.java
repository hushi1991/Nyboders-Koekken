package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Employee;
import dk.sjd.demo.Model.Entities.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            shifts.add(new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("shiftStart"), sqlRowSet.getString("shiftEnd"), sqlRowSet.getDouble("hours")));
        }

        return shifts;
    }

    @Override
    public ArrayList<Shift> read(String name) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM shifts WHERE name ='" + name + "'");
        ArrayList<Shift> shifts = new ArrayList<>();

        while(sqlRowSet.next()) {
            shifts.add(new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("shiftStart"), sqlRowSet.getString("shiftEnd"), sqlRowSet.getDouble("hours")));
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
            return new Shift(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("shiftStart"), sqlRowSet.getString("shiftEnd"), sqlRowSet.getDouble("hours"));
        }
        return new Shift();
    }

    private double hoursCalculator(Shift s){

        String time1 = s.getShiftStart();
        String time2 = s.getShiftEnd();

        long difference;
        long diffmin = 0;
        long diffhour = 0;

        try {

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);

            difference = date2.getTime() - date1.getTime();
            diffmin = difference / (60 * 1000) % 60;
            diffhour = difference / (60 * 60 * 1000) % 24;
        } catch (Exception e) {
            System.out.println(e);
        }

        double d = (double) (long) diffhour;
        double dd = (double) (long) diffmin / 60;

        return(d + dd);
    }

    @Override
    public void updateShift(Shift shift){

        shift.setHours(hoursCalculator(shift));

        jdbc.update("UPDATE shifts set name = '"+ shift.getName() +"', date = '"+ shift.getDate() +"', shiftStart = '"+ shift.getShiftStart() +"', shiftEnd = '"+ shift.getShiftEnd() +"', hours = '"+ shift.getHours() +"' WHERE id =" + shift.getId() +"");
    }

    @Override
    public void create(Shift shift) {

        shift.setHours(hoursCalculator(shift));

        jdbc.update("INSERT INTO shifts (name, date, shiftStart, shiftEnd, hours) VALUES('" + shift.getName() +"', '"+ shift.getDate() +"', '"+ shift.getShiftStart() +"', '"+ shift.getShiftEnd() +"', '"+ shift.getHours() +"')");
    }
}
