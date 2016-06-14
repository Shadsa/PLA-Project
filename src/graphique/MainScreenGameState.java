package graphique;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.AppGameContainer;
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
import org.newdawn.slick.gui.MouseOverArea;
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

	//Musique
	private Music music;

	//Bouton
	//private Bouton _bouton_jouer;
	private Button _bouton_jouer;
	private Button _bouton_quitter;
	private Button _bouton_fullScreen;
	private Button _bouton_son;

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		_input = container.getInput();
		this.game = (StateGame) game;
		this.background = new Image("src/asset/images/skeleton_army.jpg");
		
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
		_bouton_jouer = new Button(container, "Jouer", container.getWidth()/2-62, container.getHeight()/2-80, normalImage, overImage, downImage);
		_bouton_fullScreen = new Button(container, "Plein écran", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_son = new Button(container, "Désactiver son", container.getWidth()/2-62, container.getHeight()/2-40, normalImage, overImage, downImage);
		_bouton_quitter = new Button(container, "Quitter", container.getWidth()/2-62, container.getHeight()/2+40, normalImage, overImage, downImage);
		//music = new Music("src/asset/musics/menu_music.ogg");
	   // music.loop();
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
		//_bouton_jouer.render(container, g);
		_bouton_jouer.render(container, g);
		_bouton_fullScreen.render(container, g);
		_bouton_quitter.render(container, g);
		_bouton_son.render(container, g);
		g.setColor(Color.white);
		/*if (_input.getControllerCount() >= 1) {
			g.drawString("Appuyez sur START pour commencer.", 240, 300);
		} else {*/
		//}
	}

	/**
	 * Passer à l’écran de jeu à l'appui de n'importe quelle touche.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {	
		_bouton_jouer.update(container);
		_bouton_fullScreen.update(container);
		_bouton_quitter.update(container);
		_bouton_son.update(container);

		//Configuration du bouton jouer
		if (_bouton_jouer.isDown()) {
				game.enterState(InitGameState.ID);
				//this.game.enterState(MapGameState.ID, "src/asset/musics/game_music.ogg");
		}

		//Configuration du bouton quitter
		if (_bouton_quitter.isPressed()) {
			container.exit();
		}

		//Configuration du bouton plein écran
		if (_bouton_fullScreen.isPressed()) {
			_input.clearMousePressedRecord();
			if (container.isFullscreen()) {
				_bouton_fullScreen.setText("Plein écran");
				((AppGameContainer) container).setDisplayMode(800,600, false);
			} else {
				_bouton_fullScreen.setText("Fenêtré");
				((AppGameContainer) container).setDisplayMode(container.getScreenWidth(),container.getScreenHeight(), true);
			}
		}

		//Configuration du bouton son
		if (_bouton_son.isPressed()) {
			if (container.getMusicVolume() > 0) {
				container.setMusicVolume(0);
				container.setSoundVolume(0);
				_bouton_son.setText("Activer son");
			} else {
				container.setMusicVolume(100);
				container.setSoundVolume(100);
				_bouton_son.setText("Désactiver son");
			}
		}


		/*if (_input.isKeyPressed(Input.KEY_A)){
			game.enterState(DragAndDropState.ID);
		}*/

		//Gestion des boutons en plein écran
		_bouton_jouer.setLocation(container.getWidth()/2-62, container.getHeight()/2-80);
		_bouton_fullScreen.setLocation(container.getWidth()/2-62, container.getHeight()/2);
		_bouton_son.setLocation(container.getWidth()/2-62, container.getHeight()/2-40);
		_bouton_quitter.setLocation(container.getWidth()/2-62, container.getHeight()/2+40);
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		_bouton_son.setText(container.getMusicVolume() > 0 ? "Désactiver son" : "activer son");
		_bouton_fullScreen.setText(container.isFullscreen() ? "Fenêtré" : "Plein écran");

	}

	@Override
	public void keyPressed(int key, char c) {
	}

	/**
	 * L'identifiant permet d'identifier les différentes boucles.
	 * Pour passer de l'une à l'autre.
	 */
	public int getID() {
		return ID;
	}
}
