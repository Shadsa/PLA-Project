package roles.conditions;

import cases.EnnemiCheck;
import roles.Cardinaux;
import roles.Personnage;

public class EnnemiProche extends Condition {

	Cardinaux _direction;

	public EnnemiProche(Cardinaux card) {
		_direction = card;
	}
	@Override
	public boolean value(Personnage target) {
		if(_direction==null) return false;
		return target.find(new EnnemiCheck(target), _direction, 2);
	}
}
