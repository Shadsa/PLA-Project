package graphique;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class MainScreenGameState extends BasicGameState {

	public static final int ID = 1;
	private Image background;
	private StateGame game;
	private TrueTypeFont font;

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = (StateGame) game;
		this.background = new Image("src/asset/images/skeleton_army.jpg");
		Music music = new Music("src/asset/musics/menu_music.ogg");
	    music.loop();
	    
		/*// Chargement d'une nouvelle police de caractères
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("src/asset/fonts/Friedolin.ttf");
	 
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(50f); // set font size
			font = new TrueTypeFont(awtFont, false);
	 
		} catch (Exception e) {
			e.printStackTrace();
		}*/	
	}

	/**
	 * Contenons nous d'afficher l'image de fond. 
	 * Le text est placé approximativement au centre.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.drawString("Appuyez sur une touche", 300, 300);
	}

	/**
	 * Passer à l’écran de jeu à l'appui de n'importe quel touche.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	}

	public void keyReleased(int key, char c) {
		try {
			game.enterState(MapGameState.ID, "src/asset/musics/game_music.ogg");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * L'identifiant permet d'identifier les différentes boucles.
	 * Pour passer de l'une à l'autre.
	 */
	public int getID() {
		return ID;
	}
}
