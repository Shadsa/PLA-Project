package roles;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import cases.*;
import roles.action.Joueur;
import roles.action.World;

public class Carte extends Vector<Vector<Case>>{

	private int _hauteur;
	private int _largeur;
	
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
				case 0 : case 1 :
					c = new Terrain(x,y,Plaine.getInstance());
					System.out.print('P');
					break;
				case 2 :
					c = new Terrain(x,y,Caillou.getInstance());
					System.out.print('C');
					break;
				default :
					c = new Terrain(x,y,Plaine.getInstance());
					System.out.print('D');
				}
				//NE PAS OUBLIER LES BREAKS QUAND ON RAJOUTE UN TYPE DE CASE
				ligne.add(c);
			}
			add(ligne);
			System.out.print('\n');
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
		if(x < 0 || y < 0 || x >= size() || y >= get(x).size())
			throw new Exception("Dépassement de la carte");
		c.setCase(x, y, j);
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
		for(ArrayList<CaseAction> ligne : a.get_action())
			for(CaseAction c : ligne)
				try {
					putCaseAction(c,x++,y++,j);
				} catch (Exception e) {
					throw new Exception("Impossible de placer l'automate");
				}
	}

	public void modifierCase(TypeCase type, int x, int y){
		Case(x,y).modifierCase(type);
	}
	
	private void putForet(int x, int y, int facteur){
		if(Case(x,y)!=null && Case(x,y).type().franchissable() && Case(x,y).type().value() != Arbre.getInstance().value()){
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
		for(int y=0; y<_hauteur; y+=5){
			for(int x=0; x<=_largeur; x+=5){
				int roll = R.nextInt(20);
				if(roll==0){
					putForet(x,y,3);
				}
				else if(roll<=4){
					putForet(x,y,2);
				}
			}
		}
	}
	
	private void putLac(int x, int y, int facteur){
		if(Case(x,y)!=null && Case(x,y).type().franchissable() && Case(x,y).type().value() != Eau.getInstance().value()){
			Random R = new Random();
			this.modifierCase(Eau.getInstance(), x, y);
			if(facteur==1){
				if(R.nextInt(3)>1)
					putLac(x-1,y,0);
				if(R.nextInt(3)>1)
					putLac(x+1,y,0);
				if(R.nextInt(3)>1)
					putLac(x,y-1,0);
				if(R.nextInt(3)>1)
					putLac(x,y+1,0);
			}
			else if(facteur>1){
				int nextFacteur;
				if(R.nextInt(3)>1)
					nextFacteur=facteur;
				else
					nextFacteur=facteur-1;	
				putLac(x-1,y,nextFacteur);
				
				/*if(R.nextInt(3)>1)
					nextFacteur=facteur;
				else
					nextFacteur=facteur-1;	*/
				putLac(x+1,y,nextFacteur);
				
				/*if(R.nextInt(3)>1)
					nextFacteur=facteur;
				else
					nextFacteur=facteur-1;*/	
				putLac(x,y-1,nextFacteur);
				
				/*if(R.nextInt(3)>1)
					nextFacteur=facteur;
				else
					nextFacteur=facteur-1;	*/
				putLac(x,y+1,nextFacteur);
			}
		}
	}
	
	private void randomLac(){
		Random R = new Random();
		for(int y=0; y<_hauteur; y+=5){
			for(int x=0; x<=_largeur; x+=5){
				int roll = R.nextInt(20);
				if(roll==0){
					putLac(x,y,5);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
