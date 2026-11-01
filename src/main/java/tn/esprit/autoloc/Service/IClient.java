package tn.esprit.autoloc.Service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.Entities.Client;

public interface IClient {
    Client ajouterClient(Client client);
    Client modifierClient(Client client);
    Optional<Client> obtenirClient(Long id);
    List<Client> obtenirTousLesClients();
    void supprimerClient(Long id);
}
