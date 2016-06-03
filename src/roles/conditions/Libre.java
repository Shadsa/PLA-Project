package roles.conditions;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class Libre extends Condition {

	Personnage _target;

	Cardinaux _direction;

	public Libre(Personnage pers, Cardinaux card) {
		_target = pers;
		_direction = card;
	}

	@Override
	public boolean value() {
		int destX = _target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = _target.Y() + ((_direction == Cardinaux.SUD)? (-1) : ((_direction == Cardinaux.NORD)? 1 : 0));
		return World.isfree(destX, destY);
	}
}
