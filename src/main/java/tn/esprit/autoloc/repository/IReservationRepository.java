package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Reservation;
import tn.esprit.autoloc.Entities.StatutReservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientIdClient(Long clientId);

    List<Reservation> findByVehiculeIdVehicule(Long vehiculeId);

    List<Reservation> findByStatut(StatutReservation statut);

    List<Reservation> findByDateDebutBetween(LocalDate debut, LocalDate fin);
}
