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
	public void Act(World world, Personnage pers) {
		int destX=pers.X();
		int destY=pers.Y();
		Cardinaux direction;
		if(world.isfree(destX-1, destY)){
			destX--; direction = Cardinaux.OUEST;
		}
		else if(world.isfree(destX+1, destY)){
			destX++; direction = Cardinaux.EST;
		}
		else if(world.isfree(destX, destY-1)){
			destY--; direction = Cardinaux.NORD;
		}
		else if(world.isfree(destX, destY+1)){
			destY++; direction = Cardinaux.SUD;
		}
		else return;

		if(world.isfree(destX, destY))
		{
			if(pers.owner().joueur().changerRessource(-100))
				pers.owner().createPersonnage(pers.owner().joueur().getUnite(pers), destX, destY, null);
			pers.setState(new States(Statut.INVOQUE, direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
