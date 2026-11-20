# Atelier 5 — Spring MVC, API REST et SpringDoc

## A. Endpoints

Tous les endpoints sont préfixés par le context path `/api`. Les codes 400 et 404 sont
produits par `GlobalExceptionHandler` à partir des exceptions métier.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
| --- | --- | --- | --- | --- |
| `AgenceController` | GET `/api/agences` | `IAgenceService.findAll` | 200 | — |
| `AgenceController` | GET `/api/agences/{id}` | `IAgenceService.findById` | 200 | 404 |
| `AgenceController` | POST `/api/agences` | `IAgenceService.create` | 201 | 400 |
| `AgenceController` | PUT `/api/agences/{id}` | `IAgenceService.update` | 200 | 400, 404 |
| `AgenceController` | DELETE `/api/agences/{id}` | `IAgenceService.deleteById` | 204 | 404 |
| `EmployeController` | GET `/api/employes` | `IEmployeService.findAll` | 200 | — |
| `EmployeController` | GET `/api/employes/{id}` | `IEmployeService.findById` | 200 | 404 |
| `EmployeController` | POST `/api/employes` | `IEmployeService.create` | 201 | 400 |
| `EmployeController` | PUT `/api/employes/{id}` | `IEmployeService.update` | 200 | 400, 404 |
| `EmployeController` | DELETE `/api/employes/{id}` | `IEmployeService.deleteById` | 204 | 404 |
| `VehiculeController` | GET `/api/vehicules` | `IVehiculeService.findAll` | 200 | — |
| `VehiculeController` | GET `/api/vehicules/{id}` | `IVehiculeService.findById` | 200 | 404 |
| `VehiculeController` | POST `/api/vehicules` | `IVehiculeService.create` | 201 | 400 |
| `VehiculeController` | PUT `/api/vehicules/{id}` | `IVehiculeService.update` | 200 | 400, 404 |
| `VehiculeController` | DELETE `/api/vehicules/{id}` | `IVehiculeService.deleteById` | 204 | 404 |
| `EquipementController` | GET `/api/equipements` | `IEquipementService.findAll` | 200 | — |
| `EquipementController` | GET `/api/equipements/{id}` | `IEquipementService.findById` | 200 | 404 |
| `EquipementController` | POST `/api/equipements` | `IEquipementService.create` | 201 | 400 |
| `EquipementController` | PUT `/api/equipements/{id}` | `IEquipementService.update` | 200 | 400, 404 |
| `EquipementController` | DELETE `/api/equipements/{id}` | `IEquipementService.deleteById` | 204 | 404 |
| `ClientController` | GET `/api/clients` | `IClientService.findAll` | 200 | — |
| `ClientController` | GET `/api/clients/{id}` | `IClientService.findById` | 200 | 404 |
| `ClientController` | POST `/api/clients` | `IClientService.create` | 201 | 400 |
| `ClientController` | PUT `/api/clients/{id}` | `IClientService.update` | 200 | 400, 404 |
| `ClientController` | DELETE `/api/clients/{id}` | `IClientService.deleteById` | 204 | 404 |
| `ReservationController` | GET `/api/reservations` | `IReservationService.findAll` | 200 | — |
| `ReservationController` | GET `/api/reservations/{id}` | `IReservationService.findById` | 200 | 404 |
| `ReservationController` | POST `/api/reservations` | `IReservationService.create` | 201 | 400 |
| `ReservationController` | PUT `/api/reservations/{id}` | `IReservationService.update` | 200 | 400, 404 |
| `ReservationController` | DELETE `/api/reservations/{id}` | `IReservationService.deleteById` | 204 | 404 |
| `ContratController` | GET `/api/contrats` | `IContratService.findAll` | 200 | — |
| `ContratController` | GET `/api/contrats/{id}` | `IContratService.findById` | 200 | 404 |
| `ContratController` | POST `/api/contrats` | `IContratService.create` | 201 | 400 |
| `ContratController` | PUT `/api/contrats/{id}` | `IContratService.update` | 200 | 400, 404 |
| `ContratController` | DELETE `/api/contrats/{id}` | `IContratService.deleteById` | 204 | 404 |
| `PaiementController` | GET `/api/paiements` | `IPaiementService.findAll` | 200 | — |
| `PaiementController` | GET `/api/paiements/{id}` | `IPaiementService.findById` | 200 | 404 |
| `MaintenanceController` | GET `/api/maintenances` | `IMaintenanceService.findAll` | 200 | — |
| `MaintenanceController` | GET `/api/maintenances/{id}` | `IMaintenanceService.findById` | 200 | 404 |
| `MaintenanceController` | POST `/api/maintenances` | `IMaintenanceService.create` | 201 | 400 |
| `MaintenanceController` | PUT `/api/maintenances/{id}` | `IMaintenanceService.update` | 200 | 400, 404 |
| `MaintenanceController` | DELETE `/api/maintenances/{id}` | `IMaintenanceService.deleteById` | 204 | 404 |

Un POST direct sur `/api/paiements` n'a pas de mapping et reçoit 405 Method Not Allowed.
Une suppression réussie ne renvoie pas de corps (204).

## B. Annotations Jackson

| Association | Annotation | Justification |
| --- | --- | --- |
| `Contrat.paiements` / `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` côté contrat et `@JsonBackReference` côté paiement | Sérialise les paiements à travers leur parent sans sérialiser en retour le contrat et créer une boucle. La désérialisation du parent conserve le lien géré par Jackson; le service rattache le contrat avant persistance. |
| `Agence.vehicules` et autres associations bidirectionnelles | `@JsonIgnoreProperties(..., allowSetters = true)` sur les entités concernées | Évite d'inclure les associations lazy dans les réponses pour cette API provisoire tout en permettant de les fournir dans les requêtes. `Reservation.client` n'utilise pas `@JsonBackReference`, donc l'association peut être reçue dans un POST. |

`@JsonBackReference` distingue un côté enfant qui pointe vers son parent et s'emploie avec
une référence gérée de même nom. `@JsonIgnore` ignore une propriété à la lecture et à
l'écriture par défaut; `@JsonIgnoreProperties(..., allowSetters = true)` la masque dans les
réponses tout en autorisant son affectation depuis les requêtes.

## C. Postman et Swagger

Postman envoie des requêtes réutilisables et vérifie les codes de réponse. La collection
`postman/AutoLoc.postman_collection.json` contient les opérations nominales et cas d'erreur
par ressource, dont le scénario de création puis suppression en cascade Contrat/Paiement.
L'environnement `postman/AutoLoc-local.postman_environment.json` définit
`http://localhost:8080/api`.

Swagger UI présente le contrat OpenAPI généré depuis les routes et annotations et permet
d'essayer une requête. Par exemple, `GET /api/contrats/999999` doit être 404 dans Postman;
Swagger documente aussi ce code sur l'opération « Consulter un contrat ».

## D. Qualité du code

SonarQube for IDE n'a pas été lancé dans cet environnement; les corrections ci-dessous sont
des constats de revue, pas un rapport Sonar exécuté.

| Anomalie relevée | Règle / explication | Correction |
| --- | --- | --- |
| Les anciennes routes Agence utilisaient `/Add`, `/getAll` et `/Get/{id}`. | Convention REST : les ressources sont nommées au pluriel et l'action est portée par le verbe HTTP. | Routes normalisées en `/agences` et `/agences/{id}` avec GET, POST, PUT et DELETE. |
| Les anciens contrôleurs retournaient des entités avec des associations bidirectionnelles/lazy. | Risque de récursion Jackson ou d'accès à un proxy Hibernate non sérialisable. | Référence gérée/enfant pour Contrat/Paiement et associations masquées dans les autres réponses avec `@JsonIgnoreProperties`. |
| Le contrôleur générique utilisait la réflexion pour trouver et modifier l'identifiant JPA. | La réflexion contournait la couche métier et rendait le code difficile à maintenir. | Chaque contrôleur appelle explicitement son interface de service; l'identifiant est géré dans `update(id, entite)`. |

## E. Questions de compréhension

1. `DispatcherServlet` reçoit les requêtes MVC. Il consulte les mappings enregistrés à partir
   des annotations de contrôleur et délègue au handler correspondant.
2. `ContratController` dépend de `IContratService` pour rester découplé de l'accès aux
   données et de l'implémentation concrète. Le service porte les règles métier.
3. POST renvoie 201 car il crée une ressource; DELETE renvoie 204 car la suppression réussie
   n'a pas de représentation à retourner. Une ressource absente est une situation métier
   attendue traduite en 404; une erreur non traitée serait une erreur serveur 500.
4. `PaiementController` ne propose que des GET car le paiement est créé et supprimé via son
   contrat. Un POST direct ne correspond à aucune route et donne 405.
5. Exposer les entités couple l'API au schéma JPA, peut divulguer des champs internes et
   crée des problèmes de récursion ou de chargement lazy. `Contrat.paiements` utilise la
   référence gérée parce que le paiement est composé par le contrat; `Agence.vehicules` est
   ignoré dans la réponse parce que l'agence n'est pas le mécanisme de composition de ces
   ressources. Des DTO remplaceront cette exposition directe plus tard.

## Limites de vérification

La collection Postman est préparée mais n'a pas été exécutée; les scénarios avec associations
présupposent des identifiants de référence existants dans la base locale. SonarQube for IDE
n'a pas été exécuté. L'application doit être lancée localement pour vérifier les routes et
Swagger UI sur `/api/swagger-ui.html` et `/api/v3/api-docs`.
