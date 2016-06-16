package roles.action;

import cases.Arbre;
import cases.Case;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class AvancerHasard extends Action {

	private static int _Id = Action.getId(1);

	@Override
	public void Act(World world, Personnage pers) {
		Cardinaux direction;
		Case cible = pers.cible();
		if(cible == null || (cible.X()==pers.X() && cible.Y()==pers.Y())){
			pers.setCible(world.randomCase());
			cible = pers.cible();
		}
		if(cible.Y()>pers.Y())
			direction=Cardinaux.SUD;
		else if(cible.Y()<pers.Y())
			direction=Cardinaux.NORD;
		else if(cible.X()>pers.X())
			direction=Cardinaux.EST;
		else
			direction=Cardinaux.OUEST;

		int destX = pers.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((direction == Cardinaux.NORD)? (-1) : ((direction == Cardinaux.SUD)? 1 : 0));
		if(world.isfree(destX, destY))
		{
			//System.out.println(pers.ID() + " j'avance vers le " + direction +" "+ destX +" "+ destY + " etat="+pers.etat());
			if(world.Case(destX, destY).type() instanceof Arbre)
			{
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.HIDING, direction));
			}
			else if(world.Case(pers.X(), pers.Y()).type() instanceof Arbre)
			{
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.REVEAL, direction));
			}
			else
			{
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.AVANCE, direction));
			}
		}
		else if(direction==Cardinaux.NORD || direction==Cardinaux.SUD)
			pers.setCible(world.Case(pers.cible().X(), pers.Y()));
		else
			pers.setCible(world.Case(pers.X(), pers.cible().Y()));
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
