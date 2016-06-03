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
	}

	public ArrayList<Personnage> getPersonnages() {
		return _personnages;
	}

}
