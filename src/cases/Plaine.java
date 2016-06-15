package cases;

import roles.Personnage;
import roles.action.Action;
import roles.action.Attendre;

public final class Plaine extends TypeCase {

	protected static Action _action = new Attendre();
	
	private static Plaine _instance = new Plaine();
	
	public final static int _id = getId(1);

	public int value() {
		return _id;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers);		
	}

	public static TypeCase getInstance() {
		return _instance;
	}

	@Override
	public boolean franchissable() {
		return true;
	}

	@Override
	public Action action() {
		return _action;
	}

}
