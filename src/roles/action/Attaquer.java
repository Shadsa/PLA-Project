package roles.action;

import cases.Case;
import cases.CaseProperty;
import cases.ConstructionCheck;
import cases.PersoCheck;
import cases.Batiment;
import cases.CaseAction;
import cases.Construction;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;
import roles.World;

public final class Attaquer extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(1);

	private static final CaseProperty _propPers = new PersoCheck();
	private static final CaseProperty _propCons = new ConstructionCheck();

	public Attaquer(Cardinaux card) {
		super();
		_direction = card;
	}

	@Override
	public void Act(Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST) ? (-1) : ((_direction == Cardinaux.EST) ? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.NORD) ? (-1) : ((_direction == Cardinaux.SUD) ? 1 : 0));
		Case c = World.Case(destX, destY);

		if (_propPers.check(c)) {
			Personnage target = World.Case(destX, destY).Personnage();
			pers.setState(new States(Statut.ATTAQUE, _direction));
			target.change_vie(-pers.damage());
		} else if (_propCons.check(c)) {
			World.Case(destX, destY).attaquerCase(pers.damage());
			pers.setState(new States(Statut.ATTAQUE, _direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
