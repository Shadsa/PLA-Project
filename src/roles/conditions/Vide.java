package roles.conditions;

import roles.Personnage;

public class Vide extends Condition {

	@Override
	public boolean value(Personnage target) {
		return true;
	}
}
