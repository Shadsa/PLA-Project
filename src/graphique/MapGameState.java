package graphique;

import java.util.ArrayList;
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
import roles.Automate;
import roles.Cardinaux;
import roles.Joueur;
import roles.Personnage;
import roles.World;

public class MapGameState extends BasicGameState {

	static int Tick = 1000;
	static boolean TickWait = false;
	static int AnimTick = Tick*6/10;
	protected long _time = 0;
	static final int TILESIZE = 96;
	static float MoveSpeed = ((float)TILESIZE)/((float)AnimTick);
	static final float Ox = 48;
	static final float Oy = 48;

	private ArrayList<graphique.GJoueur> _joueurs = new ArrayList<graphique.GJoueur>();
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

	private float _offsetMapX = 0;
	private float _offsetMapY = 0;

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

	public static float toX(int x)
	{
		return x * TILESIZE + Ox;
	}
	public static float toY(int y)
	{
		return y * TILESIZE + Oy;
	}

	public static int fromX(float x)
	{
		return (int) (x - Ox) / TILESIZE;
	}
	public static int fromY(float y)
	{
		return (int) (y - Oy) / TILESIZE;
	}
	
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
		//Instanciation des boutons
		_bouton_fullScreen = new Button(container, "Plein écran", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_son = new Button(container, "Désactiver son", container.getWidth()/2-62, container.getHeight()/2-40, normalImage, overImage, downImage);
		_bouton_quitter = new Button(container, "Quitter", container.getWidth()/2-62, container.getHeight()/2+80, normalImage, overImage, downImage);
		_bouton_menuPrincipal = new Button(container, "Menu principal", container.getWidth()/2-62, container.getHeight()/2+40, normalImage, overImage, downImage);
		_bouton_reprendre = new Button(container, "Reprendre", container.getWidth()/2-62, container.getHeight()/2-80, normalImage, overImage, downImage);
		//Initialisation du monde
		World.BuildMap(_tailleMapY,_tailleMapX);
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
		this.map.render(g, _offsetMapX, _offsetMapY, zoom(), container.getWidth(), container.getHeight());
		
		//Affichage des personnages
		for(graphique.GJoueur j : _joueurs)
			for(Player p : j.getPersonnage())
					if(p.getX()+TILESIZE > _offsetMapX/zoom() && p.getY()+TILESIZE > _offsetMapY/zoom())
						if(p.getX()-TILESIZE < (_offsetMapX + container.getWidth())/zoom())
						if(p.getY()-TILESIZE < (_offsetMapY + container.getHeight())/zoom())
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
		
		//Affichage ressources
		if (World.getPlayers().size() == 2) {
			g.drawString("Ressources : J1 " + World.getPlayers().get(0).ressources(), 10, 250);
			g.drawString("Ressources : J2 " + World.getPlayers().get(1).ressources(), 10, 270);
		}
		
		//Affichage message de fin
		if (World.fini) {
			if(World.joueurs().size()==1){
				g.drawString(World.joueurs().get(0).nom()+" a gagné! Félicitations à lui, vraiment.", container.getWidth()/2-175, container.getHeight()/2);
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
			for(TypeUnit t : TypeUnit.values()){
				for(Animation anim : t.animations)
					anim.restart();
			}
			/*for(Animation anim : Player.Hanimations)
				anim.restart();*/
			for(Animation anim : Player.Danimations)
				anim.restart();
		}

		//Position de la souris par rapport au conteneur
		mouseAbsoluteX = _input.getAbsoluteMouseX();
		mouseAbsoluteY = _input.getAbsoluteMouseY();
		//Position de la souris sur la map
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
		if (_tailleMapX * TILESIZE * zoom() > container.getWidth() && _tailleMapX * TILESIZE * zoom() > container.getHeight()) {
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
		for(graphique.GJoueur j : _joueurs)
			for(Player p : j.getPersonnage())
				if (Input.MOUSE_LEFT_BUTTON == button && curseurSurPerso(p, mouseMapX(), mouseMapY())) {
				_target = p;
				_targetp = World.Case((int)(MapGameState._target.DestX()-Ox)/TILESIZE, (int)(MapGameState._target.DestY()-Oy)/TILESIZE).Personnage();
				this.showhud = true;
				return;
			}
	}

	/**
	 * Notification que la roulette de la souris a été bougée (la méthode est appelée à ce moment là).
	 * @param n Sens du mouvement de la roulette.
	 */
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
	public void setGame(ArrayList<UnitInfo> uIFs1, ArrayList<UnitInfo> uIFs2) {

		//int nb = 0;
		ArrayList<ArrayList<Automate>> autlist = new ArrayList<ArrayList<Automate>>();
		//ArrayList<Automate> autlist2 = new ArrayList<Automate>();
		ArrayList<ArrayList<Classe>> classes = new ArrayList<ArrayList<Classe>>();
		//ArrayList<Classe> classes2 = new ArrayList<Classe>();
		ArrayList<ArrayList<TypeUnit>> type_unit = new ArrayList<ArrayList<TypeUnit>>();
		//ArrayList<TypeUnit> type_unit2 = new ArrayList<TypeUnit>();
		ArrayList<ArrayList<TypeClothes>> type_clothes = new ArrayList<ArrayList<TypeClothes>>();
		//ArrayList<TypeClothes> type_clothes2 = new ArrayList<TypeClothes>();
		for(UnitInfo ui : uIFs1)
		{
			//nb++;
			autlist.add(new ArrayList<Automate>());
			classes.add(new ArrayList<Classe>());
			type_unit.add(new ArrayList<TypeUnit>());
			type_clothes.add(new ArrayList<TypeClothes>());
			autlist.get(0).add(ui.automate);
			classes.get(0).add(ui.classe);
			type_unit.get(0).add(ui.color);
			type_clothes.get(0).add(ui.clothes);
		}
		for(UnitInfo ui : uIFs2) {
			//nb++;
			autlist.add(new ArrayList<Automate>());
			classes.add(new ArrayList<Classe>());
			type_unit.add(new ArrayList<TypeUnit>());
			type_clothes.add(new ArrayList<TypeClothes>());
			autlist.get(1).add(ui.automate);
			classes.get(1).add(ui.classe);
			type_unit.get(1).add(ui.color);
			type_clothes.get(1).add(ui.clothes);
		}
		World.addPlayer(new Joueur("Joueur1", autlist.get(0), classes.get(0)));
		World.addPlayer(new Joueur("Joueur2", autlist.get(1), classes.get(1)));

		try {
			World.putAutomates(World.getPlayers().get(0).Automates(), 1, 1, World.getPlayers().get(0));
			World.putAutomates(World.getPlayers().get(1).Automates(),_tailleMapX-1, _tailleMapY-1, World.getPlayers().get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//for(int i = 0; i < nb; i++)
		World.getPlayers().get(0).createPersonnage(0, 1, 1);
		//for(int i = 0; i < nb; i++)
		World.getPlayers().get(1).createPersonnage(0, _tailleMapX-1, _tailleMapY-1);

		int i=0;
		for(Joueur j : World.getPlayers())
		{
			_joueurs.add(new GJoueur(type_unit.get(i),type_clothes.get(i)));
			j.addObserver(_joueurs.get(_joueurs.size()-1));
			for(Personnage pers : j.getPersonnages())
				_joueurs.get(_joueurs.size()-1).addPersonnage(pers);
			i++;
		}
		try {
			this.map.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
