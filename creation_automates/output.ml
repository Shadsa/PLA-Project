open TypesAutomate

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
   | TypeG(t) -> " type=\""^(string_of_type t)^"\""
   | Quantite(i) -> " quantite=\""^(string_of_int i)^"\""
   | Unite(i) -> " unite=\""^(string_of_int i)^"\""
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
   | OrdreDonne -> output_stab ((balise b Rien)^"OrdreDonne"^(fbalise b)) p
   | UneCaseLibre -> output_stab ((balise b Rien)^"UneCaseLibre"^(fbalise b)) p
   | UnAmi -> output_stab ((balise b Rien)^"UnAmi"^(fbalise b)) p
   | UnEnnemi -> output_stab ((balise b Rien)^"UnEnnemi"^(fbalise b)) p
   | Et(c1,c2) ->
    begin
    output_stab (balise b (Compose("Et"))) p;
    output_cond c1 (p+1) "1";
    output_cond c2 (p+1) "2";
    output_stab (fbalise b) p
    end
   | Ou(c1,c2) ->
    begin
    output_stab (balise b (Compose("Ou"))) p;
    output_cond c1 (p+1) "1";
    output_cond c2 (p+1) "2";
    output_stab (fbalise b) p
    end
   | ArbreProche(cellule) -> output_stab ((balise b (Direction(cellule)))^"ArbreProche"^(fbalise b)) p
   | Ami(cellule) -> output_stab ((balise b (Direction(cellule)))^"Ami"^(fbalise b)) p
   | CaseAmi(cellule) -> output_stab ((balise b (Direction(cellule)))^"Ami"^(fbalise b)) p
   | EnnemiProche(cellule) -> output_stab ((balise b (Direction(cellule)))^"EnnemiProche"^(fbalise b)) p
   | Ennemi(cellule) -> output_stab ((balise b (Direction(cellule)))^"Ennemi"^(fbalise b)) p
   | Libre(cellule) -> output_stab ((balise b (Direction(cellule)))^"Libre"^(fbalise b)) p
   | Type(typeCellule,cellule) -> output_stab ((balise b (Type(typeCellule,cellule)))^"Type"^(fbalise b)) p
   | UneCaseType(typeCellule) -> output_stab ((balise b (TypeG(typeCellule)))^"UneCaseType"^(fbalise b)) p
   | NbInf(quantite) -> output_stab ((balise b (Quantite(quantite)))^"NbInf"^(fbalise b)) p
   | RatioInf(quantite) -> output_stab ((balise b (Quantite(quantite)))^"RatioInf"^(fbalise b)) p 
   | RessourcesPossedees(quantite) -> output_stab ((balise b (Quantite(quantite)))^"RessourcesPossedees"^(fbalise b)) p   

let output_act (a : action) (p : int) =
  let b = "action" in
  match a with
   | Attendre -> output_stab ((balise b Rien)^"Attendre"^(fbalise b)) p
   | AvancerJoueur -> output_stab ((balise b Rien)^"AvancerJoueur"^(fbalise b)) p
   | AvancerHasard -> output_stab ((balise b Rien)^"AvancerHasard"^(fbalise b)) p
   | Combattre -> output_stab ((balise b Rien)^"Combattre"^(fbalise b)) p
   | CouperBois -> output_stab ((balise b Rien)^"CouperBois"^(fbalise b)) p
   | DupliquerZombie -> output_stab ((balise b Rien)^"DupliquerZombie"^(fbalise b)) p
   | Duel -> output_stab ((balise b Rien)^"Duel"^(fbalise b)) p
   | Soigner -> output_stab ((balise b Rien)^"Soigner"^(fbalise b)) p
   | Attaquer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Attaquer"^(fbalise b)) p
   | Avancer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Avancer"^(fbalise b)) p
   | ConstruireMur(cellule) -> output_stab ((balise b (Direction(cellule)))^"ConstruireMur"^(fbalise b)) p
   | PoserPiege(cellule) -> output_stab ((balise b (Direction(cellule)))^"PoserPiege"^(fbalise b)) p
   | Reparer(cellule) -> output_stab ((balise b (Direction(cellule)))^"Reparer"^(fbalise b)) p
   | Creer(t) -> output_stab ((balise b (Unite(t)))^"Creer"^(fbalise b)) p
  
  
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

let ecrireXML (nom : string) (auto : automate) =
  let o = open_out nom in
  begin
  output := Some(o);
  output_string o "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n\n";
  output_automate auto 0;
  close_out o
  end
