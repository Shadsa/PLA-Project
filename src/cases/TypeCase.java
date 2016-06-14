package cases;

import roles.Personnage;
import roles.action.Action;
import roles.action.Joueur;

public abstract class TypeCase {

	final public static int firstCaseActionId = 1000;

	//Réserver 0 pour les bâtiments de la ville ?
	private static int _nextId = 1;
	
	protected Action _action;
	
	
	protected abstract void Act(Personnage pers);
	
	// @ensure range>0
	protected static int getId(int range)
	{
		_nextId += range;
		return _nextId - range;
	}
	
	public abstract int value();
	
	public abstract boolean franchissable();
	
	public TypeCase string_to_type(String type){
		switch(type){
		case "Arbre" : return Arbre.getInstance();
		case "Mur" : return Mur.getInstance();
		case "Caillou" : return Caillou.getInstance();
		case "Eau" : return Eau.getInstance();
		case "Plaine" : return Plaine.getInstance();
		default : return null;
		}
	}
	
}
