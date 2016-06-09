
type cellule =
  | C
  | N | S | E | O

type typeCellule =
  | Arbre
  | Plaine
  | Caillou
  | Eau

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
  | Ennemi of cellule
  | Libre of cellule
  | OrdreDonne
  | Type of typeCellule
  | RessourcesPossedees of int
(*
  | ...
*)

type attribut =
  | Direction of String.t
  | Type of String.t
  | Quantite of String.t
  | None


(*
type categorie =
  | Citoyen
  | Zombie
  | Soldat
(*
  | ...
*)
*)

type etat = int
type poids = int
type transition = etat * condition * action * etat * poids
type automate = transition list



let hostile (cour : etat) (suiv : etat) : automate = 
  List.map  (fun direction -> (cour, Ennemi(direction), Attaquer(direction), suiv, 5) ) [N;S;E;O]

let reproducteur (cour : etat) (suiv : etat) : automate = 
  List.map  (fun direction -> (cour, Libre(direction), Dupliquer(direction), suiv, 5) ) [N;S;E;O]

let add (a1 : automate) (a2 : automate) : automate =
  a1@a2

let cellule_to_string (c : cellule) : String.t =
  match c with
   | C -> "C"
   | N -> "N"
   | S -> "S"
   | E -> "E"
   | O -> "O"

let typeCellule_to_string (t : typeCellule) : String.t =
  match t with
  | Arbre -> "Arbre"
  | Plaine -> "Plaine"
  | Caillou -> "Caillou"
  | Eau -> "Eau"

let condition_to_string (c : condition) : String.t*attribut =
  match c with
   | Vide -> "Vide",None
   | Ennemi(cellule) -> "Ennemi",Direction(cellule_to_string cellule)
   | Libre(cellule) -> "Libre",Direction(cellule_to_string cellule)
   | OrdreDonne -> "OrdreDonne",None
   | Type(typeCellule) -> "Type",Type(typeCellule_to_string typeCellule)
  (*
    | ...
  *)
   | _ -> "",None

let action_to_string (a : action) : String.t*attribut =
  match a with
   | Attendre -> "Attendre",None
   | Avancer(cellule) -> "Avancer",Direction(cellule_to_string cellule)
   | Attaquer(cellule) -> "Attaquer",Direction(cellule_to_string cellule)
   | AvancerJoueur -> "AvancerJoueur",None
   | Dupliquer(cellule) -> "Dupliquer",Direction(cellule_to_string cellule)
   | Raser -> "Raser",None
   | CouperBois -> "CouperBois",None
  (*
    | ...
  *)
   | _ -> "",None


(*
let cate_to_string (c : categorie) : String.t =
  match c with
   | Citoyen -> "Citoyen"
   | Zombie -> "Zombie"
   | Soldat -> "Soldat"
(*
  | ...
*)
*)


let attribute_to_xml (s : String.t) (nom : String.t) : String.t =
  nom^"=\""^s^"\""

let element_to_xml ((s,attribute) : String.t*attribut) (nom : String.t) : String.t =
  match attribute with
   |None -> "\t\t\t<"^nom^">"^s^"</"^nom^">"
   |Direction(attribute) -> "\t\t\t<"^nom^" "^(attribute_to_xml attribute "direction")^">"^s^"</"^nom^">"
   |Type(attribute) -> "\t\t\t<"^nom^" "^(attribute_to_xml attribute "type")^">"^s^"</"^nom^">"
   |Quantite(attribute) -> "\t\t\t<"^nom^" "^(attribute_to_xml attribute "quantite")^">"^s^"</"^nom^">"
  
let etat_to_xml (e : etat) : String.t =
  element_to_xml (string_of_int e,None) "etat"

let suivant_to_xml (e : etat) : String.t =
  element_to_xml (string_of_int e,None) "suivant"
  
let condition_to_xml (c : condition) : String.t =
  element_to_xml (condition_to_string c) "condition"

let action_to_xml (a : action) : String.t =
  element_to_xml (action_to_string a) "action"

let poids_to_xml (p : poids) : String.t =
  attribute_to_xml (string_of_int p) "poids"

let transition_to_xml ((ec,c,a,es,p) : transition) : String.t =
  String.concat "\n" ["\t\t<transition "^poids_to_xml p^">";
		      etat_to_xml ec;
		      condition_to_xml c;
		      action_to_xml a;
		      suivant_to_xml es;
		      "\t\t</transition>"]

let transition_list_to_xml (l : transition list) : String.t =
  String.concat "\n" (List.map transition_to_xml l)


let default_trans = (0,Vide,Attendre,0,0)

let nb_etat (a : automate) : int =
  let max_etat_trans ((_,_,_,e1,_) as t1 : transition) ((_,_,_,e2,_) as t2 : transition) : transition =
    if e1>e2 then t1 else t2
  in
  let (_,_,_,e,_) = List.fold_left (max_etat_trans) default_trans a in
  1+e


let automate_to_xml (a : automate) : String.t =
  "\t<automate "^attribute_to_xml (string_of_int (nb_etat a)) "nbEtats"^">\n"^transition_list_to_xml a^"\n\t</automate>"

let aut1 : automate = [(0,Libre(N),Avancer(N),0,1);
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
				(0,OrdreDonne,AvancerJoueur,1,5)			    
			       ]

let main =
  let output = open_out "sortie.xml" in
  begin
  output_string output "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n<liste>\n";
  output_string output (automate_to_xml aut1);
  output_string output "\n</liste>";
  close_out output
  end
  

		 


   
