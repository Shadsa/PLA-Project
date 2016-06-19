package roles.action;

import cases.CaseAction;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class Reparer extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(4);
	public Reparer(Cardinaux card) {
		super();
		_direction = card;
	}

	@Override
	public void Act(World world, Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		if(world.Case(destX, destY)!=null && world.Case(destX, destY) instanceof CaseAction){
			world.modifierCase(((CaseAction)  world.Case(destX, destY) ).etatInitial(), destX, destY);
			pers.setState(new States(Statut.ATTAQUE, _direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
