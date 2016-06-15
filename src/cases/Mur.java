package cases;

import roles.Personnage;
import roles.action.Action;
import roles.action.Attendre;
import roles.Joueur;

public class Mur extends TypeCase implements Construction {

	public final int _vie = 10;

	protected static Action _action = new Attendre();

	protected Joueur _owner;

	public final static int _id = getId(37);

	public int value() {
		return _id;
	}

	public Mur(Personnage pers) {
		setOwner(pers.owner());
	}

	@Override
	public boolean franchissable() {
		return false;
	}

	public void setOwner(Joueur owner){
		_owner = owner;
	}

	public Joueur getOwner(){
		return _owner;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers.world(), pers);
	}

}
