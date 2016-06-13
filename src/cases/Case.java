package cases;

import java.util.Observable;

import roles.Personnage;

public abstract class Case extends Observable{

	Personnage _personnage;
	protected int _x;
	protected int _y;
	protected TypeCase _type;
	protected int _vie;

	Case(int x, int y, TypeCase type)
	{
		_x = x;
		_y = y;
		_type = type;
		if(type instanceof Arbre)
			_vie=((Arbre) type)._vie;
		else
			if(type instanceof Mur)
				_vie=((Mur) type)._vie;		
			else
				_vie=1;
	}

	public Boolean isfree() {
		return _type.franchissable()&&_personnage == null;
	}

	public void setPersonnage(Personnage personnage) {
		// WARNING si non vide
		_personnage = personnage;
		if(personnage != null)
		{
			if(personnage.Case() != null)
				personnage.Case()._personnage = null;
			personnage.setCase(this);
		}
	}
	
	public void attaquerCase(int force){
		_vie-=force;
		if(_vie<=0){
			modifierCase(Plaine.getInstance());
			_vie=1;
		}
	}
	
	public void modifierCase(TypeCase type){
		_type = type;
		if(type instanceof Arbre)
			_vie=((Arbre) type)._vie;		
		else
			if(type instanceof Mur)
				_vie=((Mur) type)._vie;		
			else
				_vie=1;
		setChanged();
		notifyObservers(value());
	}

	public int value(){
		return _type.value();
	}

	public int X()
	{
		return _x;
	}

	public int Y()
	{
		return _y;
	}
	

	public TypeCase type() {
		return _type;
	}


	public Personnage Personnage() {
		return _personnage;
	}
}
