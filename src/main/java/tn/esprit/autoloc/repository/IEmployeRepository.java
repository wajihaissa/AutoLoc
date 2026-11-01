package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Employe;
import tn.esprit.autoloc.Entities.RoleEmploye;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByRole(RoleEmploye role);

    List<Employe> findByAgenceIdAgence(Long agenceId);
}
