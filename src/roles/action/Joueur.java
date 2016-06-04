package roles.action;

import java.util.ArrayList;

import roles.Automate;
import roles.Personnage;

public class Joueur {

	String _nom;
	ArrayList<Personnage> _personnages;
	ArrayList<Automate> _automates;

	public Joueur(String nom, ArrayList<Automate> automates)
	{
		_nom = nom;
		_automates = automates;
		_personnages = new ArrayList<Personnage>();
	}

	public ArrayList<Personnage> getPersonnages() {
		return _personnages;
	}

	public void createPersonnage(int type, int x, int y)
	{
		// WARNING faire plutot un get automate avec gestion d'erreur
		_personnages.add(new Personnage(_automates.get(type), x, y));
	}
}
