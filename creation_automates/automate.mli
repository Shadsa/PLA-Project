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
  | Type of typeCellule*cellule
  | UneCaseType of typeCellule
  | NbInf of int
  | RatioInf of int
  | RessourcesPossedees of int

val arbreProche : cellule -> condition 
val caseAmi : cellule -> condition 
val ennemi : cellule -> condition 
val ennemiProche : cellule -> condition 
val libre : cellule -> condition 
val typeCase: typeCellule -> cellule -> condition 

type etat = int
type poids = int
type transition = etat * condition * action * etat * poids
type automate = transition list

val ecrireXML : string -> automate -> unit

val creerAutomate : automate list -> automate 

val appliquerSurListe : (etat->etat->automate) -> etat list -> etat list -> automate 

val appliquerSurListe : (etat->etat->automate) -> etat -> etat list -> automate

val appliquerSurListe : (etat->etat->automate) -> etat list -> etat -> automate 

val transition : poids -> condition -> action -> etat -> etat -> automate

val hostile : poids -> etat -> etat -> automate

val recolteur : poids -> etat -> etat -> automate
  
val createurNbRatio : poids -> int -> int -> int -> int -> etat -> etat -> automate 

val createur : poids -> int -> int -> etat -> etat -> automate 

val createurZ : poids -> etat -> etat -> automate
  
val errant : poids -> etat -> etat -> automate
  
val soigneur : poids -> etat -> etat -> automate

val fonceur : poids -> etat -> etat list -> automate

val chercheur : poids -> (cellule->condition) -> etat -> etat list -> etat list -> automate

