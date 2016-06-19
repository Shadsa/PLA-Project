package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.SlickException;

import roles.Personnage;

public class GJoueur implements Observer{

    @Override
    public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	
    }
	/*private ArrayList<TypeUnit> _type_unit;
	private ArrayList<TypeClothes> _type_clothes;
	
	private int _ressource;


	public GJoueur(ArrayList<TypeUnit> type_unit, ArrayList<TypeClothes> type_clothes)
	{
		_type_unit = type_unit;
		_type_clothes = type_clothes;
		_ressource = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Integer)
			_ressource = (Integer)arg;
	}

	public void addPersonnage(Personnage pers)
	{
		_players.add(new Player(pers, _type_unit.get(pers.owner().getUnite(pers)), _type_clothes.get(pers.owner().getUnite(pers))));
	}

	public int ressource()
	{
		return _ressource;
	}

	public TypeUnit type_unit(int i)
	{
		return _type_unit.get(i);
	}
	
	public TypeClothes type_clothes(int i)
	{
		return _type_clothes.get(i);
	}*/
}
