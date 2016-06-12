package cases;

import roles.Personnage;

public class AutomateAmiCheck implements CaseProperty {

	Personnage _comparateur;
	
	public AutomateAmiCheck(Personnage pers){
		_comparateur = pers;
	}
	@Override
	public boolean check(Case c) {
		return c!=null && (c instanceof CaseAction) && ((CaseAction)c).owner() == _comparateur.owner();
	}

}
