package roles.conditions;

import roles.Personnage;

@SuppressWarnings("serial")
public class Non extends Condition {

	Condition _c;

	public Non(Condition c) {
		_c=c;
	}

	@Override
	public boolean value(Personnage target) {
		return !_c.value(target);
	}
}
