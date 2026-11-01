package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

@RestController
@RequestMapping("/api/employes")
public class EmployeController extends CrudController<Employe, IEmployeRepository> {
    public EmployeController(IEmployeRepository repository) { super(repository); }
}
