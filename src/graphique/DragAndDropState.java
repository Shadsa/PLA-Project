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
import cases.TypeCase;
import roles.World;
import roles.classe.Classe;
import graphique.Button;
import graphique.InitGameState;
import graphique.MapTest;
import graphique.MapGameState;

class DragAndDropState extends BasicGameState {
	public static final int ID = 3;
	private MapTest map = new MapTest ();
	private float _offsetMapX=0;
	private float _offsetMapY=0;
	private float mouseAbsoluteX;
	private float mouseAbsoluteY;
	private float _mouseMapX;
	private float _mouseMapY;
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
	
	//private static StateGame game;

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		_input = container.getInput();
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
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
			this.map.render(g, _offsetMapX, _offsetMapY, zoom(), container.getWidth(), container.getHeight());
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
			    
			    if(_bouton_confirmer.isPressed()) {
			    	String mapPath = "src/asset/maps/";
			    	mapPath += textInput.getText();
					File f = new File(mapPath);
					if(saveMode) {
						try
						{
						    ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (f));
						    oos.writeObject (World.map());
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
						    World.setMap((Carte)ois.readObject());
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
						map.init();
					}
					textInput.setText("");
					container.setPaused(false);
					saveMode = false;
			    }
			}
	}

	public void setTrueTypeFont(String chemin, int fontSize) throws SlickException {
	    ttf = new UnicodeFont( chemin, fontSize, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();
	}

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
					World.putAutomates(World.getPlayers().get(0).Automates(), 1, 1, World.getPlayers().get(0));
					World.putAutomates(World.getPlayers().get(1).Automates(),World.map().largeur()-2, World.map().hauteur()-2, World.getPlayers().get(1));
					map.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.init();
			}
			
			//Configuration du bouton Jouer
			if (_bouton_Jouer.isDown()) {
					((MapGameState)InitGameState.game.getState(MapGameState.ID)).setGame(UIFs1, UIFs2, map);
					InitGameState.game.enterState(MapGameState.ID);
					}
			//Gestion des boutons en plein écran
			//_bouton_Jouer.setLocation(arg0.getWidth()/2-150, arg0.getHeight()/2-50);
			
			
			TypeCase t1 = null;
			
			if (compt_clic %2 == 0 && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				System.out.print("11\n");
				if (compt_clic == 2){
					System.out.print("21\n");
					t1 = c01.type();
					c02 = World.Case(fromX(container.getInput().getAbsoluteMouseX()),fromY(container.getInput().getAbsoluteMouseY()));
					//c01.modifierCase(c02.type());
					//c02.modifierCase(t1);
					World.switchCase(c01, c02);
					map.init();
					compt_clic += 1;
				}
				else {
					System.out.print("22\n");
					int x1 = fromX(container.getInput().getAbsoluteMouseX());
					int y1 = fromY(container.getInput().getAbsoluteMouseY());
					System.out.print(x1+"\n");
					System.out.print(y1+"\n");
					c01 = World.Case(x1, y1);
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
				if (mouseAbsoluteY == container.getHeight() || _input.isControllerDown(0) || _input.isKeyDown(208)) {
					setOffsetMapY(offsetMapY() + _scrollingSpeed);
				}
				if (/*mouseAbsoluteX == 0 || _input.isControllerLeft(0) ||*/ _input.isKeyDown(203)) {
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
				if (_input.isKeyDown(209) && zoom() > 0) {
						setZoom(zoom() / 1.03f);
						if (zoom() < 0) {
							setZoom(0);
						}
						setOffsetMapX(_mouseMapX*zoom() - mouseAbsoluteX);
						setOffsetMapY(_mouseMapY*zoom() - mouseAbsoluteY);
					}
				
				
	}
	
	public int fromX(float x)
	{
		return (int) ((int) (x - _offsetMapX) / (MapGameState.TILESIZE*zoom()));
	}
	public int fromY(float y)
	{
		return (int) ((int) (y - _offsetMapY) / (MapGameState.TILESIZE*zoom()));
	}
	
	public void keyPressed(int key, char c) {
	}
	
	public float toX(int x)
	{
		return x * MapGameState.TILESIZE*zoom();
	}
	public float toY(int y)
	{
		return y * MapGameState.TILESIZE*zoom();
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
	
	public void setGame(ArrayList<UnitInfo> uIFs1,ArrayList<UnitInfo> uIFs2) {

		this.UIFs1 = uIFs1;
		this.UIFs2 = uIFs2;



		ArrayList<ArrayList<Automate>> autlist = new ArrayList<ArrayList<Automate>>();
		ArrayList<ArrayList<Classe>> classes = new ArrayList<ArrayList<Classe>>();
		ArrayList<ArrayList<TypeUnit>> type_unit = new ArrayList<ArrayList<TypeUnit>>();
		ArrayList<ArrayList<TypeClothes>> type_clothes = new ArrayList<ArrayList<TypeClothes>>();

		for(UnitInfo ui : uIFs1)
		{
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
		if (World.getPlayers().size() == 0) {
			World.addPlayer(new Joueur("Joueur1", autlist.get(0), classes.get(0)));
			World.addPlayer(new Joueur("Joueur2", autlist.get(1), classes.get(1)));
		}
		try {
			this.map.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	
	
	

}