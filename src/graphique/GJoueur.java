package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.SlickException;

import roles.Personnage;

public class GJoueur implements Observer{
	private TypeUnit _type_unit;
	private int _ressource;

	private ArrayList<Player> _players = new ArrayList<Player>();

	public GJoueur(TypeUnit type_unit)
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
		_players.add(new Player(pers, _type_unit));
	}

	public int ressource()
	{
		return _ressource;
	}

	public TypeUnit type_unit()
	{
		return _type_unit;
	}

	public ArrayList<Player> getPersonnage() {
		return _players;
	}
}
