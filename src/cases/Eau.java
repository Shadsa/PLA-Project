package cases;

import java.io.Serializable;

import roles.Personnage;
import roles.action.Action;
import roles.action.AvancerHasard;

public final class Eau extends TypeCase {

	protected static Action _action = new AvancerHasard();
	
	private static Eau _instance = new Eau();

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
		return false;
	}

	public Action action() {
		return _action;
	}
	
}