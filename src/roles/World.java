package roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cases.Case;
import cases.TypeCase;
import roles.classe.Classe;

public class World {
	public static ArrayList<Classe> classes = new ArrayList<Classe>();
	public static ArrayList<World> Univers = new ArrayList<World>();
	public static ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

	Carte _map;
	private ArrayList<Army> _army = new ArrayList<Army>();
	public boolean fini = false;
	private boolean _isArena;

	public World(int hauteur, int largeur, Boolean isArena)
	{
		_map = new Carte(hauteur,largeur);
		_isArena = isArena;
	}

	public void addArmy(Army a)
	{
		_army.add(a);
	}

	// final return...
	public ArrayList<Army> getArmys()
	{
		return _army;
	}

	public Boolean isfree(int x, int y) {
		return _map.isfree(x, y);
	}

	public Case Case(int x, int y) {
		return _map.Case(x, y);
	}

	public Case randomCase(){
		Random R = new Random();
		int x = R.nextInt(_map.largeur());
		int y = R.nextInt(_map.hauteur());
		return Case(x,y);
	}

	public void nextTurn()
	{
		ArrayList<Army> vaincus = new ArrayList<Army>();
		ArrayList<Personnage> activated = new ArrayList<Personnage>();
		for(Army a : _army){
			if(a.getPersonnages().isEmpty()){
				vaincus.add(a);
				System.out.println(a.joueur().nom()+" a perdu la bataille!");
			}
			else
				for(Personnage p : a.getPersonnages() )
					activated.add(p);
		}

		for(Army a : vaincus){
			_army.remove(a);
		}
		if(_army.size()<2){
			fini = true;
		}

		Collections.shuffle(activated);
		for(Personnage p : activated)
			if(p.vie()>0)
				p.agir();
	}

	public int SizeX() {
		return _map.get(0).size();
	}

	public void modifierCase(TypeCase type, int x, int y){
		_map.modifierCase(type, x, y);
	}

	public int SizeY() {
		return _map.size();
	}

	public Carte map() {
		return _map;
	}
	
	public void setMap(Carte map) {
		_map = map;
	}

	public void putAutomate(Automate a, int x, int y, Joueur j) throws Exception{
		_map.putAutomate(a, x, y, j);
	}
	
	public void putAutomates(ArrayList<Automate> a, int x, int y, Joueur j) throws Exception{
		_map.putAutomates(a, x, y, j);
	}

	public ArrayList<Army> army() {
		return _army;
	}
	
	public static void resetJoueurs() {
		joueurs.clear();
	}
	
	public void switchCase(Case c1, Case c2){
		_map.switchCase(c1, c2);
	}

	public boolean isArena() {
		return _isArena;
	}

}
