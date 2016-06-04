package cases;

import roles.Personnage;

public abstract class Case {

	final public static int firstCaseActionId = 1000;

	Personnage _personnage;

	public Boolean isfree() {
		return _personnage == null;
	}

	public void setPersonnage(Personnage personnage) {
		_personnage = personnage;
	}

	public abstract int value();
}
