package dk.sjd.demo.Model.Repositories;

import com.sun.org.apache.regexp.internal.RE;
import dk.sjd.demo.Model.Entities.Reservation;
import dk.sjd.demo.Model.Entities.Shift;
import dk.sjd.demo.Model.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ReservationRepository implements IReservationRepository {

    @Autowired
    private JdbcTemplate jdbc;


    @Override
    public ArrayList<Reservation> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM reservations");
        ArrayList<Reservation> reservation = new ArrayList<>();

        while (sqlRowSet.next()) {
            reservation.add(new Reservation(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getString("phone")
                    , sqlRowSet.getInt("guest"), sqlRowSet.getDate("date").toLocalDate(),sqlRowSet.getString("time"), sqlRowSet.getString("request")));
        }

        return reservation;
    }

    @Override
    public ArrayList<Reservation> readAllPhone(String phone) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM reservations WHERE phone =" + phone + "");
        ArrayList<Reservation> reservation = new ArrayList<>();

        while (sqlRowSet.next()) {
            reservation.add(new Reservation(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getString("phone")
                    , sqlRowSet.getInt("guest"), sqlRowSet.getDate("date").toLocalDate(),sqlRowSet.getString("time"), sqlRowSet.getString("request")));
        }

        return reservation;
    }


    @Override
    public Reservation readSpecific(int id) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM reservations WHERE id =" + id + "");

        if (sqlRowSet.next()){
            return new Reservation(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getString("phone"), sqlRowSet.getInt("guest"), sqlRowSet.getDate("date").toLocalDate(), sqlRowSet.getString("time"), sqlRowSet.getString("request"));
        }
        return new Reservation();
    }


    @Override
// opdeles af plus eller komma?
    public void create(Reservation reservation){
        jdbc.update("INSERT INTO reservations (name, phone, guest, date, time, request) VALUES('" + reservation.getName() +"', '"+ reservation.getPhone() +"', '"+ reservation.getGuest() +"', '"+ reservation.getDate() +"', '"+ reservation.getTime() +"', '"+ reservation.getRequest() +"')");
        // jdbc.update("INSERT INTO reservation(name,phone,guest,date,time,request) VALUES ('" + reservation.getName() + "') + ('" + reservation.getPhone() + "') + ('" + reservation.getGuest() + "') + ('" + reservation.getDate() + "') + ('" + reservation.getTime() + "') + ('" + reservation.getRequest() + "') ");
    }

    @Override
    public void delete(int id) {

        jdbc.update("DELETE FROM reservations WHERE id =" + id + "");
    }

    @Override
    public Reservation login(String phone) {
        ArrayList<Reservation> reservations = readAllPhone(phone);
        for(Reservation r : reservations) {
            if(r.getPhone().equals(phone)) {
                return r;
            }
        }

        return null;
    }

}

