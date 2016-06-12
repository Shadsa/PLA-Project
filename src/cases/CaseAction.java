package cases;

import roles.Personnage;
import roles.action.Action;
import roles.action.Joueur;

public class CaseAction extends Case {

	Batiment _initial;
	Joueur _owner;
	
	public CaseAction(Batiment type) {
		super(0, 0, type);
		_initial = type;
		_owner = null;
	}

	public void setCase(int x, int y, Joueur j){
		_x = x;
		_y = y;
		_owner = j;
	}

	@Override
	public int value() {
		return _type.value();
	}
	
	public void Act(Personnage pers){
		_type.Act(pers);
	}

	public int poids() {
		return 1;
	}
	
	public Action action(){
		return _initial.action();
	}
	
	public Joueur owner() {
		return _owner;
	}
	
	public Batiment etatInitial(){
		return _initial;
	}
}
