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
import tn.esprit.autoloc.Entities.Client;
import tn.esprit.autoloc.Service.IClient;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final IClient clientService;

    public ClientController(IClient clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> ajouterClient(@RequestBody Client client) {
        Client savedClient = clientService.ajouterClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping
    public List<Client> obtenirTousLesClients() {
        return clientService.obtenirTousLesClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> obtenirClient(@PathVariable Long id) {
        return clientService.obtenirClient(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(@PathVariable Long id, @RequestBody Client client) {
        if (clientService.obtenirClient(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        client.setIdClient(id);
        return ResponseEntity.ok(clientService.modifierClient(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable Long id) {
        if (clientService.obtenirClient(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clientService.supprimerClient(id);
        return ResponseEntity.noContent().build();
    }
}
