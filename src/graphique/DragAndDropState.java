package graphique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import cases.Case;
import cases.TypeCase;
import roles.Carte;
import roles.action.World;

public class DragAndDropState extends BasicGameState {
	private Bouton _bouton_drag;
	public static final int ID = 3;
	private MapTest map = new MapTest();
	private float _offsetMapX=0;
	private float _offsetMapY=0;
	private float zoom = 1;
	private StateBasedGame game;
	private Input _input;
	int compt_clic; //compteur de clic
	int x; //variable de sauvegarde des coordonnées
	int y;

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		//World.BuildMap(40,57);
		//map.init();	
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		//this.map.render(g, _offsetMapX, _offsetMapY, zoom);
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
		int arg3;int arg4;int arg5;int arg6;
		if (arg0.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			int x1 = arg0.getInput().getMouseX();
			int y2 = arg0.getInput().getMouseY();
		}
		
		int x1 = arg3;int y1 = arg4;
		Case c01 = World.Case(x1,y1);
		//c01.type(); permet d'obtenir le type de la case 
		mousePressed(Input.MOUSE_LEFT_BUTTON, arg5, arg6);
		int x2 = arg5;int y2 = arg6;
		Case c02 = World.Case(arg5,arg6);
		TypeCase t1 = c01.type();
		c01.modifierCase(c02.type());
		c02.modifierCase(t1);		
	}
	
	public void keyPressed(int key, char c) {
	}

	@Override
	public int getID() {
		return ID;
	}
}