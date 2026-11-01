package tn.esprit.autoloc.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController extends CrudController<Paiement, IPaiementRepository> {
    public PaiementController(IPaiementRepository repository) { super(repository); }
}
