# Atelier 4 — IoC, injection de dépendances et couche Service

## A. Modes d'injection comparés

| Critère | Constructeur (retenu) | Attribut (`@Autowired`, expérience temporaire) |
| --- | --- | --- |
| Champ `final` possible ? | Oui, la dépendance reste immuable après construction. | Non, le conteneur doit pouvoir affecter le champ. |
| Dépendance visible dans la signature ? | Oui, le constructeur l'expose. | Non, elle est cachée dans la classe. |
| Utilisation hors conteneur ? | Oui, à condition de fournir explicitement le repository. | Une instance créée avec `new` garde une dépendance `null`. |
| Signal SonarQube for IDE | Mode recommandé par l'atelier. | L'atelier indique la règle S6813 à relever dans l'IDE; l'analyse n'a pas été exécutée dans cet environnement. |

Toutes les implémentations utilisent `@RequiredArgsConstructor` avec un champ `final`
typé par l'interface du repository. Spring utilise le constructeur généré comme unique
constructeur d'injection.

## B. Services et dépendances

| Service | Dépendance injectée | Mode | Justification |
| --- | --- | --- | --- |
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | CRUD des agences; le nom est obligatoire. |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | CRUD des employés; nom, prénom et rôle sont obligatoires dans le modèle disponible. |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | CRUD des véhicules; refuse un tarif journalier négatif. |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | CRUD des équipements; le libellé est obligatoire. Le modèle ne comporte pas de quantité. |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | CRUD des clients; nom et e-mail sont obligatoires. |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | CRUD des réservations; vérifie l'ordre des dates lorsqu'elles sont fournies. |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | CRUD des contrats; refuse un montant total négatif et laisse la gestion du paiement au contrat. |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture seule: la création et la suppression du paiement passent par le contrat. |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | CRUD des maintenances; vérifie l'ordre des dates lorsqu'elles sont fournies. Le modèle ne comporte pas de coût. |

Les services CRUD refusent un identifiant fourni à la création. Une mise à jour charge
d'abord l'entité existante, copie ses champs modifiables et sauvegarde l'instance gérée.
Une absence produit `ResourceNotFoundException`; une règle métier invalide produit
`IllegalArgumentException`. L'API mappe ces exceptions vers HTTP 404 et 400.

## C. Diagnostic de messages du conteneur

1. **Aucun bean `IContratService`** : `ContratServiceImpl` peut ne pas être annoté avec
   `@Service`, ou se trouver hors de `tn.esprit.autoloc` et donc hors du scan de composants.
   Vérifier l'annotation et garder l'implémentation dans le sous-package `service`.
2. **Deux beans `Notificateur`** : annoter une implémentation avec `@Primary` pour en faire
   le choix par défaut, ou utiliser `@Qualifier("emailNotificateur")` sur le paramètre du
   constructeur consommateur pour choisir explicitement le bean.
3. **Cycle entre `ClientServiceImpl` et `ReservationServiceImpl`** : les constructeurs
   exigent chacun l'autre service avant de pouvoir créer le bean. Extraire la responsabilité
   commune dans un service métier distinct, ou déplacer la recherche relationnelle dans un
   repository, afin que les deux services ne se dépendent plus mutuellement.

## D. Analyse de qualité

Le plugin SonarQube for IDE n'a pas été exécuté ici. Les expériences et constats suivants
sont consignés comme éléments à confirmer dans le panneau de l'IDE, pas comme un rapport
Sonar produit par cette session.

| Anomalie / règle | Explication | Correction apportée |
| --- | --- | --- |
| Injection par attribut — S6813 selon l'indication de l'atelier | Dépendance cachée et champ non final; l'instance créée hors Spring serait incomplète. | Injection par constructeur générée par `@RequiredArgsConstructor` et champ `final`. |
| Exception générique — S112 | `RuntimeException` ne décrit pas l'absence d'un contrat et rend le traitement moins précis. | `ResourceNotFoundException` exprime la ressource et son identifiant manquants. |
| Contrôleurs dépendant directement des repositories (revue de code) | Cette dépendance court-circuite la couche métier et empêche les règles de service d'être appliquées aux requêtes REST. | Les contrôleurs délèguent maintenant aux interfaces `I…Service`; `PaiementController` n'expose que la lecture. |

## E. Questions de compréhension

1. **Où est le `new ContratServiceImpl` ? Qui l'exécute ?** Il n'est pas écrit dans le
   code applicatif. Le conteneur Spring détecte `@Service`, crée le bean et lui injecte le
   proxy `IContratRepository`.
2. **Pourquoi le contrôleur dépend-il de `IContratService` ?** Il dépend du contrat métier,
   ce qui évite de le lier à une implémentation et permet de la remplacer sans changer le
   contrôleur.
3. **Pourquoi un singleton doit-il rester sans état ?** La même instance sert plusieurs
   requêtes concurrentes. Un champ mutable `contratCourant` pourrait être remplacé par une
   autre requête avant la fin de la première et mélanger leurs données.
4. **Pourquoi charger l'entité existante avant `save` lors d'une mise à jour ?** La copie
   ciblée protège les champs et associations non modifiés. Pour `Contrat`, elle évite aussi
   d'écraser ou de détacher le paiement associé en copiant directement l'objet reçu.
5. **`@Component` / `@Bean`, `@Primary` / `@Qualifier`, et le service paiement ?**
   `@Component` déclare un bean sur une classe détectée par scan; `@Bean` déclare le résultat
   d'une méthode `@Configuration`. `@Primary` choisit un candidat par défaut; `@Qualifier`
   sélectionne un candidat précis au point d'injection. `IPaiementService` est en lecture
   seule parce que le contrat possède le paiement et sa suppression doit s'effectuer par
   cascade depuis le contrat.

## Adaptation au modèle du projet

Les entités du projet résident dans `tn.esprit.autoloc.Entities` et les repositories dans
`tn.esprit.autoloc.repository`; les nouveaux services suivent le package lowercase
`tn.esprit.autoloc.service`. Les règles mentionnant une quantité d'équipement ou un coût de
maintenance ne peuvent pas s'appliquer, car ces attributs n'existent pas. La suppression du
contrat cascade vers son paiement avec `orphanRemoval`.
