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
import roles.World;

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

	public void render(Graphics g, float x, float y, float zoom, int width, int height) throws SlickException {

		//Gestion du scrolling de la map
		g.translate(-x, -y);
		//Gestion du zoom
		g.scale(zoom, zoom);

		int xi = MapGameState.fromX(x/zoom);
		int yi = MapGameState.fromY(y/zoom);

		int xf = MapGameState.fromX((x+width)/zoom)+2;
		int yf = MapGameState.fromY((y+height)/zoom)+2;
		if(yi<0)
			yi = 0;
		if(xi<0)
			xi = 0;
		spriteSheet.startUse();
		//Parcours de la map test
		for (int i = yi; i < World.SizeY() && i<yf; i++) {
			for (int j = xi; j < World.SizeX() && j<xf; j++) {
				ObjetTest vj = map.get(i).get(j);
				int img = 0;
				int img2 = 0;

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
		        else if(vj.getNum() == Mur._id)
		            img = 12;
		        else if(vj.getNum() == Piege._id)
		            img = 13;				
				else if (vj.getNum() == Plaine._id)
					img = 2;
				else if (vj.getNum() == Caillou._id)
					img = 3;
				else if (vj.getNum() == 0)
					img = 7;
				else if (vj.getNum() == Eau._id)
				{
					img = 0;
					img2 = 1;
					/*if(i<map.size()-1 && map.get(i+1).get(j).getNum() != Eau._id)
						img += 4;*/
					if(j==map.get(0).size()-1 || map.get(i).get(j+1).getNum() != Eau._id)
						img += 1;
					if(j>0 && map.get(i).get(j-1).getNum() != Eau._id)
						img += 2;
					if(i>0 && map.get(i-1).get(j).getNum() != Eau._id)
						img += 4;
					if(i==map.size()-1 || map.get(i+1).get(j).getNum() != Eau._id)
						img += 8;
					spriteSheet.renderInUse(j*96, i*96, img2, img);


						if(!((img&2) > 0 || (img&4) > 0) && j>0 && i>0 &&  map.get(i-1).get(j-1).getNum() != Eau._id)
							spriteSheet.renderInUse(j*96, i*96, 0, 8);
						if(!((img&8) > 0 || (img&2) > 0) && j>0 && i<map.size()-1 &&  map.get(i+1).get(j-1).getNum() != Eau._id)
							spriteSheet.renderInUse(j*96, i*96, 0, 9);
						if(!((img&1) > 0 || (img&8) > 0) && j<map.get(0).size()-1 && i<map.size()-1 &&  map.get(i+1).get(j+1).getNum() != Eau._id)
							spriteSheet.renderInUse(j*96, i*96, 0, 11);
						if(!((img&1) > 0 || (img&4) > 0) && j<map.get(0).size()-1 && i>0 &&  map.get(i-1).get(j+1).getNum() != Eau._id)
							spriteSheet.renderInUse(j*96, i*96, 0, 10);
					continue;
				}
				spriteSheet.renderInUse(j*96, i*96, img2, img);

			}
		}
	//Ne pas oublier de finir la lecture du tableau de sprite. Sinon, il y a aura un problème avec la lecture du sprite du personnage.
		spriteSheet.endUse();
	}
}
