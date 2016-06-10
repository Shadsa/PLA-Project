
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
  | Type of typeCellule
  | Quantite of int
  | Poids of int
  | Compose of String.t
  | NbEtats of int
  | None

type action =
  | Attendre
  | Avancer of cellule
  | Attaquer of cellule
  | AvancerJoueur
  | Dupliquer of cellule
  | Raser
  | CouperBois
(*
  | ...
*)

type condition =
  | Vide
  | Et of condition*condition
  | Ennemi of cellule
  | Libre of cellule
  | OrdreDonne
  | Type of typeCellule
  | RessourcesPossedees of int
(*
  | ...
*)


type etat = int
type poids = int
type transition = etat * condition * action * etat * poids
type automate = transition list

let output = open_out "sortie.xml"

let rec output_tab (n : int) =
  if n>0 then
    begin
    output_string output "\t";
    output_tab (n-1)
    end

let output_stab (s : String.t) (p : int) =
  output_tab p;
  output_string output s;
  output_char output '\n'

let string_of_cellule (c : cellule) =
  match c with
   |N -> "N"
   |O -> "O"
   |S -> "S"
   |E -> "E"
   |C -> "C"

let string_of_type (t : typeCellule) =
  match t with
   | Arbre -> "Arbre"
   | Plaine -> "Plaine"
   | Caillou -> "Caillou"
   | Eau -> "Eau"
   | Batiment -> "Batiment"

let balise (s : String.t) (a : attribut) =
  "<"^s^(
  match a with
   | Direction(d) -> " direction=\""^(string_of_cellule d)^"\""
   | Type(t) -> " type=\""^(string_of_type t)^"\""
   | Quantite(i) -> " quantite=\""^(string_of_int i)^"\""
   | Poids(i) -> " poids=\""^(string_of_int i)^"\""
   | Compose(s) -> " compose=\""^s^"\""
   | NbEtats(i) -> " nbEtats=\""^(string_of_int i)^"\""
   |_ -> ""

  )^">"

let fbalise(s : String.t) =
  "</"^s^">"

  
  
let output_cour (e : etat) (p : int) =
  output_stab ((balise "etat" None)^(string_of_int e)^(fbalise "etat")) p

let output_suiv (e : etat) (p : int) =
  output_stab ((balise "suivant" None)^(string_of_int e)^(fbalise "suivant")) p


let rec output_cond (c : condition) (p : int) =
  let b = "condition" in
  match c with
   | Vide -> output_stab ((balise b None)^"Vide"^(fbalise b)) p
   | Et(c1,c2) ->
    begin
    output_stab (balise b (Compose("Et"))) p;
    output_cond c1 (p+1);
    output_cond c2 (p+1);
    output_stab (fbalise b) p
    end
   | Ennemi(cellule) -> output_stab ((balise b (Direction(cellule)))^"Ennemi"^(fbalise b)) p
   | Libre(cellule) -> output_stab ((balise b (Direction(cellule)))^"Libre"^(fbalise b)) p
   | OrdreDonne -> output_stab ((balise b None)^"OrdreDonne"^(fbalise b)) p
   | Type(typeCellule) -> output_stab ((balise b (Type(typeCellule)))^"Type"^(fbalise b)) p
   | RessourcesPossedees(quantite) -> output_stab ((balise b (Quantite(quantite)))^"RessourcesPossedees"^(fbalise b)) p
   |_ -> ()

let output_act (a : action) (p : int) =
  let b = "action" in
  match a with
   | Attendre -> output_stab ((balise b None)^"Attendre"^(fbalise b)) p
   | Avancer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Avancer"^(fbalise b)) p
   | Attaquer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Attaquer"^(fbalise b)) p
   | AvancerJoueur -> output_stab ((balise b None)^"AvancerJoueur"^(fbalise b)) p
   | Dupliquer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Dupliquer"^(fbalise b)) p
   | Raser -> output_stab ((balise b None)^"Raser"^(fbalise b)) p
   | CouperBois -> output_stab ((balise b None)^"CouperBois"^(fbalise b)) p
  
  
let output_transition ((ec,c,a,es,pds) : transition) (p : int) =
  output_stab (balise "transition" (Poids(pds))) p;
  output_cour ec (p+1);
  output_cond c (p+1);
  output_act a (p+1);
  output_suiv es (p+1);
  output_stab (fbalise "transition") p

let maxEtat (i : int) ((e1,_,_,e2,_) : transition) =
  max i (max e1 e2)
  
let output_automate (a : automate) (p : int) =
  let rec inner (a : automate) =
  match a with
   |t::a -> (output_transition t (p+1); inner a)
   |[] -> ()
  in
  output_stab (balise "automate" (NbEtats(1+(List.fold_left maxEtat 0 a)))) p;
  inner a;
  output_stab (fbalise "automate") p

(*let aut1 : automate = [(0,Libre(N),Avancer(N),0,1);
				(0,Ennemi(S),Attaquer(S),0,1);
				(0,Ennemi(N),Attaquer(N),0,1);
				(0,Libre(E),Avancer(E),0,1);
				(0,Libre(S),Avancer(S),0,0);
				(0,Libre(O),Avancer(O),1,0);
				(0,OrdreDonne,AvancerJoueur,0,5);

				(1,Libre(N),Avancer(N),0,0);
				(1,Libre(E),Avancer(E),0,0);
				(1,Libre(S),Avancer(S),1,1);			    
				(1,Libre(O),Avancer(O),1,1);
				(1,OrdreDonne,AvancerJoueur,0,5)			    
  ]*)

let hostile (p : poids) (e1 : etat) (e2 : etat) : automate =
  List.map (fun d -> (e1,Ennemi(d),Attaquer(d),e2,p)) [N;S;E;O]

let destructeur (p : poids) (el : etat list) : automate =
  List.map (fun e -> (e,Type(Batiment),Raser,e,p)) el

let recolteur (p : poids) (el : etat list) : automate =
  List.map (fun e -> (e,Type(Arbre),CouperBois,e,p)) el

let createur (p : poids) (el : etat list) : automate =
  List.concat (List.map (fun e -> List.map (fun d -> (e,Et(Libre(d),RessourcesPossedees(10)),Dupliquer(d),e,p)) [N;S;E;O]) el)

let errant (p : poids) (e1 : etat) (e2 : etat) : automate =
  List.map (fun d -> (e1,Libre(d),Avancer(d),e2,p)) [N;S;E;O]

let aut1 = (List.concat(List.map2 (errant 1) [0;1;2] [1;2;0]))@(recolteur 3 [0;1;2])@(createur 4 [0;1;2])

let main =
  output_string output "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n";
  output_stab (balise "liste" None) 0;
  output_automate aut1 1;
  output_stab (fbalise "liste") 0;
  close_out output
