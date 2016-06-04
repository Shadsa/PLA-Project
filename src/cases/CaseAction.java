package cases;

import roles.action.Action;

public class CaseAction extends Case {
	Action _type;

	@Override
	public int value() {
		return firstCaseActionId + _type.toInt();
	}

}
