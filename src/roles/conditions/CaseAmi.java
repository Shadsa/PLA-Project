package roles.conditions;

import cases.AutomateAmiCheck;
import cases.CaseProperty;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class CaseAmi extends Condition {

	Cardinaux _direction;

	public CaseAmi(Cardinaux card) {
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		CaseProperty p = new AutomateAmiCheck(target);
		return p.check(World.Case(destX, destY));
	}
}
