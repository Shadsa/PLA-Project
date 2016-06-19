type cellule =
  | C
  | N | S | E | O

type typeCellule =
  | Arbre
  | Plaine
  | Caillou
  | Eau
  | Batiment

  
type attribut =
  | Direction of cellule
  | Type of typeCellule*cellule
  | TypeG of typeCellule
  | Quantite of int
  | Poids of int
  | Compose of String.t
  | ComposeUnaire of String.t
  | NbEtats of int
  | Unite of int
  | Rien

type action =
  | Attendre
  | AvancerJoueur
  | AvancerHasard
  | Combattre
  | CouperBois
  | Duel
  | DupliquerZombie
  | Soigner
  | Attaquer of cellule
  | Avancer of cellule
  | ConstruireMur of cellule
  | PoserPiege of cellule
  | Reparer of cellule
  | Creer of int
(*
  | ...
*)

type condition =
  | Vide
  | OrdreDonne
  | UneCaseLibre
  | UnAmi
  | UnEnnemi
  | ArbreProche of cellule
  | Ami of cellule
  | CaseAmi of cellule
  | Ennemi of cellule
  | EnnemiProche of cellule
  | Libre of cellule
  | Et of condition*condition
  | Ou of condition*condition
  | Non of condition
  | Type of typeCellule*cellule
  | UneCaseType of typeCellule
  | NbInf of int
  | RatioInf of int
  | RessourcesPossedees of int


type etat = int
type poids = int
type transition = etat * condition * action * etat * poids
type automate = transition list

