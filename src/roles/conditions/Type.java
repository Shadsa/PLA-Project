package roles.conditions;

import cases.Case;
import cases.CaseProperty;
import cases.Construction;
import cases.Mur;
import cases.TypeCase;
import cases.TypeCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class Type extends Condition {

	Class<? extends TypeCase> _type;
	CaseProperty _prop;
	Cardinaux _direction;

	public Type(Class<? extends TypeCase> type, Cardinaux card) {
		_type = type;
		_direction = card;
		_prop = new TypeCheck(_type);
	}

	@Override
	public boolean value(Personnage target) {
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		return _prop.check(World.Case(destX, destY));	
	}
}
