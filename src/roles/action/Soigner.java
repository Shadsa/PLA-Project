package roles.action;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public class Soigner extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(4);

	public Soigner(Cardinaux card) {
		super();
		_direction = card;
	}

	@Override
	public void Act(Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		if(World.Case(destX, destY) != null && World.Case(destX, destY).Personnage() != null)
		{
			Personnage target = World.Case(destX, destY).Personnage();
			System.out.print(pers.ID() + " soigne " + target.ID() + ".\n");
			pers.setState(new States(Statut.SOIGNE, _direction));
			target.change_vie(+ pers.heal());
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
