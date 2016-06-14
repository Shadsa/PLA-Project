package roles.action;

import java.util.ArrayList;
import java.util.Observable;

import roles.Automate;
import roles.Personnage;
import roles.classe.Classe;
import roles.Cardinaux;

public class Joueur extends Observable{

	private String _nom;
	ArrayList<Personnage> _personnages;
	ArrayList<Automate> _automates;
	ArrayList<Classe> _classes; //indicage sur celui de Automate pour la cores'
	private int _ressources;



	public Joueur(String nom, ArrayList<Automate> automates, ArrayList<Classe> classes)
	{
		_nom = nom;
		_automates = automates;
		_classes = classes;
		_ressources = 0;
		_personnages = new ArrayList<Personnage>();
	}

	public ArrayList<Personnage> getPersonnages() {
		return _personnages;
	}

	public Personnage createPersonnage(Classe type, int x, int y)
	{
		// WARNING faire plutot un get automate avec gestion d'erreur
		Personnage newPers = new Personnage(_automates.get(_classes.indexOf(type)), x, y, this,type);
		_personnages.add(newPers);
		setChanged();
		notifyObservers(newPers);
		return newPers;
	}

	public int ressources(){
		return _ressources;
	}
	
	public Automate automate(int i){
		return _automates.get(0);
	}

	public boolean changerRessource(int modificateur) {
		if(_ressources+modificateur>=0){
			_ressources+=modificateur;
			return true;
		}
		return false;
	}

	public String nom() {
		return _nom;
	}
}
