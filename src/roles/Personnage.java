package roles;

import java.util.Observable;

import cases.Case;
import roles.States.Statut;
import roles.action.World;

public class Personnage extends Observable{

	Automate _brain;
	protected int _parralysie = 0;
	protected Case _location;
	int _etat;

	protected int _id;

	private static int _nextID = 0;
	protected static int nextID()
	{
		return _nextID++;
	}
	public int ID()
	{
		return _id;
	}

	public Personnage(Automate brain, int x, int y)
	{
		_etat = 0;
		_id = nextID();
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

	public int etat()
	{
		return _etat;
	}

	public void parralyse() {
		System.out.print("je n'ai rien à faire.\n");
		_parralysie++;
	}

	public void setCase(Case loc) {
		_location = loc;
	}
	public Case Case() {
		return _location;
	}
	public void setetat(Integer etat) {
		_etat = etat;
	}
	public void setState(States states) {
		setChanged();
		notifyObservers(states);
	}

}
