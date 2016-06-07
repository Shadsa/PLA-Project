package graphique;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class MainScreenGameState extends BasicGameState {
	
	//Identifiant unique de la boucle de jeu
	public static final int ID = 1;
	//Image de fond
	private Image background;
	//Le contrôleur des phases de jeu
	private static StateGame game;
	private TrueTypeFont font;
	private int size;
	private String sizeScreen;
	private GameContainer container;
	private Input _input;


	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		container.setFullscreen(true);
		_input = container.getInput();
		this.game = (StateGame) game;
		this.background = new Image("src/asset/images/skeleton_army.jpg");
		Music music = new Music("src/asset/musics/menu_music.ogg");
	    music.loop();
	    sizeScreen = "Taille de l'écran : " + container.getScreenWidth() + "x" + container.getScreenHeight();
	    
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
	 * Le texte est placé approximativement au centre.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);
		/*if (_input.getControllerCount() >= 1) {
			g.drawString("Appuyez sur START pour commencer.", 240, 300);
		} else {*/
			g.drawString("Appuyez sur une touche pour commencer", 240, 300);
		//}
	}

	/**
	 * Passer à l’écran de jeu à l'appui de n'importe quelle touche.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {	
	}

	public void keyPressed(int key, char c) {
		
	}
	
	public void keyReleased(int key, char c) {
		try {
			game.enterState(MapGameState.ID, "src/asset/musics/game_music.ogg");
		} catch (SlickException e) {
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
