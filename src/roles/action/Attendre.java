package roles.action;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public final class Attendre extends Action {

	
	private static int _Id = Action.getId(1);

	@Override
	public void Act(Personnage pers) {
		pers.setState(new States(Statut.ATTENDS, Cardinaux.NORD));
	}

	@Override
	public int toInt() {
		return _Id;
	}

}