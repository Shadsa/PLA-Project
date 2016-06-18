open TypesAutomate

val arbreProche : cellule -> condition
val caseAmi : cellule -> condition
val ennemi : cellule -> condition
val ennemiProche : cellule -> condition
val libre : cellule -> condition
val typeCase : typeCellule -> cellule -> condition

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

val fonceur : poids -> etat list -> etat -> etat -> automate

val chercheur : poids -> (cellule->condition) -> etat list -> etat -> etat  -> automate

