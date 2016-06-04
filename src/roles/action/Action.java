package roles.action;

import roles.Personnage;

public abstract class Action {
	int _poids;

	private static int _nextId = 0;

	// @ensure range>0
	protected static int getId(int range)
	{
		_nextId += range;
		return _nextId - range;
	}

	public Action(int poids)
	{
		_poids = poids;
	}
	public abstract void Act(Personnage pers);
	public int poids() {
		return _poids;
	}
	public abstract int toInt() ;
}
