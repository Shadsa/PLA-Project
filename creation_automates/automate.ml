
type cellule =
  | C
  | N | S | E | O

type action =
  | Attendre
  | Avancer of cellule
  | Frapper of cellule
(*
  | ...
*)

type condition =
  | Vide
  | Ennemi of cellule
(*
  | ...
*)

type categorie =
  | Citoyen
  | Zombie
  | Soldat
(*
  | ...
*)

type etat = int
type poids = int
type transition = etat * condition * action * etat * poids
type automate_sans_type = transition list
type automate = categorie * automate_sans_type



let en_garde (cour : etat) (suiv : etat) : automate_sans_type = 
  List.map  (fun direction -> (cour, Ennemi(direction), Frapper(direction), suiv, 5) ) [N;S;E;O]

let add (a1 : automate_sans_type) (a2 : automate_sans_type) : automate_sans_type =
  a1@a2

let add ((c1,a1) : automate) ((c2,a2) : automate) : automate =
  if c1!=c2 then failwith "erreur" else (c1,add a1 a2)
  

let cellule_to_int (c : cellule) : int =
  match c with
   | C -> 0
   | N -> 1
   | S -> 2
   | E -> 3
   | O -> 4

let condition_to_int (c : condition) : int =
  match c with
   | Vide -> 0
   | Ennemi(cellule) -> 1 + (cellule_to_int cellule) (* 1..5 *)
  (*
    | ...
  *)
   | _ -> 0

let action_to_int (a : action) : int =
  match a with
   | Attendre -> 0
   | Avancer(cellule) -> 1 + (cellule_to_int cellule) (* 1..5 *)
   | Frapper(cellule) -> 5 + (cellule_to_int cellule) (* 6..10 *)
  (*
    | ...
  *)
   | _ -> 0

let cate_to_int (c : categorie) : int =
  match c with
   | Citoyen -> 0
   | Zombie -> 1
   | Soldat -> 2
(*
  | ...
*)
    


let element_to_xml (i : int) (nom: String.t) : String.t =
  "\t\t\t<"^nom^">"^string_of_int i^"</"^nom^">"
  
let etat_to_xml (e : etat) : String.t =
  element_to_xml e "etat"

let condition_to_xml (c : condition) : String.t =
  element_to_xml (condition_to_int c) "condition"

let action_to_xml (a : action) : String.t =
  element_to_xml (action_to_int a) "action"

let attribute_to_xml (i : int) : String.t =
  "type=\""^string_of_int i^"\""
  
let cate_to_xml (c : categorie) : String.t =
  attribute_to_xml (cate_to_int c)

let poids_to_xml (p : poids) : String.t =
  attribute_to_xml p

let transition_to_xml ((ec,c,a,es,p) : transition) : String.t =
  String.concat "\n" ["\t\t<transition "^poids_to_xml p^">";
		      etat_to_xml ec;
		      condition_to_xml c;
		      action_to_xml a;
		      etat_to_xml es;
		      "\t\t</transition>"]

let transition_list_to_xml (l : transition list) : String.t =
  String.concat "\n" (List.map transition_to_xml l)

let automate_to_xml ((cat,l) : automate) : String.t =
  "\t<automate "^cate_to_xml cat^">\n"^transition_list_to_xml l^"\n\t</automate>"

let aut1 : automate = (Citoyen,[(0,Ennemi(N),Frapper(N),2,1)])

let main =
  let output = open_out "sortie.xml" in
  begin
  output_string output "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n<!DOCTYPE liste SYSTEM \"automate.dtd\">\n\n<liste>\n";
  output_string output (automate_to_xml aut1);
  output_string output "\n</liste>";
  close_out output
  end
  

		 


   
