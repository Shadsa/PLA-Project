package graphique;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import roles.action.World;

public class MapTest {

	public static int tileSize = 96;
	private Vector<Vector<ObjetTest>> map;
	private SpriteSheet spriteSheet;

	public void init() throws SlickException {
		//Dans ce sprite -> cellule 0 : buisson, cellule 1 : arbre, cellule 2 : herbe, cellule 3 : tonneaux, cellule 4 : rochers. (format des cases : 96x96).
		//Pour utiliser la méthode renderInUse(...), je dois appeler la méthode startUse() pour dire que je commence à lire cette table de sprite.
		spriteSheet = new SpriteSheet("src/asset/sprites/tiles.png", tileSize, tileSize);

		//J'initialise ma map test.
		map = new Vector<Vector<ObjetTest>> (World.SizeX());
		for (int j = 0; j < World.SizeX(); j++) {
			map.insertElementAt(new Vector<ObjetTest> (World.SizeX()), j);
		}
		for (int i = 0; i < World.SizeX(); i++) {
			for (int j = 0; j < World.SizeX(); j++) {
				Vector <ObjetTest> vi = map.elementAt(i);
				vi.insertElementAt(new ObjetTest(World.Case(i, j).value()), j);
			}
		}
	}

	public void render(Graphics g, float x, float y, float zoom) throws SlickException {

		//g.resetTransform();
		g.translate(-x, -y);
		//Pour dézoomer de moitié décommenter la ligne dessous (attention, les coordonnées de la souris reste à l'échelle 1 donc l'affichage de l'hud ne sera plus à l'échelle)
		//if (zoomIn) {
		g.scale(zoom, zoom);
		//}
		//if (zoomOut) {
			//g.scale(0.50f, 0.50f);
		//}
		spriteSheet.startUse();
		//Parcours de la map test
		for (int i = 0; i < World.SizeX(); i++) {
			for (int j = 0; j < World.SizeX(); j++) {
				Vector <ObjetTest> vi = map.elementAt(i);
				ObjetTest vj = vi.elementAt(j);
				if (vj.getNum() == 0) {
					//Affiche la cellule demandée dans la table des sprites : renderInUse(coordX du container, coordY du container, indiceX de la cellule, indiceY de la cellule).
					spriteSheet.renderInUse(i*96, j*96, 0, 0);
				} else if (vj.getNum() == 1) {
					spriteSheet.renderInUse(i*96, j*96, 0, 2);
				} else if (vj.getNum() == 2) {
					spriteSheet.renderInUse(i*96, j*96, 0, 1);
				} else if (vj.getNum() == 3) {
					spriteSheet.renderInUse(i*96, j*96, 0, 4);
				} else if (vj.getNum() == 4) {
					spriteSheet.renderInUse(i*96, j*96, 0, 3);
				} else if (vj.getNum() == 5) {
					spriteSheet.renderInUse(i*96, j*96, 0, 5);
				}

			}
		}
	//Ne pas oublier de finir la lecture du tableau de sprite. Sinon, il y a aura un problème avec la lecture du sprite du personnage.
		spriteSheet.endUse();
	}
}
