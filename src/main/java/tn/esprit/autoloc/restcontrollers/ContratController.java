package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

@RestController
@RequestMapping("/api/contrats")
public class ContratController extends CrudController<Contrat, IContratRepository> {
    public ContratController(IContratRepository repository) { super(repository); }
}
