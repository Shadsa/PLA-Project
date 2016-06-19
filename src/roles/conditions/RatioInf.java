package roles.conditions;

import roles.Personnage;

@SuppressWarnings("serial")
public class RatioInf extends Condition {

	int _quantite;

	public RatioInf(int quantite) {
		_quantite = quantite;
	}
	@Override
	public boolean value(Personnage target) {
		return target.owner().ratioUnit(target.getUnite())<_quantite;
	}
}
