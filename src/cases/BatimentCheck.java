package cases;

import roles.Personnage;

public class BatimentCheck implements Construction{

	Personnage _comparateur;
	
	public BatimentCheck(Personnage pers){
		_comparateur = pers;
	}
	@Override
	public boolean check(Case c) {
		return c!=null && c.Personnage() != null && c.Personnage().owner() != _comparateur.owner();
	}

}
