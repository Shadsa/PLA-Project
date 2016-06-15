package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.Action;
import roles.action.Attaquer;

public final class Arbre extends TypeCase {

	public final int _vie = 150;

	protected static Action _action = new Attaquer(Cardinaux.SUD);

	private static Arbre _instance = new Arbre();

	public final static int _id = getId(1);

	public int value() {
		return _id;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers.world(), pers);
	}

	public static TypeCase getInstance() {
		return _instance;
	}

	@Override
	public boolean franchissable() {
		return true;
	}

}
