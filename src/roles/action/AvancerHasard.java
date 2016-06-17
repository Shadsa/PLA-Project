package roles.action;

import java.util.Random;

import cases.Arbre;
import cases.Case;
import cases.Construction;
import cases.Mur;
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
	public void Act(Personnage pers) {
		Cardinaux direction=null;
		CaseProperty p = new LibreCheck(pers);
		int nbGen=0;
		Case cible = pers.cible();
		
		if (cible == null || (cible.X() == pers.X() && cible.Y() == pers.Y())) {
			pers.setCible(World.randomCase());
			cible = pers.cible();
		}
		if (cible.Y() > pers.Y() && p.check(World.Case(pers.X(), pers.Y() + 1)))
			direction = Cardinaux.SUD;
		else if (cible.Y() < pers.Y() && p.check(World.Case(pers.X(), pers.Y() - 1)))
			direction = Cardinaux.NORD;
		else if (cible.X() > pers.X() && p.check(World.Case(pers.X() + 1, pers.Y())))
			direction = Cardinaux.EST;
		else if (cible.X() < pers.X() && p.check(World.Case(pers.X() - 1, pers.Y())))
			direction = Cardinaux.OUEST;
		
		while (direction == null && nbGen++ < 10) {
			pers.setCible(World.randomCase());
			cible = pers.cible();
			if (cible.Y() > pers.Y() && p.check(World.Case(pers.X(), pers.Y() + 1)))
				direction = Cardinaux.SUD;
			else if (cible.Y() < pers.Y() && p.check(World.Case(pers.X(), pers.Y() - 1)))
				direction = Cardinaux.NORD;
			else if (cible.X() > pers.X() && p.check(World.Case(pers.X() + 1, pers.Y())))
				direction = Cardinaux.EST;
			else if (cible.X() < pers.X() && p.check(World.Case(pers.X() - 1, pers.Y())))
				direction = Cardinaux.OUEST;
		}
		if(direction == null)
			return;
			
		int destX = pers.X() + ((direction == Cardinaux.OUEST)? (-1) : ((direction == Cardinaux.EST)? 1 : 0));
		int destY = pers.Y() + ((direction == Cardinaux.NORD)? (-1) : ((direction == Cardinaux.SUD)? 1 : 0));
		//if(World.isfree(destX, destY) && !(World.Case(destX, destY).type() instanceof Mur && pers.owner() != ((Construction) World.Case(destX, destY).type()).getOwner()))
		if(p.check(World.Case(destX, destY)) && !(World.Case(destX, destY).type() instanceof Mur && pers.owner() != ((Construction) World.Case(destX, destY).type()).getOwner()))
		{
			//System.out.println(pers.ID() + " j'avance vers le " + direction +" "+ destX +" "+ destY + " etat="+pers.etat());
			if(World.Case(destX, destY).type() instanceof Arbre /*&& pers.classe().hard_walker()*/)
			{
				World.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.HIDING, direction));
			}
			else if(World.Case(pers.X(), pers.Y()).type() instanceof Arbre)
			{
				World.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.REVEAL, direction));
			}
			else //if(!(World.Case(destX, destY).type() instanceof Arbre))
			{
				World.Case(destX, destY).setPersonnage(pers);
				pers.setState(new States(Statut.AVANCE, direction));
			}
		}
		else
			System.out.println("J'ai pas pu avancer");
		/*else if(direction==Cardinaux.NORD || direction==Cardinaux.SUD)
			pers.setCible(World.Case(pers.cible().X(), pers.Y()));
		else
			pers.setCible(World.Case(pers.X(), pers.cible().Y()));*/
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
