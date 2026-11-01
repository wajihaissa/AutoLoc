package tn.esprit.autoloc.Service;

import java.util.List;
import java.util.Optional;
import tn.esprit.autoloc.Entities.Agence;

public interface IAgence {
    Agence ajouterAgence(Agence agence);
    Agence modifierAgence(Agence agence);
    Optional<Agence> obtenirAgence(Long id);
    List<Agence> obtenirToutesLesAgences();
    void supprimerAgence(Long id);
}
