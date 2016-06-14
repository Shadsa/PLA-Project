package roles.conditions;

import cases.CaseProperty;
import cases.LibreCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class Libre extends Condition {

	Cardinaux _direction;
	private static CaseProperty p = new LibreCheck();

	public Libre(Cardinaux card) {
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		return p.check(World.Case(destX, destY));
	}
}
