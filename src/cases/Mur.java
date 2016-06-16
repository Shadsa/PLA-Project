package cases;

import roles.Personnage;
import roles.action.Action;
import roles.action.Attendre;
import roles.action.ConstruireMur;
import roles.Cardinaux;
import roles.Joueur;

public class Mur extends TypeCase implements Construction {
	
	public final int _vie = 20;

	protected static Action _action = new ConstruireMur(Cardinaux.NORD);
	
	protected Joueur _owner;
	
	public final static int _id = getId(1);
	
	public int value() {
		return _id;
	}

	public Mur(Personnage pers) {
		setOwner(pers.owner());
	}

	@Override
	public boolean franchissable() {
		return true;
	}
	
	public void setOwner(Joueur owner){
		_owner = owner;
	}
	
	public Joueur getOwner(){
		return _owner;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers);
	}

	public Action action() {
		return _action;
	}
	
}
