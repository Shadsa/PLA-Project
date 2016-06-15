package roles.action;

import java.util.Random;

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
	public void Act(int world, Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		if(World.isfree(world, destX-1, destY)){
			destX--; direction = Cardinaux.OUEST;
		}
		else if(World.isfree(world, destX+1, destY)){
			destX++; direction = Cardinaux.EST;
		}
		else if(World.isfree(world, destX, destY-1)){
			destY--; direction = Cardinaux.NORD;
		}
		else if(World.isfree(world, destX, destY+1)){
			destY++; direction = Cardinaux.SUD;
		}
		else return;

		if(World.isfree(world, destX, destY))
		{
			Random R = new Random();
			if(R.nextInt(Integer.min(100,pers.owner().getPersonnages().size()*2))<1)
				pers.owner().createPersonnage(pers.owner().getUnite(pers), destX, destY);
			pers.setState(new States(Statut.ATTAQUE, direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
