package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController extends CrudController<Reservation, IReservationRepository> {
    public ReservationController(IReservationRepository repository) { super(repository); }
}
