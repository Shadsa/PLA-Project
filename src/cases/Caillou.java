package cases;

import roles.Personnage;
import roles.action.*;
import roles.Cardinaux;


public final class Caillou extends TypeCase {

	protected static Action _action = new Attaquer(Cardinaux.NORD);
	
	private static Caillou _instance = new Caillou();
	
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

}
