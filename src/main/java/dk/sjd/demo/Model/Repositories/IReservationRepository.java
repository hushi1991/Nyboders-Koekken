package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Reservation;
import dk.sjd.demo.Model.Entities.Shift;

import java.util.ArrayList;


public interface IReservationRepository extends ICrudRepository {

public void create (Reservation reservation);

public void delete (int id);

public Reservation login(String phone);

public ArrayList<Reservation> readAllPhone(String phone);

public Reservation readSpecific(int id);

    public void updateReservation(Reservation reservation);
}
