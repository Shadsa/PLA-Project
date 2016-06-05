package roles.action;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;

public final class Avancer extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(4);

	public Avancer(Cardinaux card, int poids) {
		super(poids);
		_direction = card;
	}

	@Override
	public void Act(Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.SUD)? (-1) : ((_direction == Cardinaux.NORD)? 1 : 0));
		if(World.isfree(destX, destY))
		{
			System.out.print(pers.ID() + "j'avance vers le " + _direction + destX + destY + ".\n");
			World.Case(destX, destY).setPersonnage(pers);
			pers.notifyObservers(States.AVANCE.init(_direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
