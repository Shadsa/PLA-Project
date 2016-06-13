package roles.conditions;

import cases.Arbre;
import cases.LibreCheck;
import cases.TypeCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class ArbreProche extends Condition {

	Cardinaux _direction;

	public ArbreProche(Cardinaux card) {
		_direction = card;
	}
	@Override
	public boolean value(Personnage target) {
		if(_direction==null) return false;
		return target.find(new TypeCheck(Arbre.getInstance()), _direction, 5);
	}
}
