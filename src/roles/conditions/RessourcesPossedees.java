package roles.conditions;

import roles.Personnage;

public class RessourcesPossedees extends Condition {

	int _quantite;

	public RessourcesPossedees(int quantite) {
		_quantite = quantite;
	}
	@Override
	public boolean value(Personnage target) {
		return target.owner().joueur().ressources()>=_quantite;
	}
}
