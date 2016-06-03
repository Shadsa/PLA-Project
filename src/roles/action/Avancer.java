package roles.action;

import roles.Cardinaux;
import roles.Personnage;

public class Avancer extends Action {

	Cardinaux _direction;

	public Avancer(Personnage pers, Cardinaux card, int poids) {
		super(pers, poids);
		_direction = card;
	}

	@Override
	public void Act() {
		int destX = _personnage.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));
		int destY = _personnage.Y() + ((_direction == Cardinaux.SUD)? (-1) : ((_direction == Cardinaux.NORD)? 1 : 0));
		if(World.isfree(destX, destY))
		{
			World.Case(destX, destY).setPersonnage(_personnage);
			World.Case(_personnage.X(), _personnage.Y()).setPersonnage(null);
		}
	}

}
