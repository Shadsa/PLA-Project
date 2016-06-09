package graphique;

import java.util.Observable;
import java.util.Observer;

public class Joueur implements Observer{
	private TypeUnit _type_unit;
	private int _ressource;

	public Joueur(TypeUnit type_unit)
	{
		_type_unit = type_unit;
		_ressource = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Integer)
			_ressource = (Integer)arg;
	}

	public int ressource()
	{
		return _ressource;
	}

	public TypeUnit type_unit()
	{
		return _type_unit;
	}
}
