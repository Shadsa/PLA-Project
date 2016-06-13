package cases;

import roles.Personnage;

public class AmiCheck implements CaseProperty{

	Personnage _comparateur;
	
	public AmiCheck(Personnage pers){
		_comparateur = pers;
	}
	@Override
	public boolean check(Case c) {
		return c!=null && c.Personnage() != null && c.Personnage().owner() == _comparateur.owner();
	}
}
