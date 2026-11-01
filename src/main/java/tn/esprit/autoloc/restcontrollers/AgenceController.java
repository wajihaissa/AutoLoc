package tn.esprit.autoloc.restcontrollers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.Entities.Agence;
import tn.esprit.autoloc.Service.IAgence;

@RestController
@RequestMapping("/agences")
public class AgenceController {
    private final IAgence service;
    public AgenceController(IAgence service) { this.service = service; }

    @PostMapping("/Add")
    public ResponseEntity<Agence> create(@RequestBody Agence agence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.ajouterAgence(agence));
    }
    @GetMapping("/getAll")
    public List<Agence> getAll() { return service.obtenirToutesLesAgences(); }
    @GetMapping("/Get/{id}")
    public ResponseEntity<Agence> get(@PathVariable Long id) {
        return service.obtenirAgence(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Agence> update(@PathVariable Long id, @RequestBody Agence agence) {
        if (service.obtenirAgence(id).isEmpty()) return ResponseEntity.notFound().build();
        agence.setIdAgence(id);
        return ResponseEntity.ok(service.modifierAgence(agence));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.obtenirAgence(id).isEmpty()) return ResponseEntity.notFound().build();
        service.supprimerAgence(id);
        return ResponseEntity.noContent().build();
    }
}
