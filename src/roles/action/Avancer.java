package roles.action;

import cases.Arbre;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class Avancer extends Action {

	Cardinaux _direction;
	private static int _Id = Action.getId(4);

	public Avancer(Cardinaux card) {
		super();
		_direction = card;
	}

	@Override
	public void Act(World world, Personnage pers) {
		int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));
		if(world.isfree(destX, destY))
		{
			if(world.Case(destX, destY).type() instanceof Arbre)
			{
				//System.out.print(pers.ID() + "j'avance vers l'arbre " + _direction + destX + " " + destY + ".\n");
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.HIDING, _direction));
			}
			else if(world.Case(pers.X(), pers.Y()).type() instanceof Arbre)
			{
				//System.out.print(pers.ID() + "j'avance vers le " + _direction + destX + destY + ".\n");
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.REVEAL, _direction));
			}
			else
			{
				//System.out.print(pers.ID() + "j'avance vers le " + _direction + destX + " " + destY + ".\n");
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.AVANCE, _direction));
			}
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
