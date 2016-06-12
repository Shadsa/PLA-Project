package roles.action;

import cases.Arbre;
import cases.CaseProperty;
import cases.EnnemiCheck;
import cases.LibreCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public final class AvancerVersEnnemi extends Action {

	private static int _Id = Action.getId(1);
	private static CaseProperty p = new LibreCheck();

	@Override
	public void Act(Personnage pers) {
		Cardinaux direction = pers.find(new EnnemiCheck(pers), 20);
		if(direction==null) return;
		int destX = pers.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((direction == Cardinaux.NORD)? (-1) : ((direction == Cardinaux.SUD)? 1 : 0));
		if(p.check(World.Case(destX, destY)))
		{
			if(World.Case(destX, destY).type() instanceof Arbre)
			{
				System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
				World.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.HIDING, direction));
			}
			else if(World.Case(pers.X(), pers.Y()).type() instanceof Arbre)
			{
				System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
				World.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.REVEAL, direction));
			}
			else
			{
				System.out.print(pers.ID() + "j'avance vers le " + direction + destX + destY + ".\n");
				World.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.AVANCE, direction));
			}
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}