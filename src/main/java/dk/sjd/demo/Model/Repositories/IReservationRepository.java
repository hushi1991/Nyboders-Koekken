package dk.sjd.demo.Model.Repositories;

import dk.sjd.demo.Model.Entities.Reservation;


public interface IReservationRepository extends ICrudRepository {

public void create (Reservation reservation);

public void delete (String phone);

//public Reservation readSpecific(String phone);
}
