package roles.action;

import cases.Plaine;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public final class Raser extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(1);
	
	public Raser(int poids) {
		super(poids);
	}

	@Override
	public void Act(Personnage pers) {
		World.modifierCase(Plaine.getInstance(), pers.X(), pers.Y());
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
