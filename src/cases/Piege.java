package cases;

import roles.Personnage;
import roles.World;
import roles.action.Action;
import roles.action.Attendre;
import roles.Joueur;

public class Piege extends TypeCase implements Construction {
	
	public final int _vie = 1;

	protected static Action _action = new Attendre();
	
	protected Joueur _owner;
	
	public final static int _id = getId(29);
	
	public int value() {
		return _id;
	}

	public Piege(Personnage pers) {
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
	 
	@Override
	public void Evenement(Personnage pers) {
		if(_owner != pers.owner()){
			pers.change_vie(-1000);
			World.modifierCase(Plaine.getInstance(), pers.X(), pers.Y());
		}
	}

}