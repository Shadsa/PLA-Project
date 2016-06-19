package roles.action;

import cases.AmiCheck;
import cases.CaseProperty;
import cases.PersoCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;
import roles.World;

public class Soigner extends Action {

	private static int _Id = Action.getId(1);

	private static final CaseProperty _prop = new PersoCheck();

	@Override
	public void Act(World world, Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		CaseProperty p = new AmiCheck(pers);
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
		else if(_prop.check(world.Case(destX-1, destY))){
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


		Personnage target = world.Case(destX, destY).Personnage();
		//System.out.print(pers.ID() + " soigne " + target.ID() + ".\n");
		pers.setState(new States(Statut.SOIGNE, direction));
		target.change_vie(+ pers.heal());
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
