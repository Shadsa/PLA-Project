package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.Action;
import roles.action.Attaquer;

public class Mur extends TypeCase {
	
	public final int _vie = 25;

	protected static Action _action = new Attaquer(Cardinaux.EST);
	
	private static Mur _instance = new Mur();
	
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
