package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.Action;
import roles.action.Attaquer;

public final class Eau extends TypeCase {

	protected static Action _action = new Attaquer(Cardinaux.NORD,1);
	
	private static Eau _instance = new Eau();
	
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
		return false;
	}

}