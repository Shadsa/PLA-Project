package roles;

import java.util.ArrayList;
import java.util.Vector;

import cases.Case;
import cases.CaseAction;
import cases.Plaine;
import roles.action.World;

public class Carte extends Vector<Vector<Case>>{

	public Carte(int hauteur,int largeur){
		super(hauteur);
		for(int y=0 ; y<hauteur ; y++){
			Vector<Case> ligne = new Vector<Case>(largeur);
			for(int x=0 ; x<largeur ; x++){
				ligne.add(new Plaine(y,x));
			}
			add(ligne);
		}
	}
	
	public Case Case(int x, int y) {
		return (x < 0 || y < 0 || x >= size() || y >= get(x).size())? null : get(x).get(y);
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
