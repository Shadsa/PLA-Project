package roles.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import cases.Case;
import cases.Plaine;
import roles.Carte;
import roles.Personnage;

public abstract class World {

	static Carte _map = new Carte(10,10);
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

	public static void BuildMap() {
		/*Vector<Case> temp;
		for(int i=0; i<10; i++)
		{
			temp = new Vector<Case>();
			for(int j=0; j<10; j++)
			{
				temp.add(new Plaine(i, j));
			}
			_map.add(temp);
		}*/
	}
}
