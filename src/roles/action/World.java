package roles.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import cases.Case;
import cases.Plaine;
import roles.Personnage;

public abstract class World {

	static Vector<Vector<Case>> _map = new Vector<Vector<Case>>();
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
		return World.Case(x, y) != null && World.Case(x, y).isfree();
	}

	public static Case Case(int x, int y) {
		return (x < 0 || y < 0 || x >= _map.size() | y >= _map.size())? null : _map.get(x).get(y);
	}

	public static void nextTurn()
	{
		ArrayList<Personnage> activated = new ArrayList<Personnage>();
		for(Joueur j : _joueurs)
			for(Personnage p : j.getPersonnages() )
				activated.add(p);

		Collections.shuffle(activated);

		for(Personnage p : activated)
			p.agir();
	}

	public static void BuildMap() {
		Vector<Case> temp;
		for(int i=0; i<10; i++)
		{
			temp = new Vector<Case>();
			for(int j=0; j<10; j++)
			{
				temp.add(new Plaine(i, j));
			}
			_map.add(temp);
		}
	}
}
