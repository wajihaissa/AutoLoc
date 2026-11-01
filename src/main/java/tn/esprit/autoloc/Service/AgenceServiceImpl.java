package tn.esprit.autoloc.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Entities.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@Transactional
public class AgenceServiceImpl implements IAgence {
    private final IAgenceRepository agenceRepo;

    public AgenceServiceImpl(IAgenceRepository agenceRepo) {
        this.agenceRepo = agenceRepo;
    }

    @Override
    public Agence ajouterAgence(Agence agence) {
        return agenceRepo.save(agence);
    }

    @Override
    public Agence modifierAgence(Agence agence) {
        return agenceRepo.save(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agence> obtenirAgence(Long id) {
        return agenceRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> obtenirToutesLesAgences() {
        return agenceRepo.findAll();
    }

    @Override
    public void supprimerAgence(Long id) {
        agenceRepo.deleteById(id);
    }
}
