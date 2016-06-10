package roles.conditions;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class Ennemi extends Condition {

	Cardinaux _direction;

	public Ennemi(Cardinaux card) {
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		return World.Case(destX, destY) != null && World.Case(destX, destY).Personnage() != null && World.Case(destX, destY).Personnage().owner() != target.owner();
	}
}
