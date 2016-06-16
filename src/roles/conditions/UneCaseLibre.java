package roles.conditions;

import cases.CaseProperty;
import cases.LibreCheck;
import roles.Personnage;
import roles.World;

public class UneCaseLibre extends Condition {

	private static CaseProperty p = new LibreCheck();

	@Override
	public boolean value(Personnage target) {
		return p.check(target.world().Case(target.X()-1, target.Y())) || p.check(target.world().Case(target.X()+1, target.Y())) || p.check(target.world().Case(target.X(), target.Y()-1)) || p.check(target.world().Case(target.X(), target.Y()+1));
	}
}
