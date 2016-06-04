package roles;

import cases.Case;
import roles.action.World;

public class Personnage {

	Automate _brain;
	protected int _parralysie = 0;
	protected Case _location;

	public Personnage(Automate brain, int x, int y)
	{
		_brain = brain;
		_parralysie = 0;
		World.Case(x, y).setPersonnage(this);
	}

	public int X() {
		return _location.X();
	}

	public int Y() {
		return _location.Y();
	}

	public void agir() {
		_brain.agir(this);
	}

	public void parralyse() {
		System.out.print("je n'ai rien à faire.\n");
		_parralysie++;
	}

	public void setCase(Case loc) {
		_location = loc;
	}

}
