import java.util.ArrayList;

import roles.Automate;
import roles.Cardinaux;
import roles.action.Avancer;
import roles.action.Joueur;
import roles.action.World;
import roles.conditions.Libre;

public class test {

	public static void main(String[] args) {
		Automate aut1 = new Automate(1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.NORD, 0), new Libre(Cardinaux.NORD), 0);
		ArrayList<Automate> autlist = new ArrayList<Automate>();
		autlist.add(aut1);
		Joueur j = new Joueur("Moi", autlist);
		World.addPlayer(j);
		World.BuildMap();
		j.createPersonnage(0, 5, 5);
		for(int i = 0; i<10; i++) World.nextTurn();
		System.out.print("done");
	}

}