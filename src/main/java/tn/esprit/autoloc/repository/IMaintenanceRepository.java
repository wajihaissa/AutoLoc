package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByVehiculeIdVehicule(Long vehiculeId);

    List<Maintenance> findByDateDebutBetween(LocalDate debut, LocalDate fin);
}
