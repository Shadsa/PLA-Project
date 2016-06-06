package graphique;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends StateBasedGame {

	private static int width = 800;
	private static int height = 600;
	
	public StateGame() {
		super("StateGame");
	}

    public void initStatesList(GameContainer container) throws SlickException {
    	addState(new MainScreenGameState());
    	addState(new MapGameState());
    }

	public void enterState(int id, String s) throws SlickException {
		super.enterState(id);
		Music music = new Music(s);
	    music.loop();
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
    public static void main(String[] args) throws SlickException {
    	
        AppGameContainer game = new AppGameContainer(new StateGame(), width, height, false);
        game.setTargetFrameRate(60);
        game.setShowFPS(true);
        game.start();
    }
}
