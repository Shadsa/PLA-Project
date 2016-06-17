package roles.conditions;

import java.io.Serializable;

import roles.Personnage;

public abstract class Condition implements Serializable {
	public abstract boolean value(Personnage target);
}
