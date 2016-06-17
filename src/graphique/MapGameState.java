package graphique;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import jus.util.assertion.Require;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import roles.classe.*;
import roles.conditions.Ennemi;
import roles.conditions.Libre;
import roles.Army;
import roles.Automate;
import roles.Cardinaux;
import roles.Joueur;
import roles.Personnage;
import roles.World;
import roles.action.Duel;

public class MapGameState extends BasicGameState implements Observer {

	static int Tick = 1000;
	static boolean TickWait = false;
	static int AnimTick = Tick*6/10;
	protected long _time = 0;
	static final int TILESIZE = 96;
	static float MoveSpeed = ((float)TILESIZE)/((float)AnimTick);

	private ArrayList<Joueur> jjj = new ArrayList<Joueur>();
	private Hud hud = new Hud();
	public static final int ID = 2;
	private String mouse;
	private float mouseAbsoluteX;
	private float mouseAbsoluteY;
	private float _mouseMapX;
	private float _mouseMapY;
	private boolean showhud = false;
	private float alpha = 0;

	public static Player _target = null;
	public static Personnage _targetp = null;

	private int _tailleMapY = 45;
	private int _tailleMapX = 75;

	//Test
	private static ArrayList<MapTest> _GUnivers = new ArrayList<MapTest>();
	private Input _input;
	private StateGame game;

	//Boutons
	private Button _bouton_fullScreen;
	private Button _bouton_son;
	private Button _bouton_quitter;
	private Button _bouton_reprendre;
	private Button _bouton_menuPrincipal;


	/**
	 * Initialise la boucle de jeu. Cette méthode est appelée avant que la boucle démarre.
	 * @param container Le conteneur du jeu dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		_input = container.getInput();
		this.game = (StateGame) game;
		//Initialisation des images boutons
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
		_GUnivers.add(new MapTest(0, 0, container.getScreenWidth(), container.getScreenHeight()));
		//Instanciation des boutons
		_bouton_fullScreen = new Button(container, "Plein écran", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_son = new Button(container, "Désactiver son", container.getWidth()/2-62, container.getHeight()/2-40, normalImage, overImage, downImage);
		_bouton_quitter = new Button(container, "Quitter", container.getWidth()/2-62, container.getHeight()/2+80, normalImage, overImage, downImage);
		_bouton_menuPrincipal = new Button(container, "Menu principal", container.getWidth()/2-62, container.getHeight()/2+40, normalImage, overImage, downImage);
		_bouton_reprendre = new Button(container, "Reprendre", container.getWidth()/2-62, container.getHeight()/2-80, normalImage, overImage, downImage);
		//Initialisation du monde
		//Initialisation des animations des personnages
		Player.sinit();
		//Initialisation de l'hud
		this.hud.init();
	}

	/**
	 * Affichage des éléments du jeu dans le conteneur.
	 * @param container Le conteneur du jeu dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 * @param g Le contexte graphique qui peut être utilisé pour afficher les éléments.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//Affichage de la map
		for(MapTest mt : _GUnivers)
		{
			mt.render(g);
			//Annule la translation pour l'affichage du string en dessous
			g.resetTransform();
		}

		g.setColor(Color.white);
		//Affichage de la position de la souris sur la map
		g.drawString(mouse, 10, 50);
		g.drawString("MouseX : " + mouseMapX() + ", MouseY : " + mouseMapY(), 10, 70);
		//g.drawString("Zoom Avant : 'PRECEDENT', Zoom Arrière : 'SUIVANT', zoom : " + _zoom, 10, 90);
		//g.drawString("offsetMapX : " + offsetMapX() + ", offsetMapY : " + offsetMapY(), 10, 110);

		//Affichage des huds
		this.hud.render(g);

		//Affichage message de fin
		if (World.Univers.get(0).fini) {
			if(World.Univers.get(0).army().size()==1){
				g.drawString(World.Univers.get(0).army().get(0).joueur().nom()+" a gagné! Félicitations à lui, vraiment.", container.getWidth()/2-175, container.getHeight()/2);
				g.resetTransform();
			}
		}

		//Gestion de la pause (affichage d'un fond noir-transparent progressif)
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


	/**
	 * Mise à jour des attributs et des éléments du conteneur.
	 * @param container Le conteneur du jeu dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 * @param delta Le temps mis entre chaque mise à jour en millisecondes.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Mise à jour des boutons
		_bouton_fullScreen.update(container);
		_bouton_son.update(container);
		_bouton_quitter.update(container);
		_bouton_reprendre.update(container);
		_bouton_menuPrincipal.update(container);

		for(MapTest mt : _GUnivers)
			mt.update(container, game, delta);

		_time += delta;
		if(_time > Tick)
		{
			_time -= Tick;
			for(int i = 0; i<World.Univers.size(); i++)
				World.Univers.get(i).nextTurn();

			for(TypeUnit t : TypeUnit.values()){
				for(Animation anim : t.animations)
					anim.restart();
}
			for(Animation anim : Player.Danimations)
				anim.restart();
		}

		//Position de la souris par rapport au conteneur
		mouseAbsoluteX = _input.getAbsoluteMouseX();
		mouseAbsoluteY = _input.getAbsoluteMouseY();
		//Position de la souris sur la map
		/*_mouseMapX = (mouseAbsoluteX + offsetMapX()) / zoom();
		_mouseMapY = (mouseAbsoluteY + offsetMapY()) / zoom();*/
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
			MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
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
				MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
			}
			else{
				if (Tick > 250 ){
					Tick=Tick-50;
					if(TickWait)
						AnimTick = Tick*6/10;
					else
						AnimTick = Tick;
					MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
				}
				else{
					Tick=Tick-25;
					if(TickWait)
						AnimTick = Tick*6/10;
					else
						AnimTick = Tick;
					MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
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
				MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
			}
			else{
				Tick = Tick+100;
				if(TickWait)
					AnimTick = Tick*6/10;
				else
					AnimTick = Tick;
				MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
			}
		}
		//R�-initialiser
		if (_input.isKeyDown(Input.KEY_V)){
			Tick = 1000;
			if(TickWait)
				AnimTick = Tick*6/10;
			else
				AnimTick = Tick;
			MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
		}

		//Gestion du scrolling de la map avec la souris/manette/clavier
		MapTest focus = (_GUnivers.size() > 1 && _GUnivers.get(1).isOver(_input.getMouseX(), _input.getMouseY()))?_GUnivers.get(1) : _GUnivers.get(0);
		if (mouseAbsoluteY == container.getHeight() || _input.isControllerDown(0) || _input.isKeyDown(208)) {
			focus.move(0, 1);
		}
		if (mouseAbsoluteX == 0 || _input.isControllerLeft(0) || _input.isKeyDown(203)) {
			focus.move(-1, 0);
		}
		if (mouseAbsoluteX == container.getWidth() - 1 || _input.isControllerRight(0) || _input.isKeyDown(205)) {
			focus.move(1, 0);
		}
		if (mouseAbsoluteY == 1 || _input.isControllerUp(0) || _input.isKeyDown(200)) {
			focus.move(0, -1);
		}

		//Gestion du zoom
		//Zoom avant
		if (_input.isKeyDown(201))
		{
			focus.setZoom(1.01f, _input.getMouseX(), _input.getMouseY());
		}

		//Zoom arrière
		if (_tailleMapX * TILESIZE * focus.zoom() > container.getWidth() && _tailleMapX * TILESIZE * focus.zoom() > container.getHeight()) {
			if (_input.isKeyDown(209) && focus.zoom() > 0) {
				focus.setZoom(1/1.03f, _input.getAbsoluteMouseX(), _input.getAbsoluteMouseY());
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

	/**
	 * Notification que l'on entre dans cette boucle de jeu.
	 * @param container Le contexte dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 */
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		_bouton_son.setText(container.getMusicVolume() > 0 ? "Désactiver son" : "Activer son");
		_bouton_fullScreen.setText(container.isFullscreen() ? "Fenêtré" : "Plein écran");
	}

	/**
	 * Notification qu'une touche du clavier a été relâchée (la méthode est appelée à ce moment là).
	 * @param key Le numéro de la touche de clavier.
	 * @param c le caractère de la touche relâchée.
	 */
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

	/**
	 * Notification qu'un bouton de la souris a été pressée (la méthode est appelée à ce moment là).
	 * @param button L'identifiant du bouton.
	 * @param x La coordonnée x (abcisse) lors de la pression sur le bouton.
	 * @param x La coordonnée y (ordonnée) lors de la pression sur le bouton.
	 */
	public void mousePressed(int button, int x, int y) {

		if(x >= _GUnivers.get(0).getX() && y >= _GUnivers.get(0).getY() && x<=_GUnivers.get(0).getX()+_GUnivers.get(0).getWidth() && y<=_GUnivers.get(0).getY()+_GUnivers.get(0).getHeight())
			_GUnivers.get(0).mousePressed(button, x, y);
		if(_GUnivers.size()>1)
		if(x >= _GUnivers.get(1).getX() && y >= _GUnivers.get(1).getY() && x<=_GUnivers.get(1).getX()+_GUnivers.get(1).getWidth() && y<=_GUnivers.get(1).getY()+_GUnivers.get(1).getHeight())
			_GUnivers.get(1).mousePressed(button, x, y);
		//super.mousePressed(button, x, y);
		/*for(graphique.GJoueur j : _joueurs)
			for(Player p : j.getPersonnage())
				if (Input.MOUSE_LEFT_BUTTON == button && curseurSurPerso(p, mouseMapX(), mouseMapY())) {
				_target = p;
				_targetp = world0.Case((int)(MapGameState._target.DestX()-Ox)/TILESIZE, (int)(MapGameState._target.DestY()-Oy)/TILESIZE).Personnage();
				this.showhud = true;
				return;
			}*/
		//map.mousePressed(button, x, y);
	}

	/**
	 * Notification que la roulette de la souris a été bougée (la méthode est appelée à ce moment là).
	 * @param n Sens du mouvement de la roulette.
	 */
	public void mouseWheelMoved(int n) {
		/*if (n < 0) {
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
		}*/
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

	public void setTarget(Player p, int x, int y)
	{
		_target = p;
		_targetp = World.Univers.get(0).Case(x, y).Personnage();
	}


	/*public float offsetMapX() {
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

	}*/
	public void setGame(ArrayList<UnitInfo> uIFs) {
		try {
			MapTest.init();
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		World.Univers.add(new World(_tailleMapY,_tailleMapX));
		_GUnivers.get(0).initialise(World.Univers.get(0));
		_GUnivers.get(0).addObserver(this);

		ArrayList<Automate> autlist = new ArrayList<Automate>();
		ArrayList<Classe> classes = new ArrayList<Classe>();
		for(UnitInfo ui : uIFs)
		{
			ui.automate.ajoute_transition(0, new Duel(Cardinaux.OUEST), new Ennemi(Cardinaux.OUEST), 0, 100);
			autlist.add(ui.automate);
			classes.add(ui.classe);
		}
		jjj.add(new Joueur("Human", autlist, classes));
		jjj.add(new Joueur("Zombie", autlist, classes));
		new Army(World.Univers.get(0), jjj.get(0));
		new Army(World.Univers.get(0), jjj.get(1));

		try {
			World.Univers.get(0).putAutomate(jjj.get(0).automate(0), 1, 1, jjj.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//for(int i = 0; i < nb; i++)
		World.Univers.get(0).army().get(0).createPersonnage(0, 1, 1);
	//for(int i = 0; i < nb; i++)
		World.Univers.get(0).army().get(1).createPersonnage(classes.size()-1, _tailleMapX-1, _tailleMapY-1);

		for(Army a : World.Univers.get(0).army())
		{
			_GUnivers.get(0).addArmy(a);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof MapTest)
		{
			try {
				int x = (arg1.getClass().getDeclaredField("mx").getInt(arg1));
				int y = (arg1.getClass().getDeclaredField("my").getInt(arg1));
				_target = ((Player)arg1.getClass().getDeclaredField("mtargetp").get(arg1));
				if(_targetp != World.Univers.get(0).Case(x, y).Personnage())
				{
					_targetp = World.Univers.get(0).Case(x, y).Personnage();
				}
				else
				{
					_target = null;
					_targetp = null;
				}
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public static void fight(Personnage pers, Personnage personnage)
	{
		World w = new World(7, 7);
		World.Univers.add(w);
		MapTest mt = new MapTest(0, 0, 300, 300);
		mt.initialise(w);
		_GUnivers.add(mt);
		w.addArmy(new Army(w, pers.owner().joueur()));
		w.army().get(0).createPersonnage(pers.owner().joueur().getUnite(pers), 0, 0);
		mt.addArmy(w.army().get(0));
		w.addArmy(new Army(w, personnage.owner().joueur()));
		w.army().get(0).createPersonnage(personnage.owner().joueur().getUnite(personnage), 6, 6);


	}
}
