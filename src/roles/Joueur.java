package roles;

import java.util.ArrayList;
import java.util.Observable;

import roles.classe.Classe;

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

	public int getUnite(Personnage pers){
		return _automates.indexOf(pers._brain);
	}

	public Personnage createPersonnage(int type, int x, int y)
	{
		// WARNING faire plutot un get automate avec gestion d'erreur
		if(type<=_automates.size()){
			Personnage newPers = new Personnage(_automates.get(type), x, y, this,_classes.get(type));
			_personnages.add(newPers);
			setChanged();
			notifyObservers(newPers);
			return newPers;
		}
		return null;
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
