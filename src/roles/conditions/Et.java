package roles.conditions;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class Et extends Condition {

	Condition _c1;
	Condition _c2;

	public Et(Condition c1, Condition c2) {
		_c1=c1;
		_c2=c2;
	}

	@Override
	public boolean value(Personnage target) {
		return _c1.value(target) && _c2.value(target);
	}
}
