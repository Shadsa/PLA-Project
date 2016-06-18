open TypesAutomate

let arbreProche (d : cellule) : condition = ArbreProche(d)
let caseAmi (d : cellule) : condition = CaseAmi(d)
let ennemi (d : cellule) : condition = Ennemi(d)
let ennemiProche (d : cellule) : condition = EnnemiProche(d)
let libre (d : cellule) : condition = Libre(d)
let typeCase (t : typeCellule) (d : cellule) : condition = Type(t,d)

let mur (d : cellule) : action = ConstruireMur(d)
let piege (d : cellule) : action = PoserPiege(d)

let creerAutomate (a : automate list) : automate = List.concat a

let rec appliquerSurListe (f : etat->etat->automate) (e1 : etat list) (e2 : etat list) : automate =  
  match e1 with
  | e::e1 -> creerAutomate ((appliquerSurListe f e1 e2)::(List.map (f e) e2))
  | [] -> []

let appliquerSurListe (f : etat->etat->automate) (e1 : etat) (e2 : etat list) : automate =  
  creerAutomate (List.map (f e1) e2)

let appliquerSurListe (f : etat->etat->automate) (e1 : etat list) (e2 : etat) : automate =  
  creerAutomate (List.map (fun e -> f e e2) e1)


  
let rec supercombine (a : 'a list) (b : 'b list) (c : 'c list) : ('a*'b*'c) list =
  match a,b,c with
   | (ahd::atl,bhd::btl,chd::ctl) -> (ahd,bhd,chd)::(supercombine atl btl ctl)
   | _ -> []

let transition (p : poids) (c : condition) (a : action) (e1 : etat) (e2 : etat) : automate =
  [(e1,c,a,e2,p)]

let hostile (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,UnEnnemi,Combattre,e2,p)]

let recolteur (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,UneCaseType(Arbre),CouperBois,e2,p)]
  
let createurNbRatio (p : poids) (typeU : int) (cout : int) (min : int) (minRat : int) (e1 : etat) (e2 : etat) : automate =
  [(e1,Et(Et(UneCaseLibre,RessourcesPossedees(cout)),Ou(NbInf(min),RatioInf(minRat))),Creer(typeU),e2,p)]

let createur (p : poids) (typeU : int) (cout : int) (e1 : etat) (e2 : etat) : automate =
  [(e1,Et(UneCaseLibre,RessourcesPossedees(cout)),Creer(typeU),e2,p)]

let createurZ (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,UneCaseLibre,DupliquerZombie,e2,p)]
  
let errant (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,Vide,AvancerHasard,e2,p)]
  
let medecin (p : poids) (e1 : etat) (e2 : etat) : automate =
  [(e1,UnAmi,Soigner,e2,p)]

let fonceur (p : poids) (eL : etat list) (e1 : etat) (e2 : etat) : automate =
  creerAutomate (List.map2 (fun e d -> [(e1,Libre(d),Avancer(d),e,p); (e,Libre(d),Avancer(d),e,p); (e,Vide,Attendre,e2,0)]) eL [N;S;E;O])

let reparateur (p : poids) (e1 : etat) (e2 : etat) : automate =
    creerAutomate (List.map (fun d -> [(e1,CaseAmi(d),Reparer(d),e,p)]) [N;S;E;O])

let chercheur (p : poids) (cond : cellule -> condition) (eL : etat list) (e1 : etat) (e2 : etat) : automate =
  creerAutomate (List.map2 (fun e d -> [(e1,Et(Libre(d),cond d),Avancer(d),e,p);
					     (e,Et(Libre(d),cond d),Avancer(d),e,p+1);
					     (e,Vide,Attendre,e2,0)]) eL [N;S;E;O])

let constructeur (p : poids) (construire : cellule -> action) (cout : int) (e1 : etat) (e2 : etat) : automate =
  creerAutomate (List.map (fun d -> [(e1,Et(Libre(d),RessourcesPossedees(cout)),construire d,e2,p)]) [N;S;E;O])