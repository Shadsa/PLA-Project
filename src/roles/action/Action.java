package roles.action;

import roles.Personnage;

public abstract class Action {

	private static int _nextId = 0;

	// @ensure range>0
	protected static int getId(int range)
	{
		_nextId += range;
		return _nextId - range;
	}

	public abstract void Act(Personnage pers);

	public abstract int toInt() ;
}
