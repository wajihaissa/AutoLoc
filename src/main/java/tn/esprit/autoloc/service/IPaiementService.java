package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.Entities.Paiement;

public interface IPaiementService {
    Paiement findById(Long id);

    List<Paiement> findAll();
}
