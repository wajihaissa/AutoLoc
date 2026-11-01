# Atelier 3 — Spring Data JPA repositories

## Choix de l'interface

Les neuf interfaces étendent `JpaRepository<Entite, Long>`. C'est l'interface retenue par
l'atelier pour un accès JPA complet : CRUD, résultats de type `List`, tri, pagination et
opérations de synchronisation (`flush`).

| Interface | Entité | Justification |
| --- | --- | --- |
| `IAgenceRepository` | `Agence` | CRUD complet et listes pour les recherches par ville et nom. |
| `IEmployeRepository` | `Employe` | CRUD complet et listes pour les recherches par rôle et agence. |
| `IVehiculeRepository` | `Vehicule` | CRUD complet et listes pour les recherches par immatriculation, statut, catégorie et agence. |
| `IEquipementRepository` | `Equipement` | CRUD complet et liste des équipements par libellé. |
| `IClientRepository` | `Client` | CRUD complet et recherches par courriel, permis ou nom. |
| `IReservationRepository` | `Reservation` | CRUD complet et recherches par client, véhicule, statut et période. |
| `IContratRepository` | `Contrat` | CRUD complet; recherche par réservation et état de validation. |
| `IPaiementRepository` | `Paiement` | CRUD et consultation des paiements; la création ou le retrait métier reste associé au contrat. |
| `IMaintenanceRepository` | `Maintenance` | CRUD complet et recherches par véhicule ou période. |

`findById` retourne un `Optional`. `save` crée une entité si son identifiant est nul et
sert aussi à mettre à jour une entité existante; il faut utiliser l'objet retourné par
`save`. `deleteById` ne signale pas d'erreur lorsque l'identifiant n'existe pas. Les
suppressions JPA ordinaires passent par le contexte de persistance; les suppressions en lot
peuvent contourner les cascades et `orphanRemoval`.

## Analyse de qualité

Le plugin SonarQube for IDE n'est pas installé ou lancé dans cet environnement. Les points
ci-dessous ont été relevés lors de l'adaptation du code; ils ne sont pas présentés comme un
rapport d'analyse Sonar exécuté.

| Anomalie relevée | Règle / explication | Correction apportée |
| --- | --- | --- |
| Les interfaces de dépôt n'avaient pas le préfixe `I` ni le suffixe `Repository`; le package était `Repositories`. | Convention de nommage et d'organisation imposée par l'atelier. | Création des neuf interfaces `I…Repository` dans `tn.esprit.autoloc.repository`; suppression des anciennes interfaces après migration des références. |
| `VehiculeController` importait des types non utilisés. | Import inutilisé (règle Sonar Java `java:S1128`, si cette règle est activée). | Suppression des imports inutiles en conservant uniquement les annotations et types utilisés. |
| Plusieurs dépôts annotaient leurs interfaces Spring Data avec `@Repository`. | Redondant : Spring Data détecte et implémente automatiquement les interfaces de dépôt. | L'annotation n'est pas reprise dans les nouvelles interfaces. |

## Périmètre appliqué

Les entités existantes sont dans `tn.esprit.autoloc.Entities`; les dépôts les importent
depuis ce package pour éviter une migration générale du modèle qui dépasse la couche
Repository de cet atelier. Les propriétés `spring.jpa.show-sql` et
`logging.level.org.hibernate.SQL` sont activées pour faciliter l'observation des requêtes.
