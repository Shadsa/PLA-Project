package graphique;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cases.Case;
import roles.Automate;
import roles.Carte;
import roles.Joueur;
import roles.World;
import roles.classe.Classe;
import graphique.Button;
import graphique.InitGameState;
import graphique.MapTest;
import graphique.MapGameState;

class DragAndDropState extends BasicGameState {
	public static final int ID = 3;
	private final int _tailleMapY = 45;
	private final int _tailleMapX = 75;
	private MapTest map ;
	private float _offsetMapX=0;
	private float _offsetMapY=0;
	private int mapSizeX = _tailleMapX * MapGameState.TILESIZE;;
	private int mapSizeY = _tailleMapY * MapGameState.TILESIZE;;
	private boolean pause = false;
	private boolean saveMode = false;
	private ArrayList<UnitInfo> UIFs1;
	private ArrayList<UnitInfo> UIFs2;
	private int _scrollingSpeed = 15;
	private float _zoom = 1;
	private float alpha = 0;
	//private static StateBasedGame game;
	private Input _input;
	int compt_clic = 0; //compteur de clic
	int x1; //variable de sauvegarde des coordonnées
	int y1;
	Case c01;
	Case c02;
	private Button _bouton_Jouer;
	private Button _bouton_sauvegarder;
	private Button _bouton_charger;
	private Button _bouton_placerAutomate;
	private Button _bouton_confirmer;
	
	private TextField textInput;
	private UnicodeFont ttf;
	
	private World _mainw ;
	
	//private static StateGame game;

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		_input = container.getInput();
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
		map = new MapTest (0, 0, container.getScreenWidth(), container.getScreenHeight());
		_bouton_Jouer = new Button(container, "Jouer", container.getWidth()-150, container.getHeight()-50, normalImage, overImage, downImage);
		_bouton_sauvegarder = new Button(container, "Sauvegarder", container.getWidth()-150, 10, normalImage, overImage, downImage);
		_bouton_charger = new Button(container, "Charger", container.getWidth()-150, 50, normalImage, overImage, downImage);
		_bouton_placerAutomate = new Button(container, "Placer Automate", container.getWidth()-150, 90, normalImage, overImage, downImage);
		_bouton_confirmer = new Button(container, "Confirmer", container.getWidth()-150, 130, normalImage, overImage, downImage);
		setFont("Arial", 20);
		textInput = new TextField (container, ttf, container.getWidth()/2-150, container.getHeight()/2-40, 300, 28);
		textInput.setBorderColor(Color.white);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		//Affichage de la map
		/*g.translate(-_offsetMapX, -_offsetMapY);
		g.scale(_zoom, _zoom);
		this.map.render(g, _offsetMapX, _offsetMapY, zoom(), container.getWidth(), container.getHeight());*/
	    	this.map.render(g);
	    	g.resetTransform();
		_bouton_Jouer.render(container, g);
		_bouton_sauvegarder.render(container, g);
		_bouton_charger.render(container, g);
		_bouton_placerAutomate.render(container, g);
		if (c01 != null) {
			g.drawImage(Hud.playerBars, -offsetMapX() + toX(c01.X()), -offsetMapY() + toY(c01.Y()),-offsetMapX() + toX(c01.X())+ MapGameState.TILESIZE*zoom(), -offsetMapY() + toY(c01.Y())+ MapGameState.TILESIZE*zoom(), 440, 419, 560, 539);
		}
			
		if (!container.isPaused()) {
			//Comportement bouton charger
			if (_bouton_charger.isPressed()) {
				container.setPaused(!pause);
			}
			
			//Comportement bouton sauvegarder
			if (_bouton_sauvegarder.isPressed()) {
				container.setPaused(!pause);
				saveMode = true;
				
			}
		    if (alpha > 0) {
		        alpha -= 0.01f;
		    }
		} else if (container.isPaused()) {
		    Rectangle rect = new Rectangle (0, 0, container.getScreenWidth(), container.getScreenHeight());
		    g.setColor(new Color (255, 255, 255, alpha));
		    g.fill(rect);
		    textInput.setFocus(true);
		    _bouton_confirmer.render(container, g);

		    if (alpha < 0.4f) {
		        alpha += 0.01f;
		    }
		    textInput.setBackgroundColor(Color.black);
		    textInput.render(container, g);
		    
		    if(_bouton_confirmer.isPressed() || _input.isKeyPressed(Input.KEY_ENTER)) {
		    	String mapPath = "src/asset/maps/";
		    	mapPath += textInput.getText();
				File f = new File(mapPath);
				if(saveMode) {
					try
					{
					    ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (f));
					    oos.writeObject (_mainw.map());
					    oos.close();
					}
					catch (IOException exception)
					{
					    System.out.println ("Erreur lors de l'écriture : " + exception.getMessage());
					}
					System.out.println("Sauvegarde OK !");
				} else {
					try
					{
					    ObjectInputStream ois = new ObjectInputStream (new FileInputStream (f));
					    _mainw.setMap((Carte)ois.readObject());
					    ois.close();
					}
					catch (ClassNotFoundException exception)
					{
					    System.out.println ("Impossible de lire l'objet : " + exception.getMessage());
					}
					catch (IOException exception)
					{
					    System.out.println ("Erreur lors de l'écriture : " + exception.getMessage());
					}
					System.out.println("Chargement OK !");
					map.initialise(_mainw);
					mapSizeX = _mainw.map().largeur() * MapGameState.TILESIZE;
					mapSizeY = _mainw.map().hauteur() * MapGameState.TILESIZE;
				}
				textInput.setText("");
				container.setPaused(false);
				saveMode = false;
		    }
		}
	}

	@SuppressWarnings("unchecked")
	public void setTrueTypeFont(String chemin, int fontSize) throws SlickException {
	    ttf = new UnicodeFont( chemin, fontSize, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();
	}

	@SuppressWarnings("unchecked")
	public void setFont(String nom, int fontSize) throws SlickException {     
		ttf = new UnicodeFont(new java.awt.Font(nom, java.awt.Font.PLAIN, fontSize));
		ttf.addAsciiGlyphs();
		ttf.addGlyphs(400,600);
		ttf.getEffects().add(new ColorEffect());
		ttf.loadGlyphs();
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		_bouton_confirmer.update(container);
		if (!container.isPaused()) {
			_bouton_Jouer.update(container);
			_bouton_sauvegarder.update(container);
			_bouton_charger.update(container);
			_bouton_placerAutomate.update(container);
			
			
			
			//Configuration bouton placer automate
			if (_bouton_placerAutomate.isDown()) {
				try {
					_mainw.putAutomates(World.joueurs.get(0).Automates(), 1, 1, World.joueurs.get(0));
					_mainw.putAutomates(World.joueurs.get(1).Automates(),_mainw.map().largeur()-2, _mainw.map().hauteur()-2, World.joueurs.get(1));
					map.initialise(_mainw);
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.initialise(_mainw);
			}
			
			//Configuration du bouton Jouer
			if (_bouton_Jouer.isDown()) {
					((MapGameState)InitGameState.game.getState(MapGameState.ID)).setGame(UIFs1, UIFs2, map);
					InitGameState.game.enterState(MapGameState.ID);
					}

			if (compt_clic %2 == 0 && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				if (compt_clic == 2){
					c02 = _mainw.Case(fromX(container.getInput().getAbsoluteMouseX()),fromY(container.getInput().getAbsoluteMouseY()));
					_mainw.switchCase(c01, c02);
					map.initialise(_mainw);
					compt_clic += 1;
				}
				else {
					int x1 = fromX(container.getInput().getAbsoluteMouseX());
					int y1 = fromY(container.getInput().getAbsoluteMouseY());
					System.out.print(x1+"\n");
					System.out.print(y1+"\n");
					c01 = _mainw.Case(x1, y1);
					compt_clic+=1;
				}
			}
			else if (compt_clic %2 == 1 && !container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				System.out.print("12\n");
				if (compt_clic ==3) {compt_clic = 0;
				c01=null;}
				else {compt_clic +=1;}
			}
		}
		
		//Gestion du scrolling de la map avec la souris/manette/clavier
		if (container.getInput().getAbsoluteMouseY() == container.getHeight() || _input.isControllerDown(0) || _input.isKeyDown(208)) {
			map.move(0, 1);
			setOffsetMapY(offsetMapY() + _scrollingSpeed);
		}
		if (container.getInput().getAbsoluteMouseX() == 0 || _input.isControllerLeft(0) || _input.isKeyDown(203)) {
			map.move(-1, 0);
			setOffsetMapX(offsetMapX() - _scrollingSpeed);
		}
		if (container.getInput().getAbsoluteMouseX() == container.getWidth() - 1 || _input.isControllerRight(0) || _input.isKeyDown(205)) {
			map.move(1, 0);
			setOffsetMapX(offsetMapX() + _scrollingSpeed);
		}
		if (container.getInput().getAbsoluteMouseY() == 1 || _input.isControllerUp(0) || _input.isKeyDown(200)) {
			map.move(0, -1);
			setOffsetMapY(offsetMapY() - _scrollingSpeed);
		}

				//Gestion du zoom
				//Zoom avant
				if (_input.isKeyDown(201))
				{
					map.setZoom(1.03f, _input.getMouseX(), _input.getMouseY());
					setZoom(zoom() * 1.03f);
					setOffsetMapX(offsetMapX() * 1.03f + _input.getMouseX() * 0.03f);
					setOffsetMapY(offsetMapY() * 1.03f + _input.getMouseY() * 0.03f);
				}

				//Zoom arrière
				if (mapSizeX * MapGameState.TILESIZE * map.zoom() > container.getWidth() && mapSizeY * MapGameState.TILESIZE * map.zoom() > container.getHeight()) {
					if (_input.isKeyDown(209) && map.zoom() > 0) {
						map.setZoom(1/1.03f, _input.getAbsoluteMouseX(), _input.getAbsoluteMouseY());
						setZoom(zoom() / 1.03f);
						setOffsetMapX(offsetMapX() / 1.03f + _input.getMouseX() * (-1f+1/1.03f));
						setOffsetMapY(offsetMapY() / 1.03f + _input.getMouseY() * (-1f+1/1.03f));
					}
				}
				
				
	}
	
	/**
	 * Notification que la roulette de la souris a été bougée (la méthode est appelée à ce moment là).
	 * @param n Sens du mouvement de la roulette.
	 */
	public void mouseWheelMoved(int n) {
		if (n > 0) {
			map.setZoom(1.03f, _input.getMouseX(), _input.getMouseY());



		} else if (n < 0) {
			//Zoom arrière
			if (_tailleMapX * MapGameState.TILESIZE * map.zoom() > map.getWidth() && _tailleMapX * MapGameState.TILESIZE * map.zoom() > map.getHeight()) {
				map.setZoom(1/1.03f, _input.getAbsoluteMouseX(), _input.getAbsoluteMouseY());
			}
		}
	}
	
	/**
	 * Notification que l'on entre dans cette boucle de jeu.
	 * @param container Le contexte dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 *
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		mapSizeX = _tailleMapX * MapGameState.TILESIZE;
		mapSizeY 
	}*/
	
	public int fromX(float x)
	{
		return (int) ((int) (x + _offsetMapX) / (MapGameState.TILESIZE * zoom()));
	}
	public int fromY(float y)
	{
		return (int) ((int) (y + _offsetMapY) / (MapGameState.TILESIZE * zoom()));
	}
	
	public void keyPressed(int key, char c) {
	}
	
	public float toX(int x)
	{
		return x * MapGameState.TILESIZE * zoom();
	}
	public float toY(int y)
	{
		return y * MapGameState.TILESIZE * zoom();
	}

	@Override
	public int getID() {
		return ID;
	}

	
	public float zoom() {
		return _zoom;
	}
	
	public void setZoom(float _zoom) {
		this._zoom = _zoom;
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
	
	@SuppressWarnings("static-access")
	public void setGame(ArrayList<UnitInfo> uIFs1,ArrayList<UnitInfo> uIFs2) {
		World.Univers.add(new World(_tailleMapY,_tailleMapX, false));
		this.UIFs1 = uIFs1;
		this.UIFs2 = uIFs2;
		
		_mainw = World.Univers.get(0);
		map.initialise(_mainw);

		ArrayList<ArrayList<Automate>> autlist = new ArrayList<ArrayList<Automate>>();
		ArrayList<ArrayList<Automate>> autclist = new ArrayList<ArrayList<Automate>>();
		ArrayList<ArrayList<Classe>> classes = new ArrayList<ArrayList<Classe>>();
		ArrayList<ArrayList<TypeUnit>> type_unit = new ArrayList<ArrayList<TypeUnit>>();
		ArrayList<ArrayList<TypeClothes>> type_clothes = new ArrayList<ArrayList<TypeClothes>>();

		autlist.add(new ArrayList<Automate>());
		autclist.add(new ArrayList<Automate>());
		classes.add(new ArrayList<Classe>());
		type_unit.add(new ArrayList<TypeUnit>());
		type_clothes.add(new ArrayList<TypeClothes>());
		
		for(UnitInfo ui : uIFs1)
		{
			autlist.get(0).add(ui.automate);
			autclist.get(0).add(ui.automatec);
			classes.get(0).add(ui.classe);
			type_unit.get(0).add(ui.color);
			type_clothes.get(0).add(ui.clothes);
		}

		autlist.add(new ArrayList<Automate>());
		autclist.add(new ArrayList<Automate>());
		classes.add(new ArrayList<Classe>());
		type_unit.add(new ArrayList<TypeUnit>());
		type_clothes.add(new ArrayList<TypeClothes>());
		
		for(UnitInfo ui : uIFs2) {
			autlist.get(1).add(ui.automate);
			autclist.get(1).add(ui.automatec);
			classes.get(1).add(ui.classe);
			type_unit.get(1).add(ui.color);
			type_clothes.get(1).add(ui.clothes);
		}
		if (World.joueurs.size() == 0) {
			World.joueurs.add(new Joueur("Joueur 1", autlist.get(0), autclist.get(0), classes.get(0), type_unit.get(0), type_clothes.get(0)));
			World.joueurs.add(new Joueur("Joueur 2", autlist.get(1), autclist.get(1), classes.get(1), type_unit.get(1), type_clothes.get(1)));
		}
		try {
			this.map.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	
	
	

}