package cases;

import roles.Personnage;

public class EnnemiCheck implements CaseProperty {

	Personnage _comparateur;

	public EnnemiCheck(Personnage pers){
		_comparateur = pers;
	}
	@Override
	public boolean check(Case c) {
		return c != null && ((c.Personnage() != null && c.Personnage().owner() != _comparateur.owner())
				|| (c.type() instanceof Construction && !(c.type() instanceof Piege) && ((Construction) c.type()).getOwner() != _comparateur.owner().joueur())
				|| (c.type() instanceof Batiment && ((CaseAction) c).owner() != _comparateur.owner().joueur()));
	}

}
