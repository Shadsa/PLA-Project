package roles.conditions;

import cases.AmiCheck;
import cases.CaseProperty;
import roles.Personnage;
import roles.World;

public class UnAmi extends Condition {

	@Override
	public boolean value(Personnage target) {
		CaseProperty p = new AmiCheck(target);
		return p.check(World.Case(target.X()-1, target.Y())) || p.check(World.Case(target.X()+1, target.Y())) || p.check(World.Case(target.X(), target.Y()-1)) || p.check(World.Case(target.X(), target.Y()+1));
	}
}
