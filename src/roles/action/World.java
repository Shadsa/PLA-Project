package roles.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import cases.Case;
import cases.Plaine;
import graphique.ObjetTest;
import roles.Carte;
import roles.Personnage;

public abstract class World {

	static Carte _map;
	static ArrayList<Joueur> _joueurs = new ArrayList<Joueur>();

	public static void addPlayer(Joueur j)
	{
		_joueurs.add(j);
	}

	// final return...
	public static ArrayList<Joueur> getPlayers()
	{
		return _joueurs;
	}

	public static Boolean isfree(int x, int y) {
		return _map.isfree(x, y);
	}

	public static Case Case(int x, int y) {
		return _map.Case(x, y);
	}

	public static void nextTurn()
	{
		ArrayList<Personnage> activated = new ArrayList<Personnage>();
		for(Joueur j : _joueurs)
			for(Personnage p : j.getPersonnages() )
				activated.add(p);

		Collections.shuffle(activated);
		for(Personnage p : activated)
			if(p.vie()>0)
				p.agir();
	}

	public static void BuildMap(int hauteur, int largeur) {
		_map = new Carte(hauteur,largeur);
	}

	public static int SizeX() {
		return _map.size();
	}
}
