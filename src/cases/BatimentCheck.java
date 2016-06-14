package cases;

import roles.Joueur;
import roles.Personnage;

public class BatimentCheck implements CaseProperty{

	Personnage _comparateur;
	
	public BatimentCheck(Personnage pers){
		_comparateur = pers;
	}
	
	public boolean check(Case c) {
		return c!=null && c.Personnage() != null && c.Personnage().owner() != _comparateur.owner();
	}

}
