package roles.action;

import cases.Case;
import cases.CaseProperty;
import cases.ConstructionCheck;
import cases.PersoCheck;
import graphique.MapGameState;
import cases.Batiment;
import cases.CaseAction;
import cases.Construction;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;
import roles.World;

public final class Duel extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(4);

	private static final CaseProperty _propPers = new PersoCheck();
	private static final CaseProperty _propCons = new ConstructionCheck();

	public Duel(Cardinaux card) {
		super();
		_direction = card;
	}

	@Override
	public void Act(World world, Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST) ? (-1) : ((_direction == Cardinaux.EST) ? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.NORD) ? (-1) : ((_direction == Cardinaux.SUD) ? 1 : 0));
		Case c = world.Case(destX, destY);

		if (_propPers.check(c)) {
			pers.setFighting(true);
			MapGameState.fight(pers, c.Personnage());
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
