package roles.action;

import cases.Arbre;
import cases.CaseProperty;
import cases.LibreCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

@SuppressWarnings("serial")
public final class AvancerJoueur extends Action {

	private static int _Id = Action.getId(1);

	@Override
	public void Act(World world, Personnage pers) {
		Cardinaux direction = pers.directionJoueur();
		CaseProperty p = new LibreCheck(pers);
		if(direction==null) return;
		int destX = pers.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((direction == Cardinaux.NORD)? (-1) : ((direction == Cardinaux.SUD)? 1 : 0));
		if(p.check(world.Case(destX, destY)))
		{
			if(world.Case(destX, destY).type() instanceof Arbre)
			{
				//System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.HIDING, direction));
			}
			else if(world.Case(pers.X(), pers.Y()).type() instanceof Arbre)
			{
				//System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.REVEAL, direction));
			}
			else
			{
				//System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.AVANCE, direction));
			}
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
