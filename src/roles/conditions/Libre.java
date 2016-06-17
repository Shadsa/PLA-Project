package roles.conditions;

import cases.Arbre;
import cases.CaseProperty;
import cases.Construction;
import cases.LibreCheck;
import cases.Mur;
import roles.Cardinaux;
import roles.Personnage;
import roles.World;

public class Libre extends Condition {

	Cardinaux _direction;

	public Libre(Cardinaux card) {
		_direction = card;
	}

	@Override
	public boolean value(Personnage target) {
		CaseProperty p = new LibreCheck(target);
		int destX = target.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = target.Y() + ((_direction == Cardinaux.NORD) ? (-1) : ((_direction == Cardinaux.SUD) ? 1 : 0));
		/*if (World.Case(destX, destY).type() instanceof Mur)
			if (target.owner() != ((Construction) World.Case(destX, destY).type()).getOwner())
				return false;*/
		return p.check(World.Case(destX, destY));
	}
}
