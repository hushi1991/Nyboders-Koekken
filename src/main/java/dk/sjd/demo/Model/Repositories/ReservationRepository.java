package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Reservation;
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
                    , sqlRowSet.getInt("guest"), sqlRowSet.getDate("date"), sqlRowSet.getString("requets")));
        }

        return reservation;
    }


    public void create(Reservation reservation){
        jdbc.update("INSERT INTO reservation VALUES ('" + reservation.getName() + "') + ('" + reservation.getPhone() + "') + ('" + reservation.getGuest() + "') + ('" + reservation.getDate() + "') ('" + reservation.getRequest() + "') ");
    }

}

