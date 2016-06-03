package roles.action;

import roles.Personnage;

public abstract class Action {
	Personnage _personnage;
	
	public Action(Personnage pers)
	{
		_personnage = pers;
	}
	public abstract void Act();
}
