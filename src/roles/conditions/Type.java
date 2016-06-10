package roles.conditions;

import cases.TypeCase;
import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class Type extends Condition {

	int _type;
	Cardinaux _direction;

	public Type(TypeCase type, Cardinaux card) {
		_type = type.value();
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		return World.Case(destX, destY).type().value()==_type;
	}
}
