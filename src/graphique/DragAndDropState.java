package graphique;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import cases.Case;
import roles.Automate;
import roles.Carte;
import roles.Joueur;
import roles.Personnage;
import cases.TypeCase;
import roles.World;
import roles.classe.Classe;
import graphique.Button;
import graphique.StateGame;
import graphique.InitGameState;
import graphique.MapTest;
import graphique.MapGameState;

class DragAndDropState extends BasicGameState {
	private Bouton _bouton_drag;
	public static final int ID = 3;
	private MapTest map = new MapTest ();
	private float _offsetMapX=0;
	private float _offsetMapY=0;
	private float mouseAbsoluteX;
	private float mouseAbsoluteY;
	private float _mouseMapX;
	private float _mouseMapY;
	private ArrayList<UnitInfo> UIFs1;
	private ArrayList<UnitInfo> UIFs2;
	private int _scrollingSpeed = 15;
	private float _zoom = 1;
	//private static StateBasedGame game;
	private Input _input;
	int compt_clic = 0; //compteur de clic
	int x1; //variable de sauvegarde des coordonnées
	int y1;
	Case c01;
	Case c02;
	private ArrayList<GJoueur> _joueurs;
	private int _tailleMapX=75;
	private int _tailleMapY=45;
	private Button _bouton_Jouer;
	
	//private static StateGame game;

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		//World.BuildMap(40,57);
		//map.init();	
		_input = arg0.getInput();
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
		_bouton_Jouer = new Button(arg0, "Jouer", arg0.getWidth()-150, arg0.getHeight()-50, normalImage, overImage, downImage);
		//UIFs = new ArrayList<UnitInfo>();	
		//DragAndDropState.game = (StateGame) game;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
				//Affichage de la map
				this.map.render(g, _offsetMapX, _offsetMapY, zoom(), arg0.getWidth(), arg0.getHeight());
				//Annule la translation pour l'affichage du string en dessous
				g.resetTransform();
				
				//Affichage message de fin
				if (World.fini) {
					if(World.joueurs().size()==1){
						g.drawString(World.joueurs().get(0).nom()+" a gagné! Félicitations à lui, vraiment.", arg0.getWidth()/2-175, arg0.getHeight()/2);
						g.resetTransform();
					}}
			_bouton_Jouer.render(arg0, g);
			if (c01 != null) 
				g.drawImage(Hud.playerBars, toX(c01.X()), toY(c01.Y()),toX(c01.X())+ MapGameState.TILESIZE*zoom(), toY(c01.Y())+ MapGameState.TILESIZE*zoom(), 732, 228, 756, 252);
	}



	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		/*if (_input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(MainScreenGameState.ID);
		}*/
		_bouton_Jouer.update(arg0);
		//Configuration du bouton Jouer
		if (_bouton_Jouer.isDown()) {
				//game.enterState(MapGameState.ID);
				((MapGameState)InitGameState.game.getState(MapGameState.ID)).setGame(UIFs1, UIFs2, map);
				InitGameState.game.enterState(MapGameState.ID);
				}
		//Gestion des boutons en plein écran
		//_bouton_Jouer.setLocation(arg0.getWidth()/2-150, arg0.getHeight()/2-50);
		
		
		TypeCase t1 = null;
		
		if (compt_clic %2 == 0 && arg0.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			System.out.print("11\n");
			if (compt_clic == 2){
				System.out.print("21\n");
				t1 = c01.type();
				c02 = World.Case(fromX(arg0.getInput().getAbsoluteMouseX()),fromY(arg0.getInput().getAbsoluteMouseY()));
				c01.modifierCase(c02.type());
				c02.modifierCase(t1);
				compt_clic += 1;
			}
			else {
				System.out.print("22\n");
				int x1 = fromX(arg0.getInput().getAbsoluteMouseX());
				int y1 = fromY(arg0.getInput().getAbsoluteMouseY());
				System.out.print(x1+"\n");
				System.out.print(y1+"\n");
				c01 = World.Case(x1, y1);
				compt_clic+=1;
			}
		}
		else if (compt_clic %2 == 1 && !arg0.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			System.out.print("12\n");
			if (compt_clic ==3) {compt_clic = 0;
			c01=null;}
			else {compt_clic +=1;}
		}
		
		//Gestion du scrolling de la map avec la souris/manette/clavier
				if (mouseAbsoluteY == arg0.getHeight() || _input.isControllerDown(0) || _input.isKeyDown(208)) {
					setOffsetMapY(offsetMapY() + _scrollingSpeed);
				}
				if (/*mouseAbsoluteX == 0 || _input.isControllerLeft(0) ||*/ _input.isKeyDown(203)) {
					setOffsetMapX(offsetMapX() - _scrollingSpeed);
				}
				if (mouseAbsoluteX == arg0.getWidth() - 1 || _input.isControllerRight(0) || _input.isKeyDown(205)) {
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
		/*//for(int i = 0; i < nb; i++)
		World.getPlayers().get(0).createPersonnage(0, 1, 1);
		//for(int i = 0; i < nb; i++)
		World.getPlayers().get(1).createPersonnage(classes.size()-1, _tailleMapX-1, _tailleMapY-1);
*/
		/*int i=0;
		for(Joueur j : World.getPlayers())
		{
			_joueurs.add(new GJoueur(type_unit.get(i),type_clothes.get(i)));
			j.addObserver(_joueurs.get(_joueurs.size()-1));
			for(Personnage pers : j.getPersonnages())
				_joueurs.get(_joueurs.size()-1).addPersonnage(pers);
			i++;
		}*/
		try {
			this.map.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	
	
	

}