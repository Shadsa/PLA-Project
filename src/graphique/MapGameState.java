package graphique;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

import roles.Army;
import roles.Automate;
import roles.Cardinaux;
import roles.Joueur;
import roles.Personnage;
import roles.World;

public class MapGameState extends BasicGameState implements Observer {

	static int Tick = 1000;
	static boolean TickWait = false;
	static int AnimTick = Tick*6/10;
	protected long _time = 0;
	static final int TILESIZE = 96;
	static float MoveSpeed = ((float)TILESIZE)/((float)AnimTick);

	//private ArrayList<Joueur> jjj = new ArrayList<Joueur>();
	private Hud hud = new Hud();
	public static final int ID = 2;
	private String mouse;
	private float mouseAbsoluteX;
	private float mouseAbsoluteY;
	private float _mouseMapX;
	private float _mouseMapY;
	private boolean showhud = false;
	private float alpha = 0;
	private int secondeTime = 0;
	private int minuteTime = 0;

	public static MapTest _targetw = null;
	public static Player _target = null;
	public static Personnage _targetp = null;

	private float _offsetMapX = 0;
	private float _offsetMapY = 0;
	private int _tailleMapY ;
	private int _tailleMapX ;
	//Test
	//private MapTest map;
	private static MapTest _mainm;
	private World _mainw;
	private static ArrayList<MapTest> _GUnivers = new ArrayList<MapTest>();
	private Input _input;
	private StateGame game;
	public static boolean debug = false;
	private boolean enJeu = false;

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
		//_GUnivers.add(new MapTest(0, 0, container.getScreenWidth(), container.getScreenHeight()));
		//Instanciation des boutons
		_bouton_fullScreen = new Button(container, "Plein écran", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_son = new Button(container, "Désactiver son", container.getWidth()/2-62, container.getHeight()/2-40, normalImage, overImage, downImage);
		_bouton_quitter = new Button(container, "Quitter", container.getWidth()/2-62, container.getHeight()/2+80, normalImage, overImage, downImage);
		_bouton_menuPrincipal = new Button(container, "Menu principal", container.getWidth()/2-62, container.getHeight()/2+40, normalImage, overImage, downImage);
		_bouton_reprendre = new Button(container, "Reprendre", container.getWidth()/2-62, container.getHeight()/2-80, normalImage, overImage, downImage);

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
		/*for(MapTest mt : _GUnivers)
		{
			mt.render(g);
			//Annule la translation pour l'affichage du string en dessous
			g.resetTransform();
		}*/
		_mainm.render(g);
		g.resetTransform();

/*		//Affichage de la map (zoom + scrolling)
		g.translate(-_offsetMapX, -_offsetMapY);
		g.scale(_zoom, _zoom);
		this.map.render(g, _offsetMapX, _offsetMapY, zoom(), container.getWidth(), container.getHeight());



		//Affichage des personnages
		for(graphique.GJoueur j : _joueurs)
			for(Player p : j.getPersonnage())
					if(p.getX()+TILESIZE > _offsetMapX/zoom() && p.getY()+TILESIZE > _offsetMapY/zoom())
						if(p.getX()-TILESIZE < (_offsetMapX + container.getWidth())/zoom())
						if(p.getY()-TILESIZE < (_offsetMapY + container.getHeight())/zoom())
							p.render(g);
*/
		if(_targetp == null || _targetp.fightworld() == null || _targetp.fightworld().fini)
			_targetw = null;
		if(_targetw != null)
		{
			InitGameState.renderMenu(0, _mainm._height-488, 516, 416);
		    _targetw.render(g);
			g.resetTransform();
		}


		g.setColor(Color.white);

		//Affichage message de fin
		if (World.Univers.size() == 0) {
			g.drawString(_mainw.army().get(0).joueur().nom()+" a gagné! Félicitations à lui, vraiment.", container.getWidth()/2-175, container.getHeight()/2);
			g.resetTransform();
		}
		//Affichage des huds
		//this.hud.render(g);
		if(_target != null && !_target.isDead()) {
			this.hud.render(g, ((debug)? 110 : 0));
		}

		affichageDebug(g);

		affichageDetailJeu(g, (debug)?((_target != null)? 174 : 110) : ((_target != null)? 84 : 0));
		/*//Affichage message de fin
		if (World.fini) {
			if(World.joueurs().size()==1){
				g.drawString(World.joueurs().get(0).nom()+" a gagné! Félicitations à lui, vraiment.", container.getWidth()/2-175, container.getHeight()/2);
				g.resetTransform();
			}
		}*/

		//Gestion de la pause (affichage d'un fond noir-transparent progressif)
		if (container.isPaused()) {
			InitGameState.renderMenu(_bouton_reprendre.x-30, _bouton_reprendre.y-30, _bouton_quitter.x-_bouton_reprendre.x+_bouton_quitter.width+60, _bouton_quitter.y-_bouton_reprendre.y + _bouton_quitter.height+60);
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

		if(_GUnivers.size() == 0)
			_mainm.update(container, game, delta);
		else
			for(MapTest mt : _GUnivers)
				mt.update(container, game, delta);

		_time += delta;
		if(_time > Tick)
		{
			_time -= Tick;
			secondeTime++;

			if(World.Univers.size() == 0)
				_mainw.nextTurn();
			else
			{
				for(int i = 0; i<World.Univers.size(); i++)
				{
					if(World.Univers.get(i).fini)
					{
						for(int k=0; k<World.Univers.get(i).getArmys().size(); k++){
						for(Personnage p : World.Univers.get(i).getArmys().get(k).getPersonnages())
						{
							if(p.imageOF() != null)
								p.imageOF().setFighting(false, null);
						}
						}
						World.Univers.remove(i);
						_GUnivers.remove(i);
						i--;
					}
					else
						World.Univers.get(i).nextTurn();
				}
			}

			for(TypeUnit t : TypeUnit.values()){
				for(Animation anim : t.animations)
					anim.restart();
}
			for(Animation anim : Player.Danimations)
				anim.restart();
		}

		//Position de la souris
		mouseAbsoluteX = _input.getAbsoluteMouseX();
		mouseAbsoluteY = _input.getAbsoluteMouseY();

		/*_mouseMapY = (mouseAbsoluteY + offsetMapY()) / zoom();
		_mouseMapX = (mouseAbsoluteX + offsetMapX()) / zoom();*/
		mouse = "MouseAbsoluteX : " + mouseAbsoluteX + ", MouseAbsoluteY : " + mouseAbsoluteY;

		gestionVitesseDeJeu(Input.KEY_B, Input.KEY_N, Input.KEY_W, Input.KEY_V);

		//Gestion du scrolling de la map avec la souris/manette/clavier
		MapTest focus = (_targetw != null && _targetw.isOver(_input.getMouseX(), _input.getMouseY()))?_targetw : _mainm;
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
			focus.setZoom(1.03f, _input.getMouseX(), _input.getMouseY());
		}

		//Zoom arrière
		if (_tailleMapX * TILESIZE * focus.zoom() > container.getWidth() && _tailleMapX * TILESIZE * focus.zoom() > container.getHeight()) {
			if (_input.isKeyDown(209) && focus.zoom() > 0) {
				focus.setZoom(1/1.03f, _input.getAbsoluteMouseX(), _input.getAbsoluteMouseY());
			}
		}

		//TODO ici gestion zoom
		//gestionZoomClavier(container, Input.KEY_NEXT, Input.KEY_PRIOR, 1.03f);

		//Configuration de la pause
		if (_input.isKeyPressed(Input.KEY_ESCAPE)) {
			 container.setPaused(!container.isPaused());
		}


		if (container.isPaused()) {

			//Configuration du bouton menu principal
			if (_bouton_menuPrincipal.isPressed()) {
				container.setPaused(false);
				_input.clearMousePressedRecord();
				World.resetJoueurs();
				enJeu = false;
				_mainm.resetArmy();
				World.Univers.clear();
				_GUnivers.clear();
				//_joueurs.clear();
				this.game.enterState(MainScreenGameState.ID, "src/asset/musics/menu_music.ogg");
			}

			//Configuration du bouton pause
			 if (_bouton_reprendre.isPressed()) {
				 container.setPaused(!container.isPaused());
			}

			//Configuration du bouton quitter
			if (_bouton_quitter.isPressed()) {
				secondeTime = 0;
				minuteTime = 0;
				container.exit();
			}

			//Configuration du bouton plein écran
			if (_bouton_fullScreen.isPressed()) {
				_input.clearMousePressedRecord();
				if (container.isFullscreen()) {
					_bouton_fullScreen.setText("Plein écran");
					((AppGameContainer) container).setDisplayMode(1200,700, false);

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

		}

		autoDeplacementBouton(container);

		//Update du debug
		if (_input.isKeyPressed(Input.KEY_F3)) {
			debug = !debug;
			container.setShowFPS(debug);
		}
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

	/**
	 * Notification qu'un bouton de la souris a été pressée (la méthode est appelée à ce moment là).
	 * @param button L'identifiant du bouton.
	 * @param x La coordonnée x (abcisse) lors de la pression sur le bouton.
	 * @param x La coordonnée y (ordonnée) lors de la pression sur le bouton.
	 */
	public void mousePressed(int button, int x, int y) {

		if(x >= _mainm.getX() && y >= _mainm.getY() && x<=_mainm.getX()+_mainm.getWidth() && y<=_mainm.getY()+_mainm.getHeight())
			_mainm.mousePressed(button, x, y);
	}

	/**
	 * Notification que la roulette de la souris a été bougée (la méthode est appelée à ce moment là).
	 * @param n Sens du mouvement de la roulette.
	 */
	public void mouseWheelMoved(int n) {
		MapTest focus = (_targetw != null && _targetw.isOver(_input.getMouseX(), _input.getMouseY()))?_targetw : _mainm;
		if (n > 0) {
			focus.setZoom(1.03f, _input.getMouseX(), _input.getMouseY());



		} else if (n < 0) {
			//Zoom arrière
			if (_tailleMapX * TILESIZE * focus.zoom() > _mainm.getWidth() && _tailleMapX * TILESIZE * focus.zoom() > _mainm.getHeight()) {
				focus.setZoom(1/1.03f, _input.getAbsoluteMouseX(), _input.getAbsoluteMouseY());
			}
		}
	}

	/**
	 * Vérification que le curseur de la souris est sur le personnage.
	 * @param p
	 * @param mouseX La position de la souris en abcisse X.
	 * @param mouseY La position de la souris en ordonnée Y.
	 * @return Un booléen qui indique si la position du curseur est sur un personnage.
	 * @throws Exception
	 * @require p != null
	 */
	public boolean curseurSurPerso(Player p, float mouseX, float mouseY) throws Exception {
		if (!(p!= null)) {
			throw new Exception("p == null)");
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

	/**
	 * Affichage des informations pour le debug. Il n'est pas affiché de base.
	 * @param g Le contexte graphique utilisé pour l'affichage des informations.
	 */
	public void affichageDebug(Graphics g) {
		if (debug) {
			g.resetTransform();
			g.setColor(Color.white);
			g.drawString(mouse, 10, 30);
			g.drawString("MouseX : " + mouseMapX() + ", MouseY : " + mouseMapY(), 10, 50);
			//g.drawString("Zoom Avant : 'PRECEDENT', Zoom Arrière : 'SUIVANT', zoom : " + _zoom, 10, 70);
			g.drawString("offsetMapX : " + offsetMapX() + ", offsetMapY : " + offsetMapY(), 10, 90);
		}
	}

	/**
	 * Gestion de la vitesse de jeu pendant la partie.
	 * @param ralentir Code de la touche choisie pour ralentir le jeu.
	 * @param accelerer Code de la touche choisie pour accélerer le jeu.
	 * @param attente Code de la touche choisie pour activer/désactiver l'attente entre chaque tour le jeu.
	 * @param reinit Code de la touche choisie pour réinitialiser la vitesse du jeu.
	 */
    public void gestionVitesseDeJeu(int ralentir, int accelerer, int attente, int reinit) {
	// Activer ou non l'attente
	if (_input.isKeyDown(attente)) {
	    if (TickWait) {
		TickWait = false;
		AnimTick = Tick;
	    } else {
		TickWait = true;
		AnimTick = Tick * 6 / 10;
	    }
	    MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
	}
	// Gestion de la vitesse du jeu
	// Acc�l�rer
	if (_input.isKeyDown(accelerer) && Tick > 125) {
	    if (Tick > 1050) {
		Tick = Tick - 100;
		if (TickWait)
		    AnimTick = Tick * 6 / 10;
		else
		    AnimTick = Tick;
		MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
	    } else {
		if (Tick > 250) {
		    Tick = Tick - 50;
		    if (TickWait)
			AnimTick = Tick * 6 / 10;
		    else
			AnimTick = Tick;
		    MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
		} else {
		    Tick = Tick - 25;
		    if (TickWait)
			AnimTick = Tick * 6 / 10;
		    else
			AnimTick = Tick;
		    MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
		}
	    }
	}
	// Ralentir
	if (_input.isKeyDown(ralentir) && Tick < 4000) {
	    if (Tick < 950) {
		Tick = Tick + 50;
		if (TickWait)
		    AnimTick = Tick * 6 / 10;
		else
		    AnimTick = Tick;
		MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
	    } else {
		Tick = Tick + 100;
		if (TickWait)
		    AnimTick = Tick * 6 / 10;
		else
		    AnimTick = Tick;
		MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
	    }
	}
	// R�-initialiser
	if (_input.isKeyDown(reinit)) {
	    Tick = 1000;
	    if (TickWait)
		AnimTick = Tick * 6 / 10;
	    else
		AnimTick = Tick;
	    MoveSpeed = ((float) TILESIZE) / ((float) AnimTick);
	}
    }

	/**
	 * Gestion de l'affichage des détails du jeu (joueurs, temps).
	 * @param g Le contexte graphique utilisé pour afficher les informations.
	 */
	public void affichageDetailJeu(Graphics g, int ty) {
		g.resetTransform();
		g.setColor(Color.white);

		//Affichage des détails des joueurs
			for(Army arm : _mainw.army())
			{
				Joueur j = arm.joueur();
				g.drawString(j.nom(), 10, ty);ty+=20;
				g.drawString("  " + "Ressources : " + j.ressources(), 10, ty);ty+=20;
				for(Automate aut : j.Automates())
				{
					g.drawString("     " + aut.nom() + " : " + arm.nbUnit(j.getUnite(aut)), 10, ty);ty+=20;
				}
				ty+=30;
			}

		//Affichage compteur de tours
		if (secondeTime >= 60) {
			secondeTime = 0;
			minuteTime++;
		}
		g.drawString("Temps de jeu : " + minuteTime + ":" + secondeTime, 10, ty);
	}

	/**
	 * Gestion de l'adaptation de la position des boutons en fonction de la taille de la fenêtre.
	 * @param container Le conteneur du jeu.
	 */
	public void autoDeplacementBouton(GameContainer container) {
		_bouton_fullScreen.setLocation(container.getWidth()/2-62, container.getHeight()/2);
		_bouton_son.setLocation(container.getWidth()/2-62, container.getHeight()/2-40);
		_bouton_quitter.setLocation(container.getWidth()/2-62, container.getHeight()/2+80);
		_bouton_reprendre.setLocation(container.getWidth()/2-62, container.getHeight()/2-80);
		_bouton_menuPrincipal.setLocation(container.getWidth()/2-62, container.getHeight()/2+40);
	}

	public void setGame(ArrayList<UnitInfo> uIFs1, ArrayList<UnitInfo> uIFs2, MapTest map) {
	    _GUnivers.add(map);
		_GUnivers.get(0).addObserver(this);
		_mainm = _GUnivers.get(0);
		_mainw = World.Univers.get(0);

		_tailleMapX = _mainw.SizeX();
		_tailleMapY = _mainw.SizeY();

		if (!enJeu) {
			new Army(_mainw, World.joueurs.get(0));
			new Army(_mainw, World.joueurs.get(1));

			_mainw.army().get(0).createPersonnage(0, 1, 1, null);
			_mainw.army().get(1).createPersonnage(0, _tailleMapX-1, _tailleMapY-1, null);
			for(Army a : _mainw.army())
			{
				_GUnivers.get(0).addArmy(a);
			}
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
				if(_target != null && _targetp != _mainw.Case(x, y).Personnage())
				{
					_targetp = _mainw.Case(x, y).Personnage();
					// Si il y a eu une imprécision (rare)
					if(_targetp == null)
					{
						_target = null;
						_targetw = null;
						return;
					}
					if(_targetp.fightworld() != null && World.Univers.indexOf(_targetp.fightworld()) != -1)
						_targetw = _GUnivers.get(World.Univers.indexOf(_targetp.fightworld()));
					else
						_targetw = null;
				}
				else
				{
					_target = null;
					_targetp = null;
					_targetw = null;
				}
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

	}

	public static void fight(Personnage pers, Personnage cible, Cardinaux dirinc)
	{
		if(cible.isfighting())
		{
			World w = cible.fightworld();
			pers.setFighting(true, w);
			for(Army arm : w.army())
				if(arm.joueur() == pers.owner().joueur())
				{
					arm.join(pers.owner().joueur().getUnite(pers), dirinc, pers);
					return;
				}

			// jamais éxécuté à 2 joueurs
			_GUnivers.get(World.Univers.indexOf(w)).addArmy(new Army(w, pers.owner().joueur()));
			w.army().get(w.army().size()-1).createPersonnage(pers.owner().joueur().getUnite(pers), 0, 0, pers);

		}
		World w = new World(7, 7, true);
		pers.setFighting(true, w);
		cible.setFighting(true, w);

		World.Univers.add(w);
		MapTest mt = new MapTest(8, _mainm._height-480, 500, 400);
		mt.initialise(w);
		_GUnivers.add(mt);
		mt.addArmy(new Army(w, pers.owner().joueur()));
		w.army().get(0).join(pers.owner().joueur().getUnite(pers), dirinc, pers);
		mt.addArmy(new Army(w, cible.owner().joueur()));
		w.army().get(1).join(cible.owner().joueur().getUnite(cible), Cardinaux.oppose(dirinc), cible);
		mt.setZoom(0.5f, 0f, 0f);
	}
}
