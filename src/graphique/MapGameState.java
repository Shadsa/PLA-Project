package graphique;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import roles.classe.*;
import roles.Automate;
import roles.Cardinaux;
import roles.Personnage;
import roles.States.Statut;
import roles.action.Attaquer;
import roles.action.Avancer;
import roles.action.Joueur;
import roles.action.World;
import roles.conditions.Enemy;
import roles.conditions.Libre;

public class MapGameState extends BasicGameState {

	private GameContainer container;
	private ArrayList<Player> _players = new ArrayList<Player>();
	//private Personnage personnage;
	private Hud hud = new Hud();
	public static final int ID = 2;
	private String playerData;
	private String mouse;
	private float mouseAbsoluteX;
	private float mouseAbsoluteY;
	private float _mouseMapX;
	private float _mouseMapY;
	private boolean showhud = false;
	private Player _selected = null;
	
	private float _offsetMapX = 0;
	private float _offsetMapY = 0;

	//Test
	private MapTest map = new MapTest();
	private Input _input;
	private int _scrollingSpeed = 8;
	private float _zoom = 1;

	public float zoom() {
		return _zoom;
	}

	public void setZoom(float _zoom) {
		this._zoom = _zoom;
	}

	/**
	 * Initialise le contenu du jeu, charge les animations
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		_input = container.getInput(); 
		
		Automate aut1 = new Automate(2);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.NORD, 1), new Libre(Cardinaux.NORD), 0);
		// décommenter pour tester attaque
		aut1.ajoute_transition(0, new Attaquer(Cardinaux.SUD, 1), new Enemy(Cardinaux.SUD), 0);
		aut1.ajoute_transition(0, new Attaquer(Cardinaux.NORD, 1), new Enemy(Cardinaux.NORD), 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.EST, 1), new Libre(Cardinaux.EST), 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.SUD, 0), new Libre(Cardinaux.SUD), 1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.OUEST, 0), new Libre(Cardinaux.OUEST), 1);

		aut1.ajoute_transition(1, new Avancer(Cardinaux.NORD, 0), new Libre(Cardinaux.NORD), 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.EST, 0), new Libre(Cardinaux.EST), 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.SUD, 1), new Libre(Cardinaux.SUD), 1);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.OUEST, 1), new Libre(Cardinaux.OUEST), 1);
		ArrayList<Automate> autlist = new ArrayList<Automate>();
		autlist.add(aut1);
		Classe generique = new Classe(1,1,0,"default class","none");
		ArrayList<Classe> classes = new ArrayList<Classe>();
		classes.add(generique);
		Joueur j2 = new Joueur("Moi", autlist,classes);
		World.addPlayer(j2);
		World.BuildMap();
		j2.createPersonnage(0, 5, 5);
		j2.createPersonnage(0, 6, 6);
		j2.createPersonnage(0, 3, 6);
		j2.createPersonnage(0, 6, 2);
		j2.createPersonnage(0, 0, 0);
		j2.createPersonnage(0, 2, 1);
		j2.createPersonnage(0, 4, 7);

		j2.createPersonnage(0, 0, 1);
		j2.createPersonnage(0, 0, 2);
		j2.createPersonnage(0, 0, 3);
		j2.createPersonnage(0, 0, 4);
		j2.createPersonnage(0, 0, 5);
		j2.createPersonnage(0, 0, 6);
		j2.createPersonnage(0, 0, 7);
		j2.createPersonnage(0, 0, 8);
		j2.createPersonnage(0, 0, 9);

		j2.createPersonnage(0, 1, 1);
		j2.createPersonnage(0, 1, 2);
		j2.createPersonnage(0, 1, 3);
		j2.createPersonnage(0, 1, 4);
		j2.createPersonnage(0, 1, 5);
		j2.createPersonnage(0, 1, 6);
		j2.createPersonnage(0, 1, 7);
		j2.createPersonnage(0, 1, 8);
		j2.createPersonnage(0, 1, 9);
		//personnage = j2.getPersonnages().get(0);


		this.container = container;
		this.map.init();

		Player pla;
		for(Joueur j : World.getPlayers())
		for(Personnage pers : j.getPersonnages())
		{
			pla = new Player();
			pla.init(pers);
			_players.add(pla);
		}

		//System.out.print(_players.size());


		/*_players.add(new Player());
		_players.get(0).init();
		_players.get(0).setX(100+300);
		_players.get(0).setDestX(100+300);
		_players.get(0).setY(100+300);
		_players.get(0).setDestY(100+300);*/
		//PlayerController controller = new PlayerController(_players.get(0));
		//container.getInput().addKeyListener(controller);
		this.hud.init();
	}

	/**
	 * Affichage des différents éléments du jeu
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//Affichage de la map
		this.map.render(g, _offsetMapX, _offsetMapY, zoom());
		//Affichage des personnages
		for(Player p : _players)
			p.render(g);
		
		//Affichage des huds
		if(showhud) {
			this.hud.render(g);
		}
		//Annule la translation pour l'affichage du string en dessous
		g.resetTransform();
		g.setColor(Color.white);
		//Affichage de la position de la souris sur la map
		g.drawString(mouse, 10, 50);
		g.drawString("MouseX : " + mouseMapX() + ", MouseY : " + mouseMapY(), 10, 70);
		g.drawString("Zoom Avant : 'PRECEDENT', Zoom Arrière : 'SUIVANT', zoom : " + _zoom, 10, 90);
		g.drawString("offsetMapX : " + offsetMapX() + ", offsetMapY : " + offsetMapY(), 10, 110);
	}

	protected long _time = 0;

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for(int i = _players.size()-1; i>=0; i--)
			if(_players.get(i).states().statut != Statut.MORT)
				_players.get(i).update(delta);
			else
				_players.remove(_players.get(i));

		_time += delta;
		if(_time > 400)
		{
			_time -= 400;
			World.nextTurn();
		}
		//playerData = "Coord X :" + this.player.getX() + ", Coord Y : " + this.player.getY() + ", action_finie : " + this.player.getAction_finie();
		mouseAbsoluteX = _input.getAbsoluteMouseX();// + offsetMapX();
		mouseAbsoluteY = _input.getAbsoluteMouseY();// + offsetMapY();
		_mouseMapX = (int) ((mouseAbsoluteX + offsetMapX()) / zoom());
		_mouseMapY = (int) ((mouseAbsoluteY + offsetMapY()) / zoom());
		mouse = "MouseAbsoluteX : " + mouseAbsoluteX + ", MouseAbsoluteY : " + mouseAbsoluteY;
		
		//Gestion du scrolling de la map avec la souris
		if (mouseAbsoluteY == container.getScreenHeight()) {
			setOffsetMapY(offsetMapY() + _scrollingSpeed);
		}
		if (mouseAbsoluteX == 0) {
			setOffsetMapX(offsetMapX() - _scrollingSpeed);
		}
		if (mouseAbsoluteX == container.getScreenWidth() - 1) {
			setOffsetMapX(offsetMapX() + _scrollingSpeed);
		}
		if (mouseAbsoluteY == 1) {
			setOffsetMapY(offsetMapY() - _scrollingSpeed);
		}
		
		//Gestion du scrolling de la map avec la manette
		if (_input.isControllerDown(0)) {
			setOffsetMapY(offsetMapY() + _scrollingSpeed);
		}
		if (_input.isControllerLeft(0)) {
			setOffsetMapX(offsetMapX() - _scrollingSpeed);
		}
		if (_input.isControllerRight(0)) {
			setOffsetMapX(offsetMapX() + _scrollingSpeed);
		}
		if (_input.isControllerUp(0)) {
			setOffsetMapY(offsetMapY() - _scrollingSpeed);
		}
		//Gestion du scrolling de la map avec le clavier
		//Touche bas
		if (_input.isKeyDown(208)) {
			setOffsetMapY(offsetMapY() + _scrollingSpeed);
		}
		//Touche gauche
		if (_input.isKeyDown(203)) {
			setOffsetMapX(offsetMapX() - _scrollingSpeed);
		}
		//Touche droite
		if (_input.isKeyDown(205)) {
			setOffsetMapX(offsetMapX() + _scrollingSpeed);
		}
		//Touche haut
		if (_input.isKeyDown(200)) {
			setOffsetMapY(offsetMapY() - _scrollingSpeed);
		}
		
		//Gestion du zoom
		//Zoom avant
		if (_input.isKeyDown(201)) {
			setZoom(zoom() + 0.01f);
			//Marche pas
			//setOffsetMapY(container.getScreenWidth()/zoom()/2 - mouseAbsoluteX);
			//setOffsetMapX(container.getScreenHeight()/zoom()/2 - mouseAbsoluteY);
		}
		//Zoom arrière
		if (_input.isKeyDown(209) && zoom() > 0) {
			setZoom(zoom() - 0.01f);
			//Marche pas
			//setOffsetMapY(container.getScreenWidth()/zoom()/2 - mouseAbsoluteX);
			//setOffsetMapX(container.getScreenHeight()/zoom()/2 - mouseAbsoluteY);
			if (zoom() < 0) {
				setZoom(0);
			}
		}
		
	}

	public void keyReleased(int key, char c) {
	}
	
	public void keyPressed(int key, char c) {
	}
	
	public void mousePressed(int arg0, int arg1, int arg2) {
		//if (Input.MOUSE_LEFT_BUTTON == arg0) {//&& mouseMapX() >= this.player.getX()-32 && mouseMapX() <= this.player.getX()+32 && mouseMapY() >= this.player.getY()-60 && mouseMapY() <= this.player.getY()+4) {
			for(Player p : _players)
				if (Input.MOUSE_LEFT_BUTTON == arg0 && curseurSurPerso(p, mouseMapX(), mouseMapY())) {
					this.showhud = true;
				}
		//}

	}

	public void mouseReleased(int arg0, int arg1, int arg2) {
		if (Input.MOUSE_LEFT_BUTTON == arg0) {
			this.showhud = false;
		}

	}
	
	public boolean curseurSurPerso(Player p, float mouseX, float mouseY) {
		return (mouseX >= p.getX()-32 && mouseX <= p.getX()+32 && mouseY >= p.getY()-60 && mouseY <= p.getY()+4);
	}

	public int getID() {
		return ID;
	}
	
	public float mouseMapX() {
		return _mouseMapX;
	}
	
	public float mouseMapY() {
		return _mouseMapY;
	}
	
	public void setMouseMapX(int x) {
		this._mouseMapX = x;
	}
	
	public void setMouseMapY(int y) {
		this._mouseMapY = y;
	}
	
	public float offsetMapX() {
		return _offsetMapX;
	}
	
	public float offsetMapY() {
		return _offsetMapY;
	}
	
	public void setOffsetMapX(float x) {
		this._offsetMapX = x;
	}
	
	public void setOffsetMapY(float f) {
		this._offsetMapY = f;
	}
}
