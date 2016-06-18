package graphique;

import java.awt.Dimension;
import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import workshop.WorkshopCreator;

public class StateGame extends StateBasedGame {

	static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private static int height = (int)screenSize.getHeight();
	private static int width = (int)screenSize.getWidth();
	public static WorkshopCreator workshop ;

	public StateGame() {
		super("Chateautomate");
		workshop = new WorkshopCreator();
	}

    public void initStatesList(GameContainer container) throws SlickException {
    	addState(new MainScreenGameState());
    	addState(new MapGameState());
      	addState(new DragAndDropState());
      	addState(new InitGameState());

    }

	public void enterState(int id, String s) throws SlickException {
		super.enterState(id);
		Music music = new Music(s);
	    music.loop();
	}

	public void keyPressed(int key, char c) {
		/*if (Input.KEY_ESCAPE == key) {
				getContainer().exit();
		}*/
	}

    public static void main(String[] args) throws SlickException {
        AppGameContainer game = new AppGameContainer(new StateGame(), 1200, 700, false);
        game.setTargetFrameRate(60);
        game.setShowFPS(false);
        game.start();
    }
}
