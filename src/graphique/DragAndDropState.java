package graphique;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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

public class DragAndDropState extends BasicGameState {
	private Bouton _bouton_drag;
	public static final int ID = 3;
	private MapTest map = new MapTest();
	private float _offsetMapX=0;
	private float _offsetMapY=0;
	private float mouseAbsoluteX;
	private float mouseAbsoluteY;
	private float _mouseMapX;
	private float _mouseMapY;
	private int _scrollingSpeed = 15;
	private float _zoom = 1;
	private StateBasedGame game;
	private Input _input;
	int compt_clic = 0; //compteur de clic
	int x1; //variable de sauvegarde des coordonnées
	int y1;
	Case c01;
	Case c02;
	private ArrayList<GJoueur> _joueurs;
	private int _tailleMapX=45;
	private int _tailleMapY=75;

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		//World.BuildMap(40,57);
		//map.init();	
		_input = arg0.getInput();
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
			if (c01 != null) 
				g.drawImage(Hud.playerBars, toX(c01.X()), toY(c01.Y()),toX(c01.X())+ MapGameState.TILESIZE*zoom(), toY(c01.Y())+ MapGameState.TILESIZE*zoom(), 732, 228, 756, 252);
	}



	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (_input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(MainScreenGameState.ID);
		}
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
		/*for(Joueur j : World.getPlayers())
		{
			_joueurs.add(new graphique.GJoueur((j == World.getPlayers().get(0))?TypeUnit.Human:TypeUnit.Zombie));
			j.addObserver(_joueurs.get(_joueurs.size()-1));
			for(Personnage pers : j.getPersonnages())
				_joueurs.get(_joueurs.size()-1).addPersonnage(pers);
		}*/
		try {
			this.map.init();
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
}