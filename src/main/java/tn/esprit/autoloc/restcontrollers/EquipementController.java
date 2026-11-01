package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController extends CrudController<Equipement, IEquipementRepository> {
    public EquipementController(IEquipementRepository repository) { super(repository); }
}
