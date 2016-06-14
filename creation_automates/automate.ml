
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
  | Quantite of int
  | Poids of int
  | Compose of String.t
  | NbEtats of int
  | Rien

type action =
  | Attendre
  | Avancer of cellule
  | Attaquer of cellule
  | AvancerJoueur
  | Dupliquer
  | DupliquerZombie
  | Raser
  | CouperBois of cellule
  | AvancerHasard
(*
  | ...
*)

type condition =
  | Vide
  | Et of condition*condition
  | Ennemi of cellule
  | Libre of cellule
  | OrdreDonne
  | Type of typeCellule*cellule
  | RessourcesPossedees of int
  | ArbreProche of cellule
  | EnnemiProche of cellule
  | UneCaseLibre
(*
  | ...
*)


type etat = int
type poids = int
type transition = etat * condition * action * etat * poids
type automate = transition list

let output : (out_channel option) ref = ref(None)

let rec output_tab (n : int) =
  match !output with
   | Some(o) ->
    if n>0 then
      begin
      output_string o "\t";
      output_tab (n-1)
      end
   | None -> ()

let output_stab (s : String.t) (p : int) =
  match !output with
   | Some(o) ->
    begin
    output_tab p;
    output_string o s;
    output_char o '\n'
    end
   | None -> ()

let string_of_cellule (c : cellule) =
  match c with
   | N -> "N"
   | O -> "O"
   | S -> "S"
   | E -> "E"
   | C -> "C"

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
   | Type(t,d) -> " type=\""^(string_of_type t)^"\" direction=\""^(string_of_cellule d)^"\""
   | Quantite(i) -> " quantite=\""^(string_of_int i)^"\""
   | Poids(i) -> " poids=\""^(string_of_int i)^"\""
   | Compose(s) -> " compose=\""^s^"\""
   | NbEtats(i) -> " nbEtats=\""^(string_of_int i)^"\""
   |_ -> ""

  )^">"

let fbalise(s : String.t) =
  "</"^s^">"

  
  
let output_cour (e : etat) (p : int) =
  output_stab ((balise "etat" Rien)^(string_of_int e)^(fbalise "etat")) p

let output_suiv (e : etat) (p : int) =
  output_stab ((balise "suivant" Rien)^(string_of_int e)^(fbalise "suivant")) p


let rec output_cond (c : condition) (p : int) (suff : String.t) =
  let b = "condition"^suff in
  match c with
   | Vide -> output_stab ((balise b Rien)^"Vide"^(fbalise b)) p
   | Et(c1,c2) ->
    begin
    output_stab (balise b (Compose("Et"))) p;
    output_cond c1 (p+1) "1";
    output_cond c2 (p+1) "2";
    output_stab (fbalise b) p
    end
   | Ennemi(cellule) -> output_stab ((balise b (Direction(cellule)))^"Ennemi"^(fbalise b)) p
   | Libre(cellule) -> output_stab ((balise b (Direction(cellule)))^"Libre"^(fbalise b)) p
   | OrdreDonne -> output_stab ((balise b Rien)^"OrdreDonne"^(fbalise b)) p
   | Type(typeCellule,cellule) -> output_stab ((balise b (Type(typeCellule,cellule)))^"Type"^(fbalise b)) p
   | RessourcesPossedees(quantite) -> output_stab ((balise b (Quantite(quantite)))^"RessourcesPossedees"^(fbalise b)) p
   | ArbreProche(cellule) -> output_stab ((balise b (Direction(cellule)))^"ArbreProche"^(fbalise b)) p
   | EnnemiProche(cellule) -> output_stab ((balise b (Direction(cellule)))^"EnnemiProche"^(fbalise b)) p
   | UneCaseLibre -> output_stab ((balise b Rien)^"UneCaseLibre"^(fbalise b)) p

let output_act (a : action) (p : int) =
  let b = "action" in
  match a with
   | Attendre -> output_stab ((balise b Rien)^"Attendre"^(fbalise b)) p
   | Avancer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Avancer"^(fbalise b)) p
   | Attaquer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Attaquer"^(fbalise b)) p
   | AvancerJoueur -> output_stab ((balise b Rien)^"AvancerJoueur"^(fbalise b)) p
   | Dupliquer -> output_stab ((balise b Rien)^"Dupliquer"^(fbalise b)) p
   | DupliquerZombie -> output_stab ((balise b Rien)^"DupliquerZombie"^(fbalise b)) p
   | Raser -> output_stab ((balise b Rien)^"Raser"^(fbalise b)) p
   | CouperBois(cellule) -> output_stab ((balise b (Direction(cellule)))^"CouperBois"^(fbalise b)) p
   | AvancerHasard -> output_stab ((balise b Rien)^"AvancerHasard"^(fbalise b)) p
  
  
let output_transition ((ec,c,a,es,pds) : transition) (p : int) =
  output_stab (balise "transition" (Poids(pds))) p;
  output_cour ec (p+1);
  output_cond c (p+1) "";
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

(*let destructeur (p : poids) (el : etat list) : automate =
  List.map (fun e -> (e,Type(Batiment),Raser,e,p)) el*)

let recolteur (p : poids) (e1 : etat) (e2 : etat) : automate =
  List.map (fun d -> (e1,Type(Arbre,d),CouperBois(d),e2,p)) [N;S;E;O]
  
let createur (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,Et(UneCaseLibre,RessourcesPossedees(250)),Dupliquer,e2,p)]

let createurZ (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,UneCaseLibre,DupliquerZombie,e2,p)]
  
let errant (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,Vide,AvancerHasard,e2,p)]
  
let fonceur (p : poids) (e1 : etat) (eL : etat list) : automate =
  List.concat (List.map2 (fun e d -> [(e1,Libre(d),Avancer(d),e,p); (e,Libre(d),Avancer(d),e,p); (e,Vide,Attendre,e1,0)]) eL [N;S;E;O])
  
let rec supercombine (a : 'a list) (b : 'b list) (c : 'c list) : ('a*'b*'c) list =
  match a,b,c with
   | (ahd::atl,bhd::btl,chd::ctl) -> (ahd,bhd,chd)::(supercombine atl btl ctl)
   | _ -> []

let chercheur (p : poids) (ed : etat list) (e1 : etat list) (e2 : etat list) (er : etat) : automate =
  List.concat(List.map (fun (e1,e2,d) -> List.concat (List.map (fun e -> [(e,Et(Libre(d),ArbreProche(d)),Avancer(d),e1,p);(e1,Et(Libre(d),ArbreProche(d)),Avancer(d),e1,p+1);(e1,ArbreProche(d),Attendre,e2,p);(e1,Vide,Attendre,er,0);(e2,Et(Libre(d),ArbreProche(d)),Avancer(d),e1,p+1);(e2,Vide,Attendre,er,0)]) ed)) (supercombine e1 e2 [N;S;E;O]))

let chasseur (p : poids) (ed : etat list) (e1 : etat list) (e2 : etat list) (er : etat) : automate =
  List.concat(List.map (fun (e1,e2,d) -> List.concat (List.map (fun e -> [(e,Et(Libre(d),EnnemiProche(d)),Avancer(d),e1,p);(e1,Et(Libre(d),EnnemiProche(d)),Avancer(d),e1,p+1);(e1,EnnemiProche(d),Attendre,e2,p);(e1,Vide,Attendre,er,0);(e2,Et(Libre(d),EnnemiProche(d)),Avancer(d),e1,p+1);(e2,Vide,Attendre,er,0)]) ed)) (supercombine e1 e2 [N;S;E;O]))

let aut1 = List.concat ([errant 1 0 0; chercheur 3 [0] [1;3;5;7] [2;4;6;8] 0; createur 10 0 0]@List.map (fun e -> (recolteur 5 e 0)) [0;1;2;3;4;5;6;7;8])
(*let aut2 = List.concat ([errant 1 0 0; chasseur 3 [0] [1;3;5;7] [2;4;6;8] 0; createur 10 0 0]@(List.map (fun e -> (recolteur 5 e 0)) [0;1;2;3;4;5;6;7;8])@(List.map (fun e -> (hostile 6 e 0)) [0;1;2;3;4;5;6;7;8]))*)
let aut2 = List.concat ([errant 1 0 0; chasseur 3 [0] [1;3;5;7] [2;4;6;8] 0; createurZ 1 0 0]@(List.map (fun e -> (hostile 6 e 0)) [0;1;2;3;4;5;6;7;8]))

let aut3 = List.concat ([errant 1 0 0; chasseur 3 [0] [1;3;5;7] [2;4;6;8] 0]@(List.map (fun e -> (hostile 6 e 0)) [0;1;2;3;4;5;6;7;8]))
(*let aut1 = (List.concat(List.map2 (errant 1) [0;1;2] [1;2;0]))@(createur 4 [0;1;2])@(List.concat(List.map2 (recolteur 3) [0;1;2] [1;2;0]))*)
(*let aut1 = [(0,Et(OrdreDonne,Libre(E)),Avancer(E),1,1);(1,Et(Libre(O),OrdreDonne),Avancer(O),2,1);(2,Et(Et(OrdreDonne,Libre(N)),Et(Libre(N),OrdreDonne)),Avancer(N),3,1);(3,Et(OrdreDonne,Et(OrdreDonne,Et(OrdreDonne,Libre(S)))),Avancer(S),0,1)]*)

let main =
  let o = ref(open_out "Ouvrier.xml") in
  output := Some(!o);
  output_string !o "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n";
  output_automate aut1 0;
  close_out !o;
  o := open_out "Zombie.xml";
  output := Some(!o);
  output_string !o "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n";
  output_automate aut2 0;
  close_out !o;
  o := open_out "Soldat.xml";
  output := Some(!o);
  output_string !o "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n";
  output_automate aut3 0;
  close_out !o;
