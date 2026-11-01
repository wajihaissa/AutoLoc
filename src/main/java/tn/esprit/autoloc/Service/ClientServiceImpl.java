package tn.esprit.autoloc.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Entities.Client;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements IClient {
    private final IClientRepository clientRepo;

    public ClientServiceImpl(IClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public Client ajouterClient(Client client) {
        return clientRepo.save(client);
    }

    @Override
    public Client modifierClient(Client client) {
        return clientRepo.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> obtenirClient(Long id) {
        return clientRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> obtenirTousLesClients() {
        return clientRepo.findAll();
    }

    @Override
    public void supprimerClient(Long id) {
        clientRepo.deleteById(id);
    }
}
