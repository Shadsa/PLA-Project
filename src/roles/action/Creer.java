package roles.action;

import cases.CaseProperty;
import cases.LibreCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class Creer extends Action {

	int _type;
	private static int _Id = Action.getId(1);

	public Creer(int type) {
		super();
		_type = type;
	}

	@Override
	public void Act(World world, Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		CaseProperty p = new LibreCheck(pers);
		if(p.check(world.Case(destX-1, destY))){
			destX--; direction = Cardinaux.OUEST;
		}
		else if(p.check(world.Case(destX+1, destY))){
			destX++; direction = Cardinaux.EST;
		}
		else if(p.check(world.Case(destX, destY-1))){
			destY--; direction = Cardinaux.NORD;
		}
		else if(p.check(world.Case(destX, destY+1))){
			destY++; direction = Cardinaux.SUD;
		}
		else return;

		/*if(p.check(world.Case(destX, destY)))
		{*/
			if(pers.owner().joueur().changerRessource(-100))
				pers.owner().createPersonnage(_type, destX, destY, null);
			pers.setState(new States(Statut.INVOQUE, direction));
		//}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
