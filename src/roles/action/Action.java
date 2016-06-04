package roles.action;

import roles.Personnage;

public abstract class Action {
	int _poids;

	public Action(int poids)
	{
		_poids = poids;
	}
	public abstract void Act(Personnage pers);
	public int poids() {
		return _poids;
	}
}
