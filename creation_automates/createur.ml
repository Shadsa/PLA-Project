open Constructeur
open Output
  

let ouvrier = creerAutomate [errant 1 0 0; chercheur 3 arbreProche 0 [1;3;5;7] [2;4;6;8]; createurNbRatio 10 0 100 5 25 0 0; createur 8 1 100 0 0; appliquerSurListe (recolteur 5) [0;1;2;3;4;5;6;7;8] 0]

let zombie = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche 0 [1;3;5;7] [2;4;6;8]; createurZ 1 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4;5;6;7;8] 0]

let soldat = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche 0 [1;3;5;7] [2;4;6;8]; appliquerSurListe (hostile 6) [0;1;2;3;4;5;6;7;8] 0]


let main =
  ecrireXML "Ouvrier.xml" ouvrier;
  ecrireXML "Zombie.xml" zombie;
  ecrireXML "Soldat.xml" soldat
