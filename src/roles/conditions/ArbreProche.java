package roles.conditions;

import cases.Arbre;
import cases.LibreCheck;
import cases.TypeCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class ArbreProche extends Condition {

	@Override
	public boolean value(Personnage target) {
		Cardinaux direction = target.find(new TypeCheck(Arbre.getInstance()), 5);
		if(direction==null) return false;
		int destX = target.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((direction == Cardinaux.NORD)? (-1) : ((direction == Cardinaux.SUD)? 1 : 0));
		return (new LibreCheck()).check(World.Case(destX, destY));
	}
}
