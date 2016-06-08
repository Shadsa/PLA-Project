package cases;

import roles.Personnage;
import roles.action.Action;

public class CaseAction extends Case {

	
	CaseAction(int x, int y, TypeCase type) {
		super(x, y, type);
	}

	public void setPosition(int x, int y){
		_x = x;
		_y = y;
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

}
