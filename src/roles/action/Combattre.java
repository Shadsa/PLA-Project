package roles.action;

import cases.Case;
import cases.CaseProperty;
import cases.ConstructionCheck;
import cases.PersoCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;
import roles.World;

public final class Combattre extends Action {

	private static int _Id = Action.getId(1);

	private static final CaseProperty _propPers = new PersoCheck();
	private static final CaseProperty _propCons = new ConstructionCheck();

	@Override
	public void Act(int world, Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		if(_propPers.check(World.Case(world, destX-1, destY)) || _propCons.check(World.Case(world, destX-1, destY))){
			destX--; direction = Cardinaux.OUEST;
		}
		else if(_propPers.check(World.Case(world, destX+1, destY)) || _propCons.check(World.Case(world, destX+1, destY))){
			destX++; direction = Cardinaux.EST;
		}
		else if(_propPers.check(World.Case(world, destX, destY-1)) || _propCons.check(World.Case(world, destX, destY-1))){
			destY--; direction = Cardinaux.NORD;
		}
		else if(_propPers.check(World.Case(world, destX, destY+1)) || _propCons.check(World.Case(world, destX, destY+1))){
			destY++; direction = Cardinaux.SUD;
		}
		else return;

		Case c = World.Case(world, destX, destY);

		if (_propPers.check(c)) {
			Personnage target = c.Personnage();
			// System.out.print(pers.ID() + " attaque " + target.ID() + ".\n");
			pers.setState(new States(Statut.ATTAQUE, direction));
			target.change_vie(-pers.damage());
		} else if (_propCons.check(c)) {
			c.attaquerCase(pers.damage());
			pers.setState(new States(Statut.ATTAQUE, direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
