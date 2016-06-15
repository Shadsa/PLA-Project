package roles.action;

import cases.Construction;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class Attaquer extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(4);

	public Attaquer(Cardinaux card) {
		super();
		_direction = card;
	}

	@Override
	public void Act(Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		if(World.Case(destX, destY) != null && World.Case(destX, destY).Personnage() != null)
		{
			Personnage target = World.Case(destX, destY).Personnage();
			//System.out.print(pers.ID() + " attaque " + target.ID() + ".\n");
			pers.setState(new States(Statut.ATTAQUE, _direction));
			target.change_vie(- pers.damage());
		}
		else{				System.out.println("sldfuvherdjcemdfcjremj");

			if(World.Case(destX, destY) != null && World.Case(destX, destY).type() instanceof Construction)
			{
				System.out.println("Hi");
				World.Case(destX, destY).attaquerCase(pers.damage());
				pers.setState(new States(Statut.ATTAQUE, _direction));
			}
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
