package roles.conditions;

import java.io.Serializable;

import roles.Personnage;

@SuppressWarnings("serial")
public abstract class Condition implements Serializable {
	public abstract boolean value(Personnage target);
}
