package tn.esprit.autoloc.restcontrollers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {
    private final IPaiementService service;

    public PaiementController(IPaiementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Paiement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
