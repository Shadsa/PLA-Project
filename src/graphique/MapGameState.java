package graphique;

import java.util.ArrayList;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.w3c.dom.DOMException;

import XML.XML_Reader;
import roles.Bonus;
import roles.classe.*;
import roles.Automate;
import roles.Cardinaux;
import roles.Joueur;
import roles.Personnage;
import roles.World;
import roles.States.Statut;
import roles.action.Attaquer;
import roles.action.Avancer;
import roles.action.AvancerJoueur;
import roles.action.Dupliquer;
import roles.action.Raser;
import roles.conditions.ArbreProche;
import roles.conditions.Ennemi;
import roles.conditions.Et;
import roles.conditions.Libre;
import roles.conditions.OrdreDonne;
import roles.conditions.Vide;

public class MapGameState extends BasicGameState {

	static int Tick = 1000;
	static boolean TickWait = true;
	static int AnimTick = Tick*6/10;
	static final int TileSize = 96;
	static float MoveSpeed = ((float)TileSize)/((float)AnimTick);
	static final float Ox = 48;
	static final float Oy = 48;

	private GameContainer container;
	private ArrayList<graphique.GJoueur> _joueurs = new ArrayList<graphique.GJoueur>();
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
	private float alpha = 0;

	public static Player _target = null;
	public static Personnage _targetp = null;

	private int _tailleMapY = 45;
	private int _tailleMapX = 75;

	private float _offsetMapX = 0;
	private float _offsetMapY = 0;

	public static float toX(int x)
	{
		return x * TileSize + Ox;
	}
	public static float toY(int y)
	{
		return y * TileSize + Oy;
	}

	public static int fromX(float x)
	{
		return (int) (x - Ox) / TileSize;
	}
	public static int fromY(float y)
	{
		return (int) (y - Oy) / TileSize;
	}

	//Test
	private MapTest map = new MapTest();
	private Input _input;
	private int _scrollingSpeed = 15;
	private float _zoom = 1;
	private StateGame game;

	//Boutons
	private Button _bouton_fullScreen;
	private Button _bouton_son;
	private Button _bouton_quitter;
	private Button _bouton_reprendre;
	private Button _bouton_menuPrincipal;

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
		this.game = (StateGame) game;
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
		_bouton_fullScreen = new Button(container, "Plein écran", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_son = new Button(container, "Désactiver son", container.getWidth()/2-62, container.getHeight()/2-40, normalImage, overImage, downImage);
		_bouton_quitter = new Button(container, "Quitter", container.getWidth()/2-62, container.getHeight()/2+80, normalImage, overImage, downImage);
		_bouton_menuPrincipal = new Button(container, "Menu principal", container.getWidth()/2-62, container.getHeight()/2+40, normalImage, overImage, downImage);
		_bouton_reprendre = new Button(container, "Reprendre", container.getWidth()/2-62, container.getHeight()/2-80, normalImage, overImage, downImage);

		World.BuildMap(_tailleMapY,_tailleMapX);
		/*
		Automate aut1 = new Automate(2);

		aut1.ajoute_transition(0, new Avancer(Cardinaux.NORD), new Libre(Cardinaux.NORD), 0, 1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.EST), new Libre(Cardinaux.EST), 0, 1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.SUD), new Libre(Cardinaux.SUD), 1, 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.OUEST), new Libre(Cardinaux.OUEST), 1, 0);
		// décommenter pour tester attaque
		aut1.ajoute_transition(0, new Dupliquer(Cardinaux.SUD), new Libre(Cardinaux.SUD), 0, 1);
		aut1.ajoute_transition(0, new Dupliquer(Cardinaux.NORD), new Libre(Cardinaux.NORD), 0, 1);
		aut1.ajoute_transition(0, new Dupliquer(Cardinaux.EST), new Libre(Cardinaux.EST), 0, 1);
		aut1.ajoute_transition(0, new Dupliquer(Cardinaux.OUEST), new Libre(Cardinaux.OUEST), 0, 1);

		aut1.ajoute_transition(0, new AvancerJoueur(), new OrdreDonne(), 0, 5);
		//aut1.ajoute_transition(0, new Raser(), new Vide(), 1, 1);

		aut1.ajoute_transition(1, new Avancer(Cardinaux.NORD), new Libre(Cardinaux.NORD), 0, 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.EST), new Libre(Cardinaux.EST), 0, 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.SUD), new Libre(Cardinaux.SUD), 1, 1);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.OUEST), new Libre(Cardinaux.OUEST), 1, 1);
		aut1.ajoute_transition(1, new AvancerJoueur(), new OrdreDonne(), 1, 5);
		*//*
		File f = new File("./creation_automates/sortie.xml");
		Automate aut1 = null;
		try {
			aut1 = XML_Reader.readXML(f);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException | DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erreur XML");
		}
		//autlist.add(aut1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.EST), new Et(new Libre(Cardinaux.EST),new ArbreProche(Cardinaux.EST)), 0, 2);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.EST), new Et(new Libre(Cardinaux.EST),new ArbreProche(Cardinaux.EST)), 1, 2);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.NORD), new Et(new Libre(Cardinaux.NORD),new ArbreProche(Cardinaux.NORD)), 0, 2);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.NORD), new Et(new Libre(Cardinaux.NORD),new ArbreProche(Cardinaux.NORD)), 1, 2);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.OUEST), new Et(new Libre(Cardinaux.OUEST),new ArbreProche(Cardinaux.OUEST)), 0, 2);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.OUEST), new Et(new Libre(Cardinaux.OUEST),new ArbreProche(Cardinaux.OUEST)), 1, 2);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.SUD), new Et(new Libre(Cardinaux.SUD),new ArbreProche(Cardinaux.SUD)), 0, 2);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.SUD), new Et(new Libre(Cardinaux.SUD),new ArbreProche(Cardinaux.SUD)), 1, 2);
	    ArrayList<Automate> autlist = new ArrayList<Automate>();
		autlist.add(aut1);
		Classe generique = new Classe(10,5,0,"default class",Bonus.VIE);
		ArrayList<Classe> classes = new ArrayList<Classe>();
		classes.add(generique);
		Joueur j2 = new Joueur("Moi", autlist,classes);
		Joueur jZ = new Joueur("Zombie", autlist,classes);
		World.addPlayer(j2);
		World.addPlayer(jZ);*//*
		World.BuildMap(_tailleMapY,_tailleMapX);
		try {
			World.putAutomate(j2.automate(0), 1, 1, j2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		jZ.createPersonnage(0, 1, 1);
		jZ.createPersonnage(0, 1, 2);
		jZ.createPersonnage(0, 1, 3);
		jZ.createPersonnage(0, 1, 4);
		jZ.createPersonnage(0, 1, 5);
		jZ.createPersonnage(0, 1, 6);
		jZ.createPersonnage(0, 1, 7);
		jZ.createPersonnage(0, 1, 8);
		jZ.createPersonnage(0, 1, 9);*/
		//personnage = j2.getPersonnages().get(0);


		this.container = container;

		Player.sinit();


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
		this.map.render(g, _offsetMapX, _offsetMapY, zoom(), container.getWidth(), container.getHeight());
		//Affichage des personnages
		for(graphique.GJoueur j : _joueurs)
			for(Player p : j.getPersonnage())
					if(p.getX()+TileSize > _offsetMapX/zoom() && p.getY()+TileSize > _offsetMapY/zoom())
						if(p.getX()-TileSize < (_offsetMapX + container.getWidth())/zoom())
						if(p.getY()-TileSize < (_offsetMapY + container.getHeight())/zoom())
							p.render(g);

		//Annule la translation pour l'affichage du string en dessous
		g.resetTransform();
		g.setColor(Color.white);
		//Affichage de la position de la souris sur la map
		g.drawString(mouse, 10, 50);
		g.drawString("MouseX : " + mouseMapX() + ", MouseY : " + mouseMapY(), 10, 70);
		g.drawString("Zoom Avant : 'PRECEDENT', Zoom Arrière : 'SUIVANT', zoom : " + _zoom, 10, 90);
		g.drawString("offsetMapX : " + offsetMapX() + ", offsetMapY : " + offsetMapY(), 10, 110);

		//Affichage des huds
		if(showhud) {
			this.hud.render(g);
		}
		
		//Affichage fin
		if (World.fini) {
			if(World.joueurs().size()==1){
				g.drawString(World.joueurs().get(0).nom()+" a gagné! Félicitations à lui, vraiment.", container.getWidth()/2-175, container.getHeight()/2);
				g.resetTransform();
			}
		}
		
		//Gestion de la pause
		if (container.isPaused()) {
		    Rectangle rect = new Rectangle (0, 0, container.getScreenWidth(), container.getScreenHeight());
		    g.setColor(new Color (0, 0, 0, alpha));
		    g.fill(rect);
		    g.setColor(Color.white);
		    _bouton_fullScreen.render(container, g);
		    _bouton_son.render(container, g);
		    _bouton_quitter.render(container, g);
		    _bouton_menuPrincipal.render(container, g);
		    _bouton_reprendre.render(container, g);

		    if (alpha < 0.7f) {
		        alpha += 0.01f;
		    }
		}
		else {
		    if (alpha > 0) {
		        alpha -= 0.01f;
		    }
	    }
	}

	protected long _time = 0;

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Mise à jour des boutons
		_bouton_fullScreen.update(container);
		_bouton_son.update(container);
		_bouton_quitter.update(container);
		_bouton_reprendre.update(container);
		_bouton_menuPrincipal.update(container);
		for(graphique.GJoueur j : _joueurs)
			for(int i = j.getPersonnage().size()-1; i>=0; i--)
				if(j.getPersonnage().get(i).AnimDead>0)
					j.getPersonnage().get(i).update(delta);
				else
					j.getPersonnage().remove(j.getPersonnage().get(i));

		_time += delta;
		if(_time > Tick)
		{
			_time -= Tick;
			World.nextTurn();
			for(Animation anim : Player.animations)
				anim.restart();
			for(Animation anim : Player.Hanimations)
				anim.restart();
			for(Animation anim : Player.Danimations)
				anim.restart();
		}
		//playerData = "Coord X :" + this.player.getX() + ", Coord Y : " + this.player.getY() + ", action_finie : " + this.player.getAction_finie();
		mouseAbsoluteX = _input.getAbsoluteMouseX();// + offsetMapX();
		mouseAbsoluteY = _input.getAbsoluteMouseY();// + offsetMapY();
		_mouseMapX = (mouseAbsoluteX + offsetMapX()) / zoom();
		_mouseMapY = (mouseAbsoluteY + offsetMapY()) / zoom();
		mouse = "MouseAbsoluteX : " + mouseAbsoluteX + ", MouseAbsoluteY : " + mouseAbsoluteY;

		//Activer ou non l'attente
		if (_input.isKeyDown(Input.KEY_W)){
			if (TickWait){
				TickWait=false;
				AnimTick = Tick;
			}
			else{
				TickWait=true;
				AnimTick = Tick*6/10;
			}
			MoveSpeed = ((float)TileSize)/((float)AnimTick);
		}

		//Gestion de la vitesse du jeu
		//Acc�l�rer
		if (_input.isKeyDown(Input.KEY_N) && Tick > 125){
			if (Tick > 1050 ){
				Tick=Tick-100;
				if(TickWait)
					AnimTick = Tick*6/10;
				else
					AnimTick = Tick;
				MoveSpeed = ((float)TileSize)/((float)AnimTick);
			}
			else{
				if (Tick > 250 ){
					Tick=Tick-50;
					if(TickWait)
						AnimTick = Tick*6/10;
					else
						AnimTick = Tick;
					MoveSpeed = ((float)TileSize)/((float)AnimTick);
				}
				else{
					Tick=Tick-25;
					if(TickWait)
						AnimTick = Tick*6/10;
					else
						AnimTick = Tick;
					MoveSpeed = ((float)TileSize)/((float)AnimTick);
				}
			}
		}
		//Ralentir
		if (_input.isKeyDown(Input.KEY_B) && Tick < 4000){
			if (Tick < 950){
				Tick = Tick+50;
				if(TickWait)
					AnimTick = Tick*6/10;
				else
					AnimTick = Tick;
				MoveSpeed = ((float)TileSize)/((float)AnimTick);
			}
			else{
				Tick = Tick+100;
				if(TickWait)
					AnimTick = Tick*6/10;
				else
					AnimTick = Tick;
				MoveSpeed = ((float)TileSize)/((float)AnimTick);
			}
		}
		//R�-initialiser
		if (_input.isKeyDown(Input.KEY_V)){
			Tick = 1000;
			if(TickWait)
				AnimTick = Tick*6/10;
			else
				AnimTick = Tick;
			MoveSpeed = ((float)TileSize)/((float)AnimTick);
		}

		//Gestion du scrolling de la map avec la souris/manette/clavier
		if (mouseAbsoluteY == container.getHeight() || _input.isControllerDown(0) || _input.isKeyDown(208)) {
			setOffsetMapY(offsetMapY() + _scrollingSpeed);
		}
		if (mouseAbsoluteX == 0 || _input.isControllerLeft(0) || _input.isKeyDown(203)) {
			setOffsetMapX(offsetMapX() - _scrollingSpeed);
		}
		if (mouseAbsoluteX == container.getWidth() - 1 || _input.isControllerRight(0) || _input.isKeyDown(205)) {
			setOffsetMapX(offsetMapX() + _scrollingSpeed);
		}
		if (mouseAbsoluteY == 1 || _input.isControllerUp(0) || _input.isKeyDown(200)) {
			setOffsetMapY(offsetMapY() - _scrollingSpeed);
		}

		//Gestion du zoom
		//Zoom avant
		if (_input.isKeyDown(201))
		{
			setZoom(zoom() * 1.03f);
			setOffsetMapX(_mouseMapX*zoom() - mouseAbsoluteX);
			setOffsetMapY(_mouseMapY*zoom() - mouseAbsoluteY);
		}
		
		//Zoom arrière
		if (_tailleMapX * TileSize * zoom() > container.getWidth() && _tailleMapX * TileSize * zoom() > container.getHeight()) {
			if (_input.isKeyDown(209) && zoom() > 0) {
				setZoom(zoom() / 1.03f);
				if (zoom() < 0) {
					setZoom(0);
				}
				setOffsetMapX(_mouseMapX*zoom() - mouseAbsoluteX);
				setOffsetMapY(_mouseMapY*zoom() - mouseAbsoluteY);
			}
		}

		//Configuration de la pause
		if (_input.isKeyPressed(Input.KEY_ESCAPE)) {
			 container.setPaused(!container.isPaused());
		}

		//Configuration du bouton quitter
		if (container.isPaused()) {

			//Configuration du bouton pause
			 if (_bouton_reprendre.isPressed()) {
				 container.setPaused(!container.isPaused());
			}

			if (_bouton_quitter.isPressed()) {
				container.exit();
			}

			//Configuration du bouton plein écran
			if (_bouton_fullScreen.isPressed()) {
				_input.clearMousePressedRecord();
				if (container.isFullscreen()) {
					_bouton_fullScreen.setText("Plein écran");
					((AppGameContainer) container).setDisplayMode(800,600, false);

				} else {
					_bouton_fullScreen.setText("Fenêtré");
					((AppGameContainer) container).setDisplayMode(container.getScreenWidth(),container.getScreenHeight(), true);
				}
			}

			//Configuration du bouton son
			if (_bouton_son.isPressed()) {
				if (container.getMusicVolume() > 0) {
					container.setMusicVolume(0);
					container.setSoundVolume(0);
					_bouton_son.setText("Activer son");
				} else {
					container.setMusicVolume(100);
					container.setSoundVolume(100);
					_bouton_son.setText("Désactiver son");
				}
			}

			//Configuration du bouton menu principal
			if (_bouton_menuPrincipal.isPressed()) {
				_input.clearMousePressedRecord();
				this.game.enterState(MainScreenGameState.ID, "src/asset/musics/menu_music.ogg");
			}
		}

		//Configuration des boutons en plain écran
		_bouton_fullScreen.setLocation(container.getWidth()/2-62, container.getHeight()/2);
		_bouton_son.setLocation(container.getWidth()/2-62, container.getHeight()/2-40);
		_bouton_quitter.setLocation(container.getWidth()/2-62, container.getHeight()/2+80);
		_bouton_reprendre.setLocation(container.getWidth()/2-62, container.getHeight()/2-80);
		_bouton_menuPrincipal.setLocation(container.getWidth()/2-62, container.getHeight()/2+40);
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		_bouton_son.setText(container.getMusicVolume() > 0 ? "Désactiver son" : "Activer son");
		_bouton_fullScreen.setText(container.isFullscreen() ? "Fenêtré" : "Plein écran");
	}

	public void keyReleased(int key, char c) {
		if(showhud == false) return;
		switch(key)
		{
		case Input.KEY_Z:
			if(_targetp.directionJoueur() != Cardinaux.NORD)
				_targetp.setDirection(Cardinaux.NORD);
			else
				_targetp.setDirection(null);
		break;
		case Input.KEY_S:
			if(_targetp.directionJoueur() != Cardinaux.SUD)
				_targetp.setDirection(Cardinaux.SUD);
			else
				_targetp.setDirection(null);
		break;
		case Input.KEY_D:
			if(_targetp.directionJoueur() != Cardinaux.EST)
				_targetp.setDirection(Cardinaux.EST);
			else
				_targetp.setDirection(null);
		break;
		case Input.KEY_Q:
			if(_targetp.directionJoueur() != Cardinaux.OUEST)
				_targetp.setDirection(Cardinaux.OUEST);
			else
				_targetp.setDirection(null);
		break;
		}
	}

	/*public void keyPressed(int key, char c) {
		if (key == Input.KEY_A){
			this.game.enterState(3);
		}
	}*/

	public void mousePressed(int arg0, int arg1, int arg2) {
		for(graphique.GJoueur j : _joueurs)
			for(Player p : j.getPersonnage())
				if (Input.MOUSE_LEFT_BUTTON == arg0 && curseurSurPerso(p, mouseMapX(), mouseMapY())) {
				_target = p;
				_targetp = World.Case((int)(MapGameState._target.DestX()-Ox)/TileSize, (int)(MapGameState._target.DestY()-Oy)/TileSize).Personnage();
				this.showhud = true;
				return;
			}
	}

	//Gestion du zoom avec molette de souris
	public void mouseWheelMoved(int n) {
		if (n < 0) {
			if (zoom() > 0) {
				setZoom(zoom() / 1.10f);
			} else {
				setZoom(0);
			}
			setOffsetMapX(_mouseMapX*zoom() - mouseAbsoluteX);
			setOffsetMapY(_mouseMapY*zoom() - mouseAbsoluteY);
		} else if (n > 0) {
			setZoom(zoom() * 1.10f);
			setOffsetMapX(_mouseMapX*zoom() - mouseAbsoluteX);
			setOffsetMapY(_mouseMapY*zoom() - mouseAbsoluteY);
		}
	}

	//Méthode permettant de savoir si le curseur de la souris est sur le personnage
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

	public void setOffsetMapY(float y) {
		this._offsetMapY = y;

	}
	public void setGame(ArrayList<UnitInfo> uIFs) {

		int nb = 0;
		ArrayList<Automate> autlist = new ArrayList<Automate>();
		ArrayList<Classe> classes = new ArrayList<Classe>();
		for(UnitInfo ui : uIFs)
		{
			nb++;
			autlist.add(ui.automate);
			classes.add(ui.classe);
		}
		World.addPlayer(new Joueur("Human", autlist, classes));
		World.addPlayer(new Joueur("Zombie", autlist, classes));

		try {
			World.putAutomate(World.getPlayers().get(0).automate(0), 1, 1, World.getPlayers().get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//for(int i = 0; i < nb; i++)
			World.getPlayers().get(0).createPersonnage(classes.get(0), 1, 1);
		//for(int i = 0; i < nb; i++)
			World.getPlayers().get(1).createPersonnage(classes.get(classes.size()-1), _tailleMapX-1, _tailleMapY-1);

		Player pla;
		for(Joueur j : World.getPlayers())
		{
			_joueurs.add(new graphique.GJoueur((j == World.getPlayers().get(0))?TypeUnit.Human:TypeUnit.Zombie));
			j.addObserver(_joueurs.get(_joueurs.size()-1));
			for(Personnage pers : j.getPersonnages())
				_joueurs.get(_joueurs.size()-1).addPersonnage(pers);
		}
		try {
			this.map.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
