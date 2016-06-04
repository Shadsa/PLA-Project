package roles;

public class Personnage {

	Automate _brain;
	int _x;
	int _y;

	Personnage(Automate brain, int x, int y)
	{
		_brain = brain;
		_x = x;
		_y = y;
	}

	public int X() {
		return _x;
	}

	public int Y() {
		return _y;
	}

	public void agir() {
		_brain.agir(this);
	}

}
