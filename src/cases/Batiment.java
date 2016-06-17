package cases;

import roles.Personnage;
import roles.action.Action;

public final class Batiment extends TypeCase {

	public final int _vie = 20;

	protected Action _action;

	public final static int _id = getId(1);

	public Batiment(Action a){
		_action = a;
	}

	public int value() {
		return _id;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers.world(), pers);
	}

	@Override
	public boolean franchissable() {
		return true;
	}

	public Action action(){
		return _action;
	}

}
