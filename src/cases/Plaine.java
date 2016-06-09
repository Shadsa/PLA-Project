package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.*;

public final class Plaine extends TypeCase {

	protected static Action _action = new Avancer(Cardinaux.SUD);
	
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

}
