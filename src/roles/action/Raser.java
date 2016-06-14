package roles.action;

import cases.Plaine;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class Raser extends Action {

	Cardinaux _direction;
	
	private static int _Id = Action.getId(1);

	@Override
	public void Act(Personnage pers) {
		World.modifierCase(Plaine.getInstance(), pers.X(), pers.Y());
		pers.setState(new States(Statut.ATTAQUE, Cardinaux.NORD));
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
