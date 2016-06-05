package graphique;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MapTest {

	private Vector<Vector<ObjetTest>> map;
	private SpriteSheet spriteSheet;

	public void init() throws SlickException {
		
		//Dans ce sprite -> cellule 0 : buisson, cellule 1 : arbre, cellule 2 : herbe, cellule 3 : tonneaux, cellule 4 : rochers. (format des cases : 96x96).
		//Pour utiliser la méthode renderInUse(...), je dois appeler la méthode startUse() pour dire que je commence à lire cette table de sprite.
		spriteSheet = new SpriteSheet("src/asset/sprites/tiles.png", 96, 96);

		//J'initialise ma map test.
		map = new Vector<Vector<ObjetTest>> (5);
		for (int i = 0; i < 5; i++) {
			map.insertElementAt(new Vector<ObjetTest> (5), i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Vector <ObjetTest> vi = map.elementAt(i);
				vi.insertElementAt(new ObjetTest(j%5), j);
			}
		}
	}
	
	public void render(Graphics g) throws SlickException {
		
		//Pour dézoomer de moitié décommenter la ligne dessous (attention, les coordonnées de la souris reste à l'échelle 1 donc l'affichage de l'hud ne sera plus à l'échelle)
		//g.scale(0.75f, 0.75f);
		spriteSheet.startUse();
		//Parcours de la map test
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Vector <ObjetTest> vi = map.elementAt(i);
				ObjetTest vj = vi.elementAt(j);
				if (vj.getNum() == 0) {
					//Affiche la cellule demandée dans la table des sprites : renderInUse(coordX du container, coordY du container, indiceX de la cellule, indiceY de la cellule). 
					spriteSheet.renderInUse(0+i*96, 0, 0, 0);
				} else if (vj.getNum() == 1) {
					spriteSheet.renderInUse(0+i*96, 96, 0, 2);
				} else if (vj.getNum() == 2) {
					spriteSheet.renderInUse(0+i*96, 192, 0, 1);
				} else if (vj.getNum() == 3) {
					spriteSheet.renderInUse(0+i*96, 288, 0, 4);
				} else if (vj.getNum() == 4) {
					spriteSheet.renderInUse(0+i*96, 384, 0, 3);
				}
				
			}
		}
	//Ne pas oublier de finir la lecture du tableau de sprite. Sinon, il y a aura un problème avec la lecture du sprite du personnage.
		spriteSheet.endUse();
	}
}
