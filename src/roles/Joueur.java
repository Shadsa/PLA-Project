package roles;

import java.util.ArrayList;
import java.util.Observable;

import graphique.TypeClothes;
import graphique.TypeUnit;
import roles.classe.Classe;

public class Joueur extends Observable{

	private String _nom;
	ArrayList<Automate> _automates;
	ArrayList<Classe> _classes; //indicage sur celui de Automate pour la cores'
	ArrayList<TypeUnit> _type_unit;
	ArrayList<TypeClothes> _type_clothes;
	private int _ressources;



	public Joueur(String nom, ArrayList<Automate> automates, ArrayList<Classe> classes, ArrayList<TypeUnit> type_unit, ArrayList<TypeClothes> type_clothes)
	{
		_nom = nom;
		_automates = automates;
		_classes = classes;
		_type_unit = type_unit;
		_type_clothes = type_clothes;
		_ressources = 0;
	}

	public int getUnite(Personnage pers){
		return _automates.indexOf(pers._brain);
	}
	
	public TypeUnit getType(Personnage pers){
		return _type_unit.get(getUnite(pers));
	}
	
	public TypeClothes getClothes(Personnage pers){
		return _type_clothes.get(getUnite(pers));
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
