package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import roles.Joueur;
import roles.Personnage;

public class GArmy implements Observer {

	private Joueur _joueur;
	private MapTest _map;
	private ArrayList<Player> _players = new ArrayList<Player>();



	public GArmy(Joueur gj, MapTest map) {
		_joueur = gj;
		_map = map;
	}

	@Override
	public void update(Observable o, Object arg) {
		addPersonnage((Personnage)arg);
	}

	public void addPersonnage(Personnage pers)
	{
		//TODO gerer vetements et apparence
		_players.add(new Player(pers, TypeUnit.Human, TypeClothes.Civilian, this));
	}


	public ArrayList<Player> getPersonnage() {
		return _players;
	}

	public MapTest map()
	{
		return _map;
	}
}
