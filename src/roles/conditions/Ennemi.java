package roles.conditions;

import cases.CaseProperty;
import cases.EnnemiCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class Ennemi extends Condition {

	Cardinaux _direction;

	public Ennemi(Cardinaux card) {
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		CaseProperty p = new EnnemiCheck(target);
		return p.check(World.Case(destX, destY));
	}
}
