package roles.action;

import java.util.ArrayList;
import java.util.Observable;

import roles.Automate;
import roles.Personnage;
import roles.classe.Classe;
import roles.Cardinaux;

public class Joueur extends Observable{

	String _nom;
	ArrayList<Personnage> _personnages;
	ArrayList<Automate> _automates;
	ArrayList<Classe> _classes; //indicage sur celui de Automate pour la cores'



	public Joueur(String nom, ArrayList<Automate> automates,ArrayList<Classe> classes)
	{
		_nom = nom;
		_automates = automates;
		_classes = classes;
		_personnages = new ArrayList<Personnage>();
	}

	public ArrayList<Personnage> getPersonnages() {
		return _personnages;
	}

	public Personnage createPersonnage(int type, int x, int y)
	{
		// WARNING faire plutot un get automate avec gestion d'erreur
		Personnage newPers = new Personnage(_automates.get(type), x, y, this,_classes.get(type));
		_personnages.add(newPers);
		return newPers;
	}
}
