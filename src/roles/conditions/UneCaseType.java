package roles.conditions;

import cases.CaseProperty;
import cases.TypeCase;
import cases.TypeCheck;
import roles.Personnage;

@SuppressWarnings("serial")
public class UneCaseType extends Condition {

	Class<? extends TypeCase> _type;
	CaseProperty _prop;

	public UneCaseType(Class<? extends TypeCase> type) {
		_type = type;
		_prop = new TypeCheck(_type);
	}

	@Override
	public boolean value(Personnage target) {
		return _prop.check(target.world().Case(target.X()-1, target.Y())) || _prop.check(target.world().Case(target.X()+1, target.Y())) || _prop.check(target.world().Case(target.X(), target.Y()-1)) || _prop.check(target.world().Case(target.X(), target.Y()+1));
	}
}
