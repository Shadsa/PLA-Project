package roles.action;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class Dupliquer extends Action {

	//Cardinaux _direction;
	private static int _Id = Action.getId(1);

	/*public Dupliquer(Cardinaux card) {
		super();
		_direction = card;
	}*/

	@Override
	public void Act(Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		if(World.isfree(destX-1, destY)){
			destX--; direction = Cardinaux.OUEST;
		}
		else if(World.isfree(destX+1, destY)){
			destX++; direction = Cardinaux.EST;
		}
		else if(World.isfree(destX, destY-1)){
			destY--; direction = Cardinaux.NORD;
		}
		else if(World.isfree(destX, destY+1)){
			destY++; direction = Cardinaux.SUD;
		}
		else return;
		
		if(World.isfree(destX, destY))
		{
			if(pers.owner().changerRessource(-250))
				pers.owner().createPersonnage(pers.classe(), destX, destY);
			pers.setState(new States(Statut.ATTAQUE, direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
