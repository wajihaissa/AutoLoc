package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Agence;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {
    List<Agence> findByVilleIgnoreCase(String ville);

    List<Agence> findByNomContainingIgnoreCase(String nom);
}
