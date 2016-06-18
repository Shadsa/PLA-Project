open TypesAutomate
open Constructeur
open Output
  

let ouvrier = creerAutomate [errant 1 0 0; chercheur 3 arbreProche [1;2;3;4] 0 0; createurNbRatio 10 0 100 5 25 0 0; createur 8 1 100 0 0; appliquerSurListe (recolteur 5) [0;1;2;3;4] 0]

let zombie = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche [1;2;3;4] 0 0; createurZ 1 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0]

let soldat = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche [1;2;3;4] 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0]

let batisseur = creerAutomate [errant 1 0 1; errant 1 1 0; reparateur 1 1 0; reparateur 1 0 1; constructeur mur 1 356 1 0]

let soigneur = creerAutomate [errant 1 0 1; errant 1 1 0; medecin 10 0 1; constructeur piege 1 354 1 0]

let main =
  ecrireXML "Ouvrier.xml" ouvrier;
  ecrireXML "Zombie.xml" zombie;
  ecrireXML "Soldat.xml" soldat;
  ecrireXML "Batisseur.xml" batisseur;
  ecrireXML "Soigneur.xml" soigneur
