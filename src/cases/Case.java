package cases;

import java.util.Observable;

import roles.Personnage;

public abstract class Case extends Observable{

	Personnage _personnage;
	protected int _x;
	protected int _y;
	protected TypeCase _type;

	Case(int x, int y, TypeCase type)
	{
		_x = x;
		_y = y;
		_type = type;
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
	
	public void modifierCase(TypeCase type){
		_type = type;
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

	public Personnage Personnage() {
		return _personnage;
	}
}
