package graphique;

import java.util.Observable;
import java.util.Observer;

import cases.Case;

public class ObjetTest  implements Observer {

	private int num;

	public ObjetTest(int num) {
		this.num = num;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(Integer arg1) {
		num = arg1;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Integer)
			setNum((Integer)arg1);
	}
}
