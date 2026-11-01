package tn.esprit.autoloc.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
    Optional<Contrat> findByReservationIdReservation(Long reservationId);

    List<Contrat> findByValide(boolean valide);
}
