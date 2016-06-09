
type cellule =
  | C
  | N | S | E | O

type action =
  | Attendre
  | Avancer of cellule
  | Attaquer of cellule
  | AvancerJoueur
(*
  | ...
*)

type condition =
  | Vide
  | Ennemi of cellule
  | Libre of cellule
  | OrdreDonne
(*
  | ...
*)


type categorie = int
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
type automate_sans_type = transition list
type automate = categorie * automate_sans_type



let hostile (cour : etat) (suiv : etat) : automate_sans_type = 
  List.map  (fun direction -> (cour, Ennemi(direction), Attaquer(direction), suiv, 5) ) [N;S;E;O]

let add (a1 : automate_sans_type) (a2 : automate_sans_type) : automate_sans_type =
  a1@a2

let add ((c1,a1) : automate) ((c2,a2) : automate) : automate =
  if c1!=c2 then failwith "erreur" else (c1,add a1 a2)
  

let cellule_to_string (c : cellule) : String.t =
  match c with
   | C -> "C"
   | N -> "N"
   | S -> "S"
   | E -> "E"
   | O -> "O"

let condition_to_string (c : condition) : String.t*(String.t option) =
  match c with
   | Vide -> "Vide",None
   | Ennemi(cellule) -> "Ennemi",Some(cellule_to_string cellule)
   | Libre(cellule) -> "Libre",Some(cellule_to_string cellule)
   | OrdreDonne -> "OrdreDonne",None
  (*
    | ...
  *)
   | _ -> "",None

let action_to_string (a : action) : String.t*(String.t option) =
  match a with
   | Attendre -> "Attendre",None
   | Avancer(cellule) -> "Avancer",Some(cellule_to_string cellule)
   | Attaquer(cellule) -> "Attaquer",Some(cellule_to_string cellule)
   | AvancerJoueur -> "AvancerJoueur",None
  (*
    | ...
  *)
   | _ -> "",None


let cate_to_string (c : categorie) : String.t = string_of_int c
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

let element_to_xml ((s,attribute) : String.t*(String.t option)) (nom : String.t) : String.t =
  match attribute with
   |None -> "\t\t\t<"^nom^">"^s^"</"^nom^">"
   |Some(attribute) -> "\t\t\t<"^nom^" "^(attribute_to_xml attribute "direction")^">"^s^"</"^nom^">"
  
let etat_to_xml (e : etat) : String.t =
  element_to_xml (string_of_int e,None) "etat"

let suivant_to_xml (e : etat) : String.t =
  element_to_xml (string_of_int e,None) "suivant"
  
let condition_to_xml (c : condition) : String.t =
  element_to_xml (condition_to_string c) "condition"

let action_to_xml (a : action) : String.t =
  element_to_xml (action_to_string a) "action"
  
let cate_to_xml (c : categorie) : String.t =
  attribute_to_xml (cate_to_string c) "type"

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

let automate_to_xml ((cat,l) : automate) : String.t =
  "\t<automate "^cate_to_xml cat^">\n"^transition_list_to_xml l^"\n\t</automate>"

let aut1 : automate = (0,[(0,Libre(N),Avancer(N),0,1);
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
			       ])

let main =
  let output = open_out "sortie.xml" in
  begin
  output_string output "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n<!DOCTYPE liste SYSTEM \"automate.dtd\">\n\n<liste>\n";
  output_string output (automate_to_xml aut1);
  output_string output "\n</liste>";
  close_out output
  end
  

		 


   
