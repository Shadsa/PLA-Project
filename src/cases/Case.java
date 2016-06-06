package cases;

import roles.Personnage;

public abstract class Case {

	final public static int firstCaseActionId = 1000;

	Personnage _personnage;
	protected int _x;
	protected int _y;

	Case(int x, int y)
	{
		_x = x;
		_y = y;
	}

	public Boolean isfree() {
		return _personnage == null;
	}

	public void setPersonnage(Personnage personnage) {
		// WARNING si non vide
		_personnage = personnage;
		if(personnage != null)
		{
			if(personnage.Case() != null)
				personnage.Case()._personnage = null;
			personnage.setCase(this);
		}
	}

	public abstract int value();

	public int X()
	{
		return _x;
	}

	public int Y()
	{
		return _y;
	}

	public Personnage Personnage() {
		return _personnage;
	}
}
