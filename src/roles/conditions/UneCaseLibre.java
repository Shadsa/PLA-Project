package roles.conditions;

import cases.CaseProperty;
import cases.LibreCheck;
import roles.Personnage;
import roles.World;

public class UneCaseLibre extends Condition {

	@Override
	public boolean value(Personnage target) {
		CaseProperty p = new LibreCheck(target);
		return p.check(World.Case(target.X()-1, target.Y())) || p.check(World.Case(target.X()+1, target.Y())) || p.check(World.Case(target.X(), target.Y()-1)) || p.check(World.Case(target.X(), target.Y()+1));
	}
}
