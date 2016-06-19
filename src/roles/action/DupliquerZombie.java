package roles.action;

import java.util.Random;

import cases.CaseProperty;
import cases.LibreCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class DupliquerZombie extends Action {

	//Cardinaux _direction;
	private static int _Id = Action.getId(1);

	/*public Dupliquer(Cardinaux card) {
		super();
		_direction = card;
	}*/

	@Override
	public void Act(World world, Personnage pers) {
		CaseProperty p = new LibreCheck(pers);
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
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
			Random R = new Random();
			if(R.nextInt(Integer.min(100,pers.owner().getPersonnages().size()*2))<1)
				pers.owner().createPersonnage(pers.owner().joueur().getUnite(pers), destX, destY, null);
			pers.setState(new States(Statut.INVOQUE, direction));
		//}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
