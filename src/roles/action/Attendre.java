package roles.action;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;
import roles.World;

@SuppressWarnings("serial")
public final class Attendre extends Action {


	private static int _Id = Action.getId(1);

	@Override
	public void Act(World world, Personnage pers) {
		pers.setState(new States(Statut.ATTENDS, Cardinaux.SUD));
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
