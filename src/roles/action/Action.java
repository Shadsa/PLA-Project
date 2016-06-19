package roles.action;

import java.io.Serializable;

import roles.Personnage;
import roles.World;

@SuppressWarnings("serial")
public abstract class Action implements Serializable{

	private static int _nextId = 0;

	// @ensure range>0
	protected static int getId(int range)
	{
		_nextId += range;
		return _nextId - range;
	}

	public abstract void Act(World world, Personnage pers);

	public abstract int toInt() ;
}
