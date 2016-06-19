package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;

import cases.*;
import jus.util.assertion.Require;
import roles.Army;
import roles.Joueur;
import roles.Personnage;
import roles.World;

public class MapTest extends Observable {

	static final int TILESIZE = MapGameState.TILESIZE;

	private Vector<Vector<ObjetTest>> map;
	private static SpriteSheet spriteSheet = null;

	private int _scrollingSpeed = 15;
	private float zoom = 1;


	protected float Ox = 48;
	protected float Oy = 48;

	private float x = 0;
	private float y = 0;

	protected int _x;
	protected int _y;
	protected int _width;
	protected int _height;

	private ArrayList<GArmy> _joueurs = new ArrayList<GArmy>();

	public MapTest(int x, int y, int width, int height)
	{
		_x = x;
		_y = y;
		_width = width;
		_height = height;
	}

	public void initialise(World world)
	{
		// plutot avoir un buffer image
		map = new Vector<Vector<ObjetTest>> ();
		for (int j = 0; j < world.SizeY() ; j++) {
			map.add(new Vector<ObjetTest> ());
		}
		for (int j = 0; j < world.SizeY() ; j++) {
			for (int i = 0; i < world.SizeX() ; i++) {
				map.get(j).add(new ObjetTest(world.Case(i, j).value()));
				world.Case(i, j).addObserver(map.get(j).get(i));
			}
		}
	}

	public static void init() throws SlickException {
		//Dans ce sprite -> cellule 0 : buisson, cellule 1 : arbre, cellule 2 : herbe, cellule 3 : tonneaux, cellule 4 : rochers. (format des cases : 96x96).
		//Pour utiliser la méthode renderInUse(...), je dois appeler la méthode startUse() pour dire que je commence à lire cette table de sprite.
		spriteSheet = new SpriteSheet("src/asset/sprites/tiles.png", TILESIZE, TILESIZE);
		//Tree = new Image("src/asset/sprites/SimpleTree.png");
		//J'initialise ma map test.

	}

	public void render(Graphics g) throws SlickException {

		//Nettoyage de la zone d'affichage
		g.drawImage(spriteSheet, _x, _y, _x+_width, _y+_height, 96, 0, 96*2, 96);
		//Gestion du scrolling de la map
		g.translate(-x, -y);
		//Gestion du zoom
		g.scale(zoom, zoom);


		int xi = MapGameState.fromX(x/zoom);
		int yi = MapGameState.fromY(y/zoom);
		g.setClip(new Rectangle(_x, _y, _width, _height));

		int xi = fromX(x/zoom);
		int yi = fromY(y/zoom);

		int xf = fromX((x+_width)/zoom)+2;
		int yf = fromY((y+_height)/zoom)+2;
		if(yi<0)
			yi = 0;
		if(xi<0)
			xi = 0;
		spriteSheet.startUse();
		//Parcours de la map test
		for (int i = yi; i < map.size()  && i<yf; i++) {
			for (int j = xi; j < map.get(0).size()  && j<xf; j++) {
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
				else if (vj.getNum() == Batiment._id)
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
			g.clearClip();
		}
	//Ne pas oublier de finir la lecture du tableau de sprite. Sinon, il y a aura un problème avec la lecture du sprite du personnage.
		spriteSheet.endUse();

		//Affichage des personnages
		for(GArmy j : _joueurs)
			for(Player p : j.getPersonnage())
					if(p.getX()+TILESIZE > x/zoom() && p.getY()+TILESIZE > y/zoom())
						if(p.getX()-TILESIZE < (x + _width)/zoom())
						if(p.getY()-TILESIZE < (y + _height)/zoom())
							p.render(g);

	}

	public void render2(Graphics g) throws SlickException {
		//Gestion du scrolling de la map
				g.translate(-x, -y);
				//Gestion du zoom
				g.scale(zoom, zoom);

				spriteSheet.startUse();
				for (int i = 0; i < map.size(); i++) {
					for (int j = 0; j < map.get(0).size(); j++) {
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
						else if (vj.getNum() == Batiment._id)
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


				//Affichage des personnages
				for(GArmy j : _joueurs)
					for(Player p : j.getPersonnage())
							if(p.getX()+TILESIZE > x/zoom() && p.getY()+TILESIZE > y/zoom())
								if(p.getX()-TILESIZE < (x + _width)/zoom())
								if(p.getY()-TILESIZE < (y + _height)/zoom())
									p.render(g);


	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		for(GArmy j : _joueurs)
			for(int i = j.getPersonnage().size()-1; i>=0; i--)
				if(j.getPersonnage().get(i).AnimDead>0)
					j.getPersonnage().get(i).update(delta);
				else
					j.getPersonnage().remove(j.getPersonnage().get(i));
	}

	public void addArmy(Army a)
	{
		_joueurs.add(new GArmy(a.joueur(), this));
		a.addObserver(_joueurs.get(_joueurs.size()-1));
		for(Personnage pers : a.getPersonnages())
			_joueurs.get(_joueurs.size()-1).addPersonnage(pers);
	}

	public float toX(int x)
	{
		return x * TILESIZE + Ox;
	}
	public float toY(int y)
	{
		return y * TILESIZE + Oy;
	}

	public int fromX(float x)
	{
		return (int) (x - Ox) / TILESIZE;
	}
	public int fromY(float y)
	{
		return (int) (y - Oy) / TILESIZE;
	}

	public float zoom() {
		return zoom;
	}

	public void setZoom(float fact, float mx, float my) {
		x = x * fact + mx * (fact - 1);// + mx / zoom - mx / (zoom * fact);
		y = y * fact + my * (fact - 1);// + my / zoom - my / (zoom * fact);
		this.zoom = zoom * fact;
	}

	public int getHeight() {
		return _height;
	}

	public int getWidth() {
		return _width;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public void render(GUIContext arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	public void setLocation(int x, int y) {
		_x = x;
		_y = y;
	}


	/**
	 * Vérification que le curseur de la souris est sur le personnage.
	 * @param p
	 * @param mouseX La position de la souris en abcisse X.
	 * @param mouseY La position de la souris en ordonnée Y.
	 * @return Un booléen qui indique si la position du curseur est sur un personnage.
	 * @require p != null
	 */
	public boolean curseurSurPerso(Player p, float mouseX, float mouseY) {
		if (!(p!= null)) {
			throw new Require("p == null)");
		}
		return (mouseX >= p.getX()-32 && mouseX <= p.getX()+32 && mouseY >= p.getY()-60 && mouseY <= p.getY()+4);
	}

	public boolean mousePressed(int button, int mx, int my) {
		//super.mousePressed(button, x, y);
		for(GArmy a : _joueurs)
			for(Player p : a.getPersonnage())
				if (Input.MOUSE_LEFT_BUTTON == button && curseurSurPerso(p, (mx+this.x)/zoom(), (my+this.y)/zoom())) {
				//this.showhud = true;
					this.setChanged();
					this.notifyObservers(new Object(){
						public int mx = fromX(p.DestX());
						public int my = fromY(p.DestY());
						public Player mtargetp =p;});
					//((MapGameState)InitGameState.game.getState(MapGameState.ID)).setTarget(p, (int)(MapGameState._target.DestX()-Ox)/TILESIZE, (int)(MapGameState._target.DestY()-Oy)/TILESIZE);
				return true;
			}
			/*	else

					System.out.println((mx+this.x)/zoom() + " " +this.x +" " +mx + " " + zoom() +" " + p.DestX());*/
		//map.mousePressed(button, x, y);
		this.setChanged();
		this.notifyObservers(new Object(){
			public int mx = 0;
			public int my = 0;
			public Player mtargetp =null;});
			return false;
	}

	public void move(int i, int j) {
		x += i * _scrollingSpeed;
		y += j * _scrollingSpeed;
	}

	public boolean isOver(int mouseX, int mouseY) {
		return mouseX >= getX() && mouseY >= getY() && mouseX<=getX()+getWidth() && mouseY<=getY()+getHeight();
	}
}
