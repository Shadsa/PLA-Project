package roles;

import java.util.Observable;

import cases.Case;
import cases.CaseProperty;
import roles.States.Statut;
import roles.action.Joueur;
import roles.action.World;
import roles.classe.Classe;

public class Personnage extends Observable{

	Automate _brain;
	protected int _paralysie = 0;
	protected Case _location;
	protected Classe _classe;
	private int _etat;
	private int _vie;
	private int _damage;
	private int _heal;
	private int _armor;
	private Cardinaux _directionJoueur = null;
	private Case _cible;

	protected int _id;
	protected Joueur _owner;

	private static int _nextID = 0;
	protected static int nextID()
	{
		return _nextID++;
	}
	public int ID()
	{
		return _id;
	}


	public Cardinaux directionJoueur() {
		return _directionJoueur;
	}

	public void setDirection(Cardinaux direction) {
		this._directionJoueur = direction;
	}

	public Personnage(Automate brain, int x, int y, Joueur owner,Classe classe)
	{
		_etat = 0;
		_vie = 10;
		_id = nextID();
		_brain = brain;
		_paralysie = 0;
		_owner = owner;
		_classe = classe;
		_vie = _classe.HP();
		_damage = _classe.damage();
		_heal = _classe.heal();		
		_armor = _classe.armor();
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

	public void paralyse() {
		System.out.println("je n'ai rien à faire."+this.etat());
		_paralysie++;
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

	public Classe classe(){
		return _classe;
	}
	public int damage(){
		return _damage;
	}
	public int heal(){
		return _heal;
	}	
	public int vie(){
		return _vie;
	}
	public int armor(){
		return _armor;
	}
	public Joueur owner() {
		return _owner;
	}
	
	public Case cible() {
		return _cible;
	}
	public void setCible(Case c) {
		_cible = c;
	}
	/**
	 * 
	 * @param c : la propriété de la case à trouver
	 * @param maxRange : la distance à laquelle regarder
	 * @return la direction vers laquelle se trouve la case la plus proche, ou null si aucune case n'a été trouvée
	 */
	public boolean find(CaseProperty c, Cardinaux direction, int maxRange){	
		switch(direction){
		case EST:
			for(int range=1;range<=maxRange;range++)
				for(int xy=-range;xy<=range;xy++)
					if(World.Case(X()+range, Y()+xy)!=null && c.check(World.Case(X()+range, Y()+xy)))
						return true;
		case OUEST:
			for(int range=1;range<=maxRange;range++)
				for(int xy=-range;xy<=range;xy++)
					if(World.Case(X()-range, Y()+xy)!=null && c.check(World.Case(X()-range, Y()+xy)))
						return true;
		case SUD:
			for(int range=1;range<=maxRange;range++)
				for(int xy=-range;xy<=range;xy++)
					if(World.Case(X()+xy, Y()+range)!=null && c.check(World.Case(X()+xy, Y()+range)))
						return true;
		case NORD:
			for(int range=1;range<=maxRange;range++)
				for(int xy=-range;xy<=range;xy++)
					if(World.Case(X()+xy, Y()-range)!=null && c.check(World.Case(X()+xy, Y()-range)))
						return true;
		}
		return false;
	}

	public void change_vie(int delta)
	{
		if(delta <0){
			delta = _armor + delta;
			if(delta>=0){delta =1;}
		}

		_vie += delta;
		if(_vie <= 0)
		{
			_vie = 0;
			setChanged();
			notifyObservers(new States(Statut.MORT));
			_owner.getPersonnages().remove(this);
			_location.setPersonnage(null);;
		}
		else
			if(_vie > _classe.HP())
				_vie = _classe.HP();
	}
}
