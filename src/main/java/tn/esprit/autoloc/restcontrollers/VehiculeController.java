package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController extends CrudController<Vehicule, IVehiculeRepository> {
    public VehiculeController(IVehiculeRepository repository) { super(repository); }
}
