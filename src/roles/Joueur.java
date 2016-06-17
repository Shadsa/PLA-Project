package roles;

import java.util.ArrayList;
import java.util.Observable;

import graphique.TypeUnit;
import roles.classe.Classe;

public class Joueur extends Observable{

	private String _nom;
	ArrayList<Automate> _automates;
	ArrayList<Classe> _classes; //indicage sur celui de Automate pour la cores'
	private int _ressources;



	public Joueur(String nom, ArrayList<Automate> automates, ArrayList<Classe> classes)
	{
		_nom = nom;
		_automates = automates;
		_classes = classes;
		_ressources = 0;
	}

	public int getUnite(Personnage pers){
		return _automates.indexOf(pers._brain);
	}

	public int ressources(){
		return _ressources;
	}

	public Automate automate(int i){
		return _automates.get(i);
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

	public Classe classe(int type) {
		return _classes.get(type);
	}

	public TypeUnit type_unit() {
		// TODO Auto-generated method stub
		return null;
	}
}
