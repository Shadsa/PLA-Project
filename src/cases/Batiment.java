package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.*;

public final class Batiment extends TypeCase {

	protected Action _action;
	
	public final static int _id = 0;
	
	public Batiment(Action a){
		_action = a;
	}

	public int value() {
		return _id;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers);		
	}

	@Override
	public boolean franchissable() {
		return true;
	}

}
