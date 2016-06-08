package cases;

import roles.Personnage;
import roles.action.Action;

public class CaseAction extends Case {
	public CaseAction(Action type) {
		super(0, 0);
		_type = type;
	}

	Action _type;
	
	public void setPosition(int x, int y){
		_x = x;
		_y = y;
	}

	@Override
	public int value() {
		return firstCaseActionId + _type.toInt();
	}
	
	public void Act(Personnage pers){
		_type.Act(pers);
	}

	public int poids() {
		return _type.poids();
	}

	@Override
	public Case modifierCase(Class<? extends Case> c) {
		if(CaseAction.class.isAssignableFrom(c)){
			
		}
		return this;
	}
}
