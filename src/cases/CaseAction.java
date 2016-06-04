package cases;

import roles.action.Action;

public class CaseAction extends Case {
	CaseAction(int x, int y) {
		super(x, y);
	}

	Action _type;

	@Override
	public int value() {
		return firstCaseActionId + _type.toInt();
	}

}
