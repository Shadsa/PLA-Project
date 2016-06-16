package roles.conditions;

import cases.AmiCheck;
import cases.CaseProperty;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class Ami extends Condition {
	Cardinaux _direction;

	public Ami(Cardinaux card) {
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		CaseProperty p = new AmiCheck(target);
		return p.check(target.world().Case(destX, destY));
	}

}
