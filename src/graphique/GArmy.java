package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import roles.Personnage;

public class GArmy implements Observer {

	private GJoueur _joueur;
	private MapTest _map;
	private ArrayList<Player> _players = new ArrayList<Player>();



	public GArmy(GJoueur gj, MapTest map) {
		_joueur = gj;
		_map = map;
	}

	@Override
	public void update(Observable o, Object arg) {
		addPersonnage((Personnage)arg);
	}

	public void addPersonnage(Personnage pers)
	{
		_players.add(new Player(pers, _joueur.type_unit(), this));
	}


	public ArrayList<Player> getPersonnage() {
		return _players;
	}

	public MapTest map()
	{
		return _map;
	}
}
