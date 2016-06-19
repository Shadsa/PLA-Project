open TypesAutomate
open Constructeur
open Output

  
let zombie = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche [1;2;3;4] 0 0; createurZ 1 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0]

let ouvrier1 = creerAutomate [errant 1 0 0; chercheur 3 arbreProche [1;2;3;4] 0 0; createurNbRatio 10 0 100 5 25 0 0; createur 8 1 100 0 0; createur 8 2 120 0 0; createur 8 3 140 0 0; appliquerSurListe (recolteur 5) [0;1;2;3;4] 0]

let soldat1 = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche [1;2;3;4] 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0; appliquerSurListe (suiveur 100) [0;1;2;3;4] 0]

let batisseur1 = creerAutomate [errant 1 0 1; errant 1 1 0; reparateur 1 1 0; reparateur 1 0 1; constructeur 1 mur 120 1 0 ; [(0,OrdreDonne,ConstruireMur(N),1,100)] ; [(0,OrdreDonne,ConstruireMur(E),1,100)] ; [(0,OrdreDonne,ConstruireMur(S),1,100)] ; [(0,OrdreDonne,ConstruireMur(O),1,100)] ; [(1,OrdreDonne,ConstruireMur(N),0,100)] ; [(1,OrdreDonne,ConstruireMur(E),0,100)] ; [(1,OrdreDonne,ConstruireMur(S),0,100)] ; [(1,OrdreDonne,ConstruireMur(O),0,100)]]

let soigneur1 = creerAutomate [errant 1 0 1; errant 1 1 0; medecin 10 0 1; medecin 10 1 0; constructeur 1 piege 118 1 0]

let ouvrier2 = creerAutomate [errant 1 0 0; chercheur 3 arbreProche [1;2;3;4] 0 0; createurNbRatio 10 0 100 20 25 0 0; createur 8 1 100 0 0; createur 8 2 120 0 0; createur 8 3 140 0 0; appliquerSurListe (recolteur 5) [0;1;2;3;4] 0 ; appliquerSurListe (suiveur 100) [0;1;2;3;4] 0]

let soldat2 = creerAutomate [errant 1 0 0; chercheur 6 ennemiProche [1;2;3;4] 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0; appliquerSurListe (suiveur 100) [0;1;2;3;4] 0]

let batisseur2 = creerAutomate [errant 1 0 1; errant 1 1 0; reparateur 1 1 0; reparateur 1 0 1; constructeur 1 mur 128 1 0 ; [(0,OrdreDonne,ConstruireMur(N),1,100)] ; [(0,OrdreDonne,ConstruireMur(E),1,100)] ; [(0,OrdreDonne,ConstruireMur(S),1,100)] ; [(0,OrdreDonne,ConstruireMur(O),1,100)] ; [(1,OrdreDonne,ConstruireMur(N),0,100)] ; [(1,OrdreDonne,ConstruireMur(E),0,100)] ; [(1,OrdreDonne,ConstruireMur(S),0,100)] ; [(1,OrdreDonne,ConstruireMur(O),0,100)]]

let soigneur2 = creerAutomate [errant 1 0 1; errant 1 1 0; medecin 10 0 1; medecin 10 1 0; constructeur 1 piege 124 1 0; constructeur 1 piege 124 0 1]

let main =
  ecrireXML "Zombie.xml" zombie;
  ecrireXML "Ouvrier.xml" ouvrier1;
  ecrireXML "Soldat.xml" soldat1;
  ecrireXML "Batisseur.xml" batisseur1;
  ecrireXML "Soigneur.xml" soigneur1;
  ecrireXML "Ouvrier2.xml" ouvrier2;
  ecrireXML "Soldat2.xml" soldat2;
  ecrireXML "Batisseur2.xml" batisseur2;
  ecrireXML "Soigneur2.xml" soigneur2;

