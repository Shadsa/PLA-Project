package roles;

import java.util.ArrayList;
import java.util.Observable;

import roles.classe.Classe;

public class Army extends Observable{

	protected Joueur _joueur;
	protected ArrayList<Personnage> _personnages;
	protected World _world;

	public Army(World world, Joueur j)
	{
		_joueur = j;
		_personnages = new ArrayList<Personnage>();
		_world = world;
		world.addArmy(this);
	}

	public Joueur joueur() {
		return _joueur;
	}

	public ArrayList<Personnage> getPersonnages() {
		return _personnages;
	}

	public Personnage createPersonnage(int type, int x, int y)
	{
		// WARNING faire plutot un get automate avec gestion d'erreur
		Personnage newPers = new Personnage(_joueur.automate(type), x, y, this, _joueur.classe(type));
		_personnages.add(newPers);
		setChanged();
		notifyObservers(newPers);
		return newPers;
	}

	public int nbUnit(Classe c){
		int count=0;
		for(Personnage p : _personnages)
			if(p.classe()==c)
				count++;
		return count;
	}

	public int ratioUnit(Classe c){
		return 100*(nbUnit(c)/_personnages.size());
	}

	public World world() {
		return _world;
	}

}
