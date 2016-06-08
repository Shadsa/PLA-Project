package roles.action;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public final class AvancerJoueur extends Action {

	private static int _Id = Action.getId(4);

	public AvancerJoueur(int poids) {
		super(poids);
	}

	@Override
	public void Act(Personnage pers) {
		Cardinaux direction = pers.directionJoueur();
		if(direction==null) return;
		int destX = pers.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((direction == Cardinaux.SUD)? (-1) : ((direction == Cardinaux.NORD)? 1 : 0));
		if(World.isfree(destX, destY))
		{
			System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
			World.Case(destX, destY).setPersonnage(pers);
			pers.setState(new States(Statut.AVANCE, direction));
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
