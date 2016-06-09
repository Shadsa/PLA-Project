package roles.action;

import cases.Arbre;
import cases.Plaine;
import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public final class CouperBois extends Action {

	Cardinaux _direction;
	
	private static int _Id = Action.getId(1);

	@Override
	public void Act(Personnage pers) {
		if(World.Case(pers.X(), pers.Y()).type().value() == Arbre.getInstance().value()){
			World.modifierCase(Plaine.getInstance(), pers.X(), pers.Y());
			pers.setState(new States(Statut.ATTAQUE, Cardinaux.EST));
			pers.owner().changerRessource(10);
		}
	}

	@Override
	public int toInt() {
		return _Id;
	}

}
