package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController extends CrudController<Maintenance, IMaintenanceRepository> {
    public MaintenanceController(IMaintenanceRepository repository) { super(repository); }
}
