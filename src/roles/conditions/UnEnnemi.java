package roles.conditions;

import cases.CaseProperty;
import cases.EnnemiCheck;
import roles.Personnage;
import roles.World;

public class UnEnnemi extends Condition {

	@Override
	public boolean value(Personnage target) {
		CaseProperty p = new EnnemiCheck(target);
		return p.check(World.Case(target.world(), target.X()-1, target.Y())) || p.check(World.Case(target.world(), target.X()+1, target.Y())) || p.check(World.Case(target.world(), target.X(), target.Y()-1)) || p.check(World.Case(target.world(), target.X(), target.Y()+1));
	}
}
