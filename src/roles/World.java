package roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cases.Case;
import cases.TypeCase;
import roles.classe.Classe;

public abstract class World {

	//static Carte _map;
	static ArrayList<Carte> _maps = new ArrayList<Carte>();
	private static ArrayList<Joueur> _joueurs = new ArrayList<Joueur>();
	public static ArrayList<Classe> classes = new ArrayList<Classe>();
	public static boolean fini = false;

	public static void addPlayer(Joueur j)
	{
		joueurs().add(j);
	}

	// final return...
	public static ArrayList<Joueur> getPlayers()
	{
		return joueurs();
	}

	public static Boolean isfree(int w, int x, int y) {
		return _maps.get(w).isfree(x, y);
	}

	public static Case Case(int w, int x, int y) {
		return _maps.get(w).Case(x, y);
	}

	public static Case randomCase(int w){
		Random R = new Random();
		int x = R.nextInt(_maps.get(w).largeur());
		int y = R.nextInt(_maps.get(w).hauteur());
		return Case(w,x,y);
	}

	public static void nextTurn()
	{
		ArrayList<Joueur> vaincus = new ArrayList<Joueur>();
		ArrayList<Personnage> activated = new ArrayList<Personnage>();
		for(Joueur j : joueurs()){
			if(j.getPersonnages().isEmpty()){
				vaincus.add(j);
				System.out.print(j.nom()+" a perdu!");
			}
			else
				for(Personnage p : j.getPersonnages() )
					activated.add(p);
		}

		for(Joueur j : vaincus){
			joueurs().remove(j);
		}
		if(joueurs().size()==1){
			fini = true;
		}

		Collections.shuffle(activated);
		for(Personnage p : activated)
			if(p.vie()>0)
				p.agir();
	}

	public static void BuildMap(int hauteur, int largeur) {
		_maps.add(new Carte(hauteur,largeur));
	}

	public static int SizeX(int w) {
		return _maps.get(w).get(0).size();
	}

	public static void modifierCase(int w, TypeCase type, int x, int y){
		_maps.get(w).modifierCase(type, x, y);
	}

	public static int SizeY(int w) {
		return _maps.get(w).size();
	}

	public static void putAutomate(int w, Automate a, int x, int y, Joueur j) throws Exception{
		_maps.get(w).putAutomate(a, x, y, j);
	}

	public static ArrayList<Joueur> joueurs() {
		return _joueurs;
	}

}
