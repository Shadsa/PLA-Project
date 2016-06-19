package roles.conditions;

import roles.Personnage;

@SuppressWarnings("serial")
public class Vide extends Condition {

	@Override
	public boolean value(Personnage target) {
		return true;
	}
}
