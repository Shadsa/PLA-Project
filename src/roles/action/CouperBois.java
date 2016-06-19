package roles.action;

import cases.Arbre;
import cases.CaseProperty;
import cases.TypeCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

@SuppressWarnings("serial")
public final class CouperBois extends Action {

	private static int _Id = Action.getId(1);

	private static final CaseProperty _prop = new TypeCheck(Arbre.class);

	@Override
	public void Act(World world, Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		if(_prop.check(world.Case(destX-1, destY))){
			destX--; direction = Cardinaux.OUEST;
		}
		else if(_prop.check(world.Case(destX+1, destY))){
			destX++; direction = Cardinaux.EST;
		}
		else if(_prop.check(world.Case(destX, destY-1))){
			destY--; direction = Cardinaux.NORD;
		}
		else if(_prop.check(world.Case(destX, destY+1))){
			destY++; direction = Cardinaux.SUD;
		}
		else return;

		world.Case(destX, destY).attaquerCase(pers.damage());
		pers.setState(new States(Statut.ATTAQUE, direction));
		pers.owner().joueur().changerRessource(6);
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
