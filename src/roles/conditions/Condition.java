package roles.conditions;

import roles.Personnage;

public abstract class Condition {
	public abstract boolean value(Personnage target);
}
