package graphique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		//World.BuildMap(40,57);
		//map.init();	
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		//this.map.render(g, _offsetMapX, _offsetMapY, zoom);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (_input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(MainScreenGameState.ID);
		}
		
	}
	
	public void keyPressed(int key, char c) {
	}

	@Override
	public int getID() {
		return ID;
	}
}
