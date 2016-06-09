package roles.conditions;

import cases.TypeCase;
import roles.Cardinaux;
import roles.Personnage;
import roles.action.World;

public class Type extends Condition {

	int _type;

	public Type(TypeCase type) {
		_type = type.value();
	}

	@Override
	public boolean value(Personnage target) {
		return World.Case(target.X(), target.Y()) != null && World.Case(target.X(), target.Y()).type().value()==_type;
	}
}
