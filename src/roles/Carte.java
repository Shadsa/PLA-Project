package roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import cases.*;

public class Carte extends Vector<Vector<Case>> implements Serializable{

	private static final long serialVersionUID = -8840331548046134782L;
	private int _hauteur;
	private int _largeur;
	
	public int hauteur() {
		return _hauteur;
	}

	public int largeur() {
		return _largeur;
	}


	public Carte(int hauteur,int largeur){
		super(hauteur);
		_hauteur = hauteur;
		_largeur = largeur;
		Random R = new Random();
		int type;
		Case c;
		for(int y=0 ; y<hauteur ; y++){
			Vector<Case> ligne = new Vector<Case>(largeur);
			for(int x=0 ; x<largeur ; x++){
				type = R.nextInt(20);
				switch(type){
				case 0 :
					c = new Terrain(x,y,Caillou.getInstance());
					//System.out.print('C');
					break;
				default :
					c = new Terrain(x,y,Plaine.getInstance());
					//System.out.print('D');
				}
				//NE PAS OUBLIER LES BREAKS QUAND ON RAJOUTE UN TYPE DE CASE
				ligne.add(c);
			}
			add(ligne);
		}
		this.randomLac();
		this.randomForet();
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
	public void putCaseAction(CaseAction c, int x, int y, Joueur j) throws Exception {
		if(x < 0 || y < 0 || y >= size() || x >= get(y).size())
			throw new Exception("Dépassement de la carte");
		c.setCase(x, y, j);
		get(y).set(x, c);
	}

	public void putCase(Case c) throws Exception {
		int x = c.X();
		int y = c.Y();
		if(x < 0 || y < 0 || y >= size() || x >= get(y).size())
			throw new Exception("Dépassement de la carte");
		get(y).set(x, c);
	}
	
	/**
	 *
	 * @param a : automate à placer
	 * @param x : abscisse où placer l'automate
	 * @param y : ordonnée où placer l'automate
	 * @throws Exception : si on ne peut placer l'automate à cette position
	 */
	public void putAutomate(Automate a, int x, int y, Joueur j) throws Exception {
		int xdeb = x, dim=0;
		ArrayList<CaseAction> action_list = new ArrayList<CaseAction>();
		for(ArrayList<CaseAction> ligne : a.get_action())
			for(CaseAction c : ligne){
				action_list.add(c);
				dim++;
			}
		dim = x + (int) Math.sqrt(dim);
		for(CaseAction c : action_list){
			try {
				putCaseAction(c,x++,y,j);
			} catch (Exception e) {
				throw new Exception("Impossible de placer l'automate");
			}
			if(x>dim){
				y++;
				x=xdeb;
			}
		}
	}
	
	public void putAutomates(ArrayList<Automate> A, int x, int y, Joueur j) throws Exception {
		int xdeb, dim=0;
		ArrayList<CaseAction> action_list = new ArrayList<CaseAction>();
		for(Automate a : A)
			for(ArrayList<CaseAction> ligne : a.get_action())
				for(CaseAction c : ligne){
					action_list.add(c);
					dim++;
				}
		dim = (int) Math.sqrt(dim);
		if (x+dim >= _largeur)
			x = _largeur-(dim+1)-1;
		xdeb = x;
		if (y+dim >= _hauteur)
			y = _hauteur-(dim+1)-1;
		dim += x;
		for(CaseAction c : action_list){
			try {
				putCaseAction(c,x++,y,j);
			} catch (Exception e) {
				throw new Exception("Impossible de placer l'automate case : "+x+","+y);
			}
			if(x>dim){
				y++;
				x=xdeb;
			}
		}
	}

	public void modifierCase(TypeCase type, int x, int y){
		Case(x,y).modifierCase(type);
	}
	
	private void putForet(int x, int y, int facteur){
		if(Case(x,y)!=null && (x>0 || y>_hauteur/2) && (x<_largeur-1 || y<_hauteur/2) && (y>0 || x>_largeur/2) && (y<_hauteur-1 || x<_largeur/2) && Case(x,y).type().franchissable() && Case(x,y).type().value() != Arbre.getInstance().value()){
			Random R = new Random();
			this.modifierCase(Arbre.getInstance(), x, y);
			if(facteur==1){
				if(R.nextInt(3)>1){
					putForet(x-1,y,0);
					if(R.nextInt(5)>3){
						putForet(x-1,y-1,0);
					}
					if(R.nextInt(5)>3){
						putForet(x-1,y+1,0);
					}
				}
				if(R.nextInt(3)>1){
					putForet(x+1,y,0);
					if(R.nextInt(5)>3){
						putForet(x-1,y-1,0);
					}
					if(R.nextInt(5)>3){
						putForet(x-1,y+1,0);
					}
				}
				if(R.nextInt(3)>1){
					putForet(x,y-1,0);

					if(R.nextInt(5)>3){
						putForet(x-1,y-1,0);
					}
					if(R.nextInt(5)>3){
						putForet(x-1,y+1,0);
					}
				}
				if(R.nextInt(3)>1){
					putForet(x,y+1,0);
					if(R.nextInt(5)>3){
						putForet(x-1,y-1,0);
					}
					if(R.nextInt(5)>3){
						putForet(x-1,y+1,0);
					}
				}
				
			}
			else if(facteur>1){
				int nextFacteur;
				for(int i=-1;i<=1;i++)
					for(int j=-1;j<=1;j++)
						if(j!=0 || i!=0){
							if(R.nextInt(3)>1)
								nextFacteur=facteur;
							else
								nextFacteur=facteur-1;	
							putForet(x+i,y+j,nextFacteur);
						}
			}
		}
	}
	
	private void randomForet(){
		Random R = new Random();
		int incrX = (_largeur>=_hauteur)?15:9;//(_largeur/5);
		int incrY = (_largeur>=_hauteur)?9:15;//(_hauteur/5);
		for(int y=0; y<_hauteur; y+=incrY){
			for(int x=0; x<=_largeur; x+=incrX){
				int xF = Math.min(R.nextInt(incrX)+x,_largeur);
				int yF = Math.min(R.nextInt(incrY)+y,_hauteur);
				putForet(xF,yF,2);
			}
		}
	}
	
	private void putLac(int x, int y, int facteur){
		if(Case(x,y)!=null && (x>1 || y>_hauteur/2) && (x<_largeur-2 || y<_hauteur/2) && (y>1 || x>_largeur/2) && (y<_hauteur-2 || x<_largeur/2) && Case(x,y).type().franchissable() && !(Case(x,y).type() instanceof Eau)){
			Random R = new Random();
			
			if(facteur>0){ 
				modifierCase(Eau.getInstance(), x, y);
				if (facteur == 1) {
					if(R.nextInt(3)==2){
						putLac(x - 1, y, 0);
						putLac(x + 1, y, 0);
					} else if(R.nextInt(3)==1)
						putLac(x - 1, y, 0);
					else
						putLac(x + 1, y, 0);
					
					if(R.nextInt(3)==2){
						putLac(x, y - 1, 0);
						putLac(x, y + 1, 0);	
					} else if(R.nextInt(3)==1)
						putLac(x, y - 1, 0);
					else
						putLac(x, y + 1, 0);
				} else if (facteur > 1) {
					int nextFacteur;
					if (R.nextInt(3) > 1)
						nextFacteur = facteur;
					else
						nextFacteur = facteur - 1;
					putLac(x - 1, y, nextFacteur);
					/*
					 * if(R.nextInt(3)>1) nextFacteur=facteur; else
					 * nextFacteur=facteur-1;
					 */
					putLac(x + 1, y, nextFacteur);
					/*
					 * if(R.nextInt(3)>1) nextFacteur=facteur; else
					 * nextFacteur=facteur-1;
					 */
					putLac(x, y - 1, nextFacteur);
					/*
					 * if(R.nextInt(3)>1) nextFacteur=facteur; else
					 * nextFacteur=facteur-1;
					 */
					putLac(x, y + 1, nextFacteur);
				}
			} else{
				int adja = 0;
				adja += (Case(x-1,y)!=null && Case(x-1,y).type() instanceof Eau)? 1 : 0;
				adja += (Case(x+1,y)!=null && Case(x+1,y).type() instanceof Eau)? 1 : 0;
				adja += (Case(x,y-1)!=null && Case(x,y-1).type() instanceof Eau)? 1 : 0;
				adja += (Case(x,y+1)!=null && Case(x,y+1).type() instanceof Eau)? 1 : 0;
				if(adja>1)
					modifierCase(Eau.getInstance(), x, y);
			}
		}
	}
	
	private void randomLac(){
		Random R = new Random();
		for(int y=5; y<_hauteur-5; y+=5){
			for(int x=5; x<=_largeur-5; x+=5){
				int roll = R.nextInt(20);
				if(roll==0){
					putLac(x,y,5);
				}
			}
		}
	}
	
	public void switchCase(Case c1, Case c2){
		if(c1 == null || c2 == null || (c1 instanceof Terrain && c2 instanceof Terrain)){
			return;
		}
		int x1 = c1.X();
		int x2 = c2.X();
		int y1 = c1.Y();
		int y2 = c2.Y();
		
		if(c1 instanceof CaseAction)
			try {
				putCaseAction(((CaseAction) c1),x2,y2,((CaseAction) c1).owner());
			} catch (Exception e) {
			    	e.printStackTrace();
				System.out.println("bug1");
				return;
			}
		else{
		    try {
			putCase(new Terrain(x2,y2,c1.type()));
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		
		if(c2 instanceof CaseAction)
			try {
				putCaseAction(((CaseAction) c2),x1,y1,((CaseAction) c2).owner());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("bug2");
				return;
			}
		else{
		    try {
			putCase(new Terrain(x1,y1,c2.type()));
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
