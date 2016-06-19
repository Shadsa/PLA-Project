package roles.conditions;

import roles.Personnage;

@SuppressWarnings("serial")
public class NbInf extends Condition {

	int _quantite;

	public NbInf(int quantite) {
		_quantite = quantite;
	}
	@Override
	public boolean value(Personnage target) {
		return target.owner().nbUnit(target.getUnite())<_quantite;
	}
}
