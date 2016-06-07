package roles;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import cases.*;
import roles.action.World;

public class Carte extends Vector<Vector<Case>>{

	public Carte(int hauteur,int largeur){
		super(hauteur);
		Random R = new Random();
		int type;
		for(int y=0 ; y<hauteur ; y++){
			Vector<Case> ligne = new Vector<Case>(largeur);
			for(int x=0 ; x<largeur ; x++){
				type = R.nextInt(5);
				switch(type){
				case 0 : case 1 :
					ligne.add(new Plaine(x,y));
					System.out.print('P');
					break;
				case 2 :
					ligne.add(new Arbre(x,y));
					System.out.print('A');
					break;
				case 3 :
					ligne.add(new Caillou(x,y));
					System.out.print('C');
				case 4 :
					ligne.add(new Eau(x,y));
					System.out.print('E');
				}
			}
			add(ligne);
			System.out.print('\n');
		}
	}

	public Case Case(int x, int y) {
		return (y < 0 || x < 0 || y >= size() || x >= get(y).size())? null : get(y).get(x);
	}

	public Boolean isfree(int x, int y) {
		return Case(x, y) != null && Case(x, y).isfree();
	}

	/**
	 *
	 * @param c : la case à placer
	 * @param x : abscisse où placer la case c
	 * @param y : ordonnée où placer la case c
	 * @throws Exception : si cette case n'existe pas
	 */
	public void putCaseAction(CaseAction c, int x, int y) throws Exception {
		if(x < 0 || y < 0 || x >= size() || y >= get(x).size())
			throw new Exception("Dépassement de la carte");
		c.setPosition(x, y);
		get(y).set(x, c);
	}

	/**
	 *
	 * @param a : automate à placer
	 * @param x : abscisse où placer l'automate
	 * @param y : ordonnée où placer l'automate
	 * @throws Exception : si on ne peut placer l'automate à cette position
	 */
	public void putAutomate(Automate a, int x, int y) throws Exception {
		for(ArrayList<CaseAction> ligne : a.get_action())
			for(CaseAction c : ligne)
				try {
					putCaseAction(c,x++,y++);
				} catch (Exception e) {
					throw new Exception("Impossible de placer l'automate");
				}
	}
}
