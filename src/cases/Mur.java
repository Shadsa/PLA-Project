package cases;

import roles.Cardinaux;
import roles.Personnage;
import roles.action.Action;
import roles.action.Attaquer;
import roles.action.Joueur;

public class Mur extends TypeCase implements Construction {
	
	public final int _vie = 1;

	protected static Action _action = new Attaquer(Cardinaux.EST);
	
	private static Mur _instance = new Mur();
	
	protected Joueur _owner;
	
	public final static int _id = getId(37);
	
	public int value() {
		return _id;
	}

	@Override
	protected void Act(Personnage pers) {
		_action.Act(pers);
		setOwner(pers.owner());
	}

	public static TypeCase getInstance() {
		return _instance;
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

}
