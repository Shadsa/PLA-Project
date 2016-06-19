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
   | ComposeUnaire(s) -> " composeUnaire=\""^s^"\""
   | NbEtats(i) -> " nbEtats=\""^(string_of_int i)^"\""
   |_ -> ""

  )^">"

let fbalise(s : String.t) =
  "</"^s^">"

let output_with_balise (b : String.t) (a : attribut) (p : int) (nom : String.t) =
  output_stab ((balise b a)^nom^(fbalise b)) p  
  
  
let output_cour (e : etat) (p : int) =
  output_with_balise "etat" Rien p (string_of_int e)

let output_suiv (e : etat) (p : int) =
  output_with_balise "suivant" Rien p (string_of_int e)


let rec output_cond (c : condition) (p : int) (suff : String.t) =
  let b = "condition"^suff in
  match c with
   | Vide -> output_with_balise b Rien p "Vide"
   | OrdreDonne -> output_with_balise b Rien p "OrdreDonne"
   | UneCaseLibre -> output_with_balise b Rien p "UneCaseLibre"
   | UnAmi -> output_with_balise b Rien p "UnAmi"
   | UnEnnemi -> output_with_balise b Rien p "UnEnnemi"
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
   | Non(c) ->
    begin
    output_stab (balise b (ComposeUnaire("Non"))) p;
    output_cond c (p+1) "";
    output_stab (fbalise b) p
    end
   | ArbreProche(cellule) -> output_with_balise b (Direction(cellule)) p "ArbreProche"
   | Ami(cellule) -> output_with_balise b (Direction(cellule)) p "Ami"
   | CaseAmi(cellule) -> output_with_balise b (Direction(cellule)) p "Ami"
   | EnnemiProche(cellule) -> output_with_balise b (Direction(cellule)) p "EnnemiProche"
   | Ennemi(cellule) -> output_with_balise b (Direction(cellule)) p "Ennemi"
   | Libre(cellule) -> output_with_balise b (Direction(cellule)) p "Libre"
   | Type(typeCellule,cellule) -> output_with_balise b (Type(typeCellule,cellule)) p "Type"
   | UneCaseType(typeCellule) -> output_with_balise b (TypeG(typeCellule)) p "UneCaseType"
   | NbInf(quantite) -> output_with_balise b (Quantite(quantite)) p "NbInf"
   | RatioInf(quantite) -> output_with_balise b (Quantite(quantite)) p "RatioInf" 
   | RessourcesPossedees(quantite) -> output_with_balise b (Quantite(quantite)) p "RessourcesPossedees"   

let output_act (a : action) (p : int) =
  let b = "action" in
  match a with
   | Attendre -> output_with_balise b Rien p "Attendre"
   | AvancerJoueur -> output_with_balise b Rien p "AvancerJoueur"
   | AvancerHasard -> output_with_balise b Rien p "AvancerHasard"
   | Combattre -> output_with_balise b Rien p "Combattre"
   | CouperBois -> output_with_balise b Rien p "CouperBois"
   | DupliquerZombie -> output_with_balise b Rien p "DupliquerZombie"
   | Duel -> output_with_balise b Rien p "Duel"
   | Soigner -> output_with_balise b Rien p "Soigner"
   | Attaquer(cellule) -> output_with_balise b (Direction(cellule)) p "Attaquer"
   | Avancer(cellule) -> output_with_balise b (Direction(cellule)) p "Avancer"
   | ConstruireMur(cellule) -> output_with_balise b (Direction(cellule)) p "ConstruireMur"
   | PoserPiege(cellule) -> output_with_balise b (Direction(cellule)) p "PoserPiege"
   | Reparer(cellule) -> output_with_balise b (Direction(cellule)) p "Reparer"
   | Creer(t) -> output_with_balise b (Unite(t)) p "Creer"
  
  
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
  close_out_noerr o
  end
