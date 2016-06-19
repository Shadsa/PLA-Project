package roles.conditions;

import cases.AmiCheck;
import cases.CaseProperty;
import roles.Personnage;

@SuppressWarnings("serial")
public class UnAmi extends Condition {

	@Override
	public boolean value(Personnage target) {
		CaseProperty p = new AmiCheck(target);
		return p.check(target.world().Case(target.X()-1, target.Y())) || p.check(target.world().Case(target.X()+1, target.Y())) || p.check(target.world().Case(target.X(), target.Y()-1)) || p.check(target.world().Case(target.X(), target.Y()+1));
	}
}
