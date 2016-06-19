package roles.conditions;

import cases.CaseProperty;
import cases.LibreCheck;
import roles.Personnage;

@SuppressWarnings("serial")
public class UneCaseLibre extends Condition {

	@Override
	public boolean value(Personnage target) {
		CaseProperty p = new LibreCheck(target);
		return p.check(target.world().Case(target.X()-1, target.Y())) || p.check(target.world().Case(target.X()+1, target.Y())) || p.check(target.world().Case(target.X(), target.Y()-1)) || p.check(target.world().Case(target.X(), target.Y()+1));
	}
}
