package cases;

import roles.Personnage;
import roles.action.Action;

public abstract class TypeCase {

	final public static int firstCaseActionId = 1000;

	//Réserver 0 pour les bâtiments de la ville ?
	private static int _nextId = 1;
	
	protected Action _action;
	
	protected abstract void Act(Personnage pers);
	
	// @ensure range>0
	protected static int getId(int range)
	{
		_nextId += range;
		return _nextId - range;
	}
	
	public abstract int value();
	
	public abstract boolean franchissable();
	

}
