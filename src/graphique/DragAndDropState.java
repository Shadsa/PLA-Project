package graphique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import roles.action.World;

public class DragAndDropState extends BasicGameState {
	private Bouton _bouton_drag;
	public static final int ID = 2;
	private MapTest map = new MapTest();
	private float _offsetMapX=0;
	private float _offsetMapY=0;
	private float zoom;
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		World.BuildMap(10,10);
		map.init();
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		this.map.render(g, _offsetMapX, _offsetMapY, zoom);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return ID;
	}
}
