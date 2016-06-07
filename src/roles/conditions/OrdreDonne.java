package roles.conditions;

import roles.Personnage;

public class OrdreDonne extends Condition {

	@Override
	public boolean value(Personnage target) {
		return target.owner().directionJoueur()!=null;
	}
}
