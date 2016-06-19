package roles.action;

import java.util.Random;

import cases.Arbre;
import cases.Case;
import cases.CaseProperty;
import cases.LibreCheck;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.World;
import roles.States.Statut;

public final class AvancerHasard extends Action {

	private static int _Id = Action.getId(1);
	Random R = new Random();

	@Override
	public void Act(World world, Personnage pers) {
		Cardinaux direction=null;
		CaseProperty p = new LibreCheck(pers);
		int nbGen=0;
		Case cible = pers.cible();
		
		if (cible == null || (cible.X() == pers.X() && cible.Y() == pers.Y())) {
			pers.setCible(world.randomCase());
			cible = pers.cible();
		}
		if (cible.Y() > pers.Y() && p.check(world.Case(pers.X(), pers.Y() + 1)))
			direction = Cardinaux.SUD;
		else if (cible.Y() < pers.Y() && p.check(world.Case(pers.X(), pers.Y() - 1)))
			direction = Cardinaux.NORD;
		else if (cible.X() > pers.X() && p.check(world.Case(pers.X() + 1, pers.Y())))
			direction = Cardinaux.EST;
		else if (cible.X() < pers.X() && p.check(world.Case(pers.X() - 1, pers.Y())))
			direction = Cardinaux.OUEST;
		
		while (direction == null && nbGen++ < 10) {
			pers.setCible(world.randomCase());
			cible = pers.cible();
			if (cible.Y() > pers.Y() && p.check(world.Case(pers.X(), pers.Y() + 1)))
				direction = Cardinaux.SUD;
			else if (cible.Y() < pers.Y() && p.check(world.Case(pers.X(), pers.Y() - 1)))
				direction = Cardinaux.NORD;
			else if (cible.X() > pers.X() && p.check(world.Case(pers.X() + 1, pers.Y())))
				direction = Cardinaux.EST;
			else if (cible.X() < pers.X() && p.check(world.Case(pers.X() - 1, pers.Y())))
				direction = Cardinaux.OUEST;
		}
		if(direction == null)
			return;
			
		int destX = pers.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((direction == Cardinaux.NORD)? (-1) : ((direction == Cardinaux.SUD)? 1 : 0));
		//if(world.isfree(destX, destY) && !(world.Case(destX, destY).type() instanceof Mur && pers.owner() != ((Construction) world.Case(destX, destY).type()).getOwner()))
		if(p.check(world.Case(destX, destY)))
		{
			//System.out.println(pers.ID() + " j'avance vers le " + direction +" "+ destX +" "+ destY + " etat="+pers.etat());
			if(world.Case(destX, destY).type() instanceof Arbre /*&& pers.classe().hard_walker()*/)
			{
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.HIDING, direction));
			}
			else if(world.Case(pers.X(), pers.Y()).type() instanceof Arbre)
			{
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.REVEAL, direction));
			}
			else //if(!(world.Case(destX, destY).type() instanceof Arbre))
			{
				world.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.AVANCE, direction));
			}
		}
		else
			System.out.println("J'ai pas pu avancer");
		/*else if(direction==Cardinaux.NORD || direction==Cardinaux.SUD)
			pers.setCible(world.Case(pers.cible().X(), pers.Y()));
		else
			pers.setCible(world.Case(pers.X(), pers.cible().Y()));*/
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
