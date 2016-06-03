package roles.action;

import roles.Personnage;

public class Gauche extends Action {

	public Gauche(Personnage pers) {
		super(pers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Act() {
		World.isfree(_personnage.X(), _personnage.Y());
	}

}
