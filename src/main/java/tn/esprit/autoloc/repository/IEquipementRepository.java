package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
    List<Equipement> findByLibelleContainingIgnoreCase(String libelle);
}
