package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.Action;
import roles.action.Attaquer;

public final class Arbre extends TypeCase {


	protected static Action _action = new Attaquer(Cardinaux.SUD,1);
	
	private static Arbre _instance = new Arbre();
	
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
