package roles.action;

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
			if(pers.owner().changerRessource(-100))
				pers.owner().createPersonnage(_type, destX, destY);
			pers.setState(new States(Statut.INVOQUE, direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
