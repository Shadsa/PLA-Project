package roles.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import cases.Case;
import cases.Plaine;
import cases.TypeCase;
import graphique.ObjetTest;
import roles.Automate;
import roles.Carte;
import roles.Personnage;
import roles.classe.Classe;

public abstract class World {

	static Carte _map;
	static ArrayList<Joueur> _joueurs = new ArrayList<Joueur>();
	public static ArrayList<Classe> classes = new ArrayList<Classe>();

	public static void addPlayer(Joueur j)
	{
		_joueurs.add(j);
	}

	// final return...
	public static ArrayList<Joueur> getPlayers()
	{
		return _joueurs;
	}

	public static Boolean isfree(int x, int y) {
		return _map.isfree(x, y);
	}

	public static Case Case(int x, int y) {
		return _map.Case(x, y);
	}
	
	public static Case randomCase(){
		Random R = new Random();
		int x = R.nextInt(_map.largeur());
		int y = R.nextInt(_map.hauteur());
		return Case(x,y);
	}
	
	public static void nextTurn()
	{
		ArrayList<Joueur> vaincus = new ArrayList<Joueur>();
		ArrayList<Personnage> activated = new ArrayList<Personnage>();
		for(Joueur j : _joueurs){
			if(j.getPersonnages().isEmpty()){
				vaincus.add(j);
				System.out.print(j._nom+" a perdu!");
			}
			else
				for(Personnage p : j.getPersonnages() )
					activated.add(p);
		}
		for(Joueur j : vaincus){
			_joueurs.remove(j);
		}

		Collections.shuffle(activated);
		for(Personnage p : activated)
			if(p.vie()>0)
				p.agir();
	}

	public static void BuildMap(int hauteur, int largeur) {
		_map = new Carte(hauteur,largeur);
	}

	public static int SizeX() {
		return _map.get(0).size();
	}

	public static void modifierCase(TypeCase type, int x, int y){
		_map.modifierCase(type, x, y);
	}

	public static int SizeY() {
		return _map.size();
	}

	public static void putAutomate(Automate a, int x, int y, Joueur j) throws Exception{
		_map.putAutomate(a, x, y, j);
	}
}
