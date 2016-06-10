package graphique;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import cases.*;
import roles.action.World;

public class MapTest {

	public static int tileSize = 96;
	private Vector<Vector<ObjetTest>> map;
	private SpriteSheet spriteSheet;
	private Image Tree;

	public void init() throws SlickException {
		//Dans ce sprite -> cellule 0 : buisson, cellule 1 : arbre, cellule 2 : herbe, cellule 3 : tonneaux, cellule 4 : rochers. (format des cases : 96x96).
		//Pour utiliser la méthode renderInUse(...), je dois appeler la méthode startUse() pour dire que je commence à lire cette table de sprite.
		spriteSheet = new SpriteSheet("src/asset/sprites/tiles.png", tileSize, tileSize);
		//Tree = new Image("src/asset/sprites/SimpleTree.png");
		//J'initialise ma map test.
		map = new Vector<Vector<ObjetTest>> ();
		for (int j = 0; j < World.SizeY(); j++) {
			map.add(new Vector<ObjetTest> ());
		}
		for (int j = 0; j < World.SizeY(); j++) {
			for (int i = 0; i < World.SizeX(); i++) {
				map.get(j).add(new ObjetTest(World.Case(i, j).value()));
				World.Case(i, j).addObserver(map.get(j).get(i));
			}
		}
	}

	public void render(Graphics g, float x, float y, float zoom) throws SlickException {

		//Gestion du scrolling de la map
		g.translate(-x, -y);
		//Gestion du zoom
		g.scale(zoom, zoom);
		
		spriteSheet.startUse();
		//Parcours de la map test
		for (int i = 0; i < World.SizeY(); i++) {
			for (int j = 0; j < World.SizeX(); j++) {
				ObjetTest vj = map.get(i).get(j);
				int img = 0;

				if (vj.getNum() == Arbre._id)
				{
					spriteSheet.renderInUse(j*96, i*96, 0, 2);
					spriteSheet.renderInUse(j*96, i*96, 0, 6);
					if(i>0 && map.get(i-1).get(j).getNum() == Arbre._id)
						spriteSheet.renderInUse(j*96-12, i*96-48, 0, 6);
					if(j>0 && map.get(i).get(j-1).getNum() == Arbre._id)
						spriteSheet.renderInUse(j*96-48, i*96-12, 0, 6);
					continue;
				}
				else if (vj.getNum() == Plaine._id)
					img = 2;
				else if (vj.getNum() == Caillou._id)
					img = 3;
				else if (vj.getNum() == Eau._id)
					img = 5;

				/*if (vj.getNum() == 0) {
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
				}*/
				spriteSheet.renderInUse(j*96, i*96, 0, img);

			}
		}
	//Ne pas oublier de finir la lecture du tableau de sprite. Sinon, il y a aura un problème avec la lecture du sprite du personnage.
		spriteSheet.endUse();
	}
}
