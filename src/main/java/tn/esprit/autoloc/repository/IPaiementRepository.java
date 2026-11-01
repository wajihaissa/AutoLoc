package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.ModePaiement;
import tn.esprit.autoloc.Entities.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByContratIdContrat(Long contratId);

    List<Paiement> findByModePaiement(ModePaiement modePaiement);

    List<Paiement> findByDatePaiementBetween(LocalDate debut, LocalDate fin);
}
