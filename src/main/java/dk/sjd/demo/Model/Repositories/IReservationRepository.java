package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Reservation;
import dk.sjd.demo.Model.Entities.Shift;

import java.util.ArrayList;


public interface IReservationRepository extends ICrudRepository {

public void create (Reservation reservation);

public void delete (String phone);

public Reservation login(String phone);

public ArrayList<Reservation> readAllPhone(String phone);

public Reservation readSpecific(String phone);
}
