package graphique;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends StateBasedGame {

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
	
    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new StateGame(), 800, 600, false).start();
    }
}
