package roles.action;

import roles.Personnage;

public abstract class Action {
	Personnage _personnage;
	int _poids;

	public Action(Personnage pers, int poids)
	{
		_personnage = pers;
		_poids = poids;
	}
	public abstract void Act();
	public int poids() {
		return _poids;
	}
}
