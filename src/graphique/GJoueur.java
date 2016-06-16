package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.SlickException;

import roles.Personnage;

public class GJoueur implements Observer{
	private ArrayList<TypeUnit> _type_unit;
	private int _ressource;

	private ArrayList<Player> _players = new ArrayList<Player>();

	public GJoueur(ArrayList<TypeUnit> type_unit)
	{
		_type_unit = type_unit;
		_ressource = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Integer)
			_ressource = (Integer)arg;
		if(arg instanceof Personnage)
			addPersonnage((Personnage)arg);
	}

	public void addPersonnage(Personnage pers)
	{
		_players.add(new Player(pers, _type_unit.get(pers.owner().getUnite(pers))));
	}

	public int ressource()
	{
		return _ressource;
	}

	public TypeUnit type_unit(int i)
	{
		return _type_unit.get(i);
	}

	public ArrayList<Player> getPersonnage() {
		return _players;
	}
}
