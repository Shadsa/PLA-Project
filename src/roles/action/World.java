package roles.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import cases.Case;
import roles.Personnage;

public abstract class World {

	static Vector<Vector<Case>> _map;
	static ArrayList<Joueur> _joueurs;

	public static void addPlayer(Joueur j)
	{
		_joueurs.add(j);
	}

	public static Boolean isfree(int x, int y) {
		return World.Case(x, y).isfree();
	}

	public static Case Case(int x, int y) {
		return (x < 0 || y < 0 || x >= _map.size() | y >= _map.size())? null : _map.get(x).get(y);
	}

	public void nextTurn()
	{
		ArrayList<Personnage> activated = new ArrayList<Personnage>();
		for(Joueur j : _joueurs)
			for(Personnage p : j.getPersonnages() )
				activated.add(p);

		Collections.shuffle(activated);

		for(Personnage p : activated)
			p.agir();
	}
}
