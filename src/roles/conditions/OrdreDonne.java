package roles.conditions;

import roles.Personnage;

@SuppressWarnings("serial")
public class OrdreDonne extends Condition {

	@Override
	public boolean value(Personnage target) {
		return target.directionJoueur()!=null;
	}
}
