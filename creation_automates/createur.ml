open TypesAutomate
open Constructeur
open Output

  
let zombie = creerAutomate [errant 1 0 0; chercheur 3 ennemiProche [1;2;3;4] 0 0; createurZ 1 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0]

let ouvrier1 = creerAutomate [errant 1 0 0; chercheur 3 arbreProche [1;2;3;4] 0 0; createurNbRatio 10 0 100 5 25 0 0; createur 8 1 100 0 0; createur 8 2 120 0 0; createur 8 3 140 0 0; appliquerSurListe (recolteur 5) [0;1;2;3;4] 0; appliquerSurListe (suiveur 6) [0;1;2;3;4] 0]

let soldat1 = creerAutomate [errant 1 0 0; chercheur 5 ennemiProche [1;2;3;4] 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0; appliquerSurListe (suiveur 4) [0;1;2;3;4] 0]

let batisseur1 = creerAutomate [errant 1 0 1; errant 1 1 0; reparateur 1 1 0; reparateur 1 0 1; constructeur 1 mur 112 1 0; appliquerSurListe (suiveur 100) [0;1;2;3;4] 0]

let soigneur1 = creerAutomate [errant 1 0 1; errant 1 1 0; medecin 10 0 1; medecin 10 1 0; constructeur 1 piege 112 1 0; [(0,Et(Libre(N),OrdreDonne),piege d,0,100)]; [(0,Et(Libre(N),OrdreDonne),piege N,0,100)]; [(0,Et(Libre(S),OrdreDonne),piege S,0,100)]; [(0,Et(Libre(O),OrdreDonne),piege O,0,100)]; [(1,Et(Libre(N),OrdreDonne),piege N,0,100)]; [(1,Et(Libre(E),OrdreDonne),piege E,0,100)]; [(1,Et(Libre(S),OrdreDonne),piege S,0,100)]; [(1,Et(Libre(O),OrdreDonne),piege O,0,100)]]

let ouvrier2 = creerAutomate [errant 1 0 0; chercheur 3 arbreProche [1;2;3;4] 0 0; createurNbRatio 10 0 100 20 25 0 0; createur 8 1 100 0 0; createur 8 2 120 0 0; createur 8 3 140 0 0; appliquerSurListe (recolteur 5) [0;1;2;3;4] 0 ; appliquerSurListe (suiveur 100) [0;1;2;3;4] 0]

let soldat2 = creerAutomate [errant 1 0 0; chercheur 5 ennemiProche [1;2;3;4] 0 0; appliquerSurListe (hostile 6) [0;1;2;3;4] 0; appliquerSurListe (suiveur 4) [0;1;2;3;4] 0]

let batisseur2 = creerAutomate [errant 1 0 1; errant 1 1 0; reparateur 1 1 0; reparateur 1 0 1; constructeur 1 mur 120 1 0 ; appliquerSurListe (suiveur 100) [0;1;2;3;4] 0]

let soigneur2 = creerAutomate [errant 1 0 1; errant 1 1 0; medecin 10 0 1; medecin 10 1 0; constructeur 1 piege 121 1 0; constructeur 1 piege 121 0 1; [(0,Et(Libre(N),OrdreDonne),piege d,0,100)]; [(0,Et(Libre(N),OrdreDonne),piege N,0,100)]; [(0,Et(Libre(S),OrdreDonne),piege S,0,100)]; [(0,Et(Libre(O),OrdreDonne),piege O,0,100)]; [(1,Et(Libre(N),OrdreDonne),piege N,0,100)]; [(1,Et(Libre(E),OrdreDonne),piege E,0,100)]; [(1,Et(Libre(S),OrdreDonne),piege S,0,100)]; [(1,Et(Libre(O),OrdreDonne),piege O,0,100)]]

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

