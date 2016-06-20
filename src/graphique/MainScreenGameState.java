package graphique;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainScreenGameState extends BasicGameState {

	//Identifiant unique de la boucle de jeu
	public static final int ID = 1;
	//Image de fond
	private Image background;
	//Ensemble des entrées clavier/souris/manette
	private Input _input;

	//Musique
	private Music music;

	//Bouton
	private Button _bouton_jouer;
	private Button _bouton_quitter;
	private Button _bouton_fullScreen;
	private Button _bouton_son;
	private Button _bouton_options;
	private UnicodeFont ttf;

	/**
	 * Initialise la boucle de jeu. Cette méthode est appelée avant que la boucle démarre.
	 * @param container Le conteneur du jeu dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		_input = container.getInput();
		this.background = new Image("src/asset/images/gilead_entry_area_by_rusty001-d2y351t.jpg");
		
		Image img = new Image("src/asset/sprites/ui_big_pieces.png");
		Image normalImage = img.getSubImage(633, 23, 123, 27);
		Image overImage = img.getSubImage(633, 53, 123, 27);
		Image downImage = img.getSubImage(633, 83, 123, 27);
		_bouton_jouer = new Button(container, "Jouer", container.getWidth()/2-62, container.getHeight()/2-40, normalImage, overImage, downImage);
		//_bouton_fullScreen = new Button(container, "Plein écran", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_son = new Button(container, "Désactiver son", container.getWidth()/2-62, container.getHeight()/2, normalImage, overImage, downImage);
		_bouton_quitter = new Button(container, "Quitter", container.getWidth()/2-62, container.getHeight()/2+80, normalImage, overImage, downImage);
		_bouton_options = new Button(container, "Options", container.getWidth()/2-62, container.getHeight()/2+40, normalImage, overImage, downImage);
		music = new Music("src/asset/musics/menu_music.ogg");
	    music.loop();


	}

	/**
	 * Affichage des éléments du jeu dans le conteneur.
	 * @param container Le conteneur du jeu dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 * @param g Le contexte graphique qui peut être utilisé pour afficher les éléments.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		background.draw(0, 0, container.getWidth(), container.getHeight());
		_bouton_jouer.render(container, g);
		//_bouton_fullScreen.render(container, g);
		_bouton_quitter.render(container, g);
		_bouton_son.render(container, g);
		_bouton_options.render(container, g);
		g.setColor(Color.white);
		
		setTrueTypeFont("./src/asset/fonts/teutonic4.ttf", 80);
		ttf.drawString(container.getWidth()/2 - 337, container.getHeight()/7 - 2, "Chateautomate", Color.white);
		ttf.drawString(container.getWidth()/2 - 336, container.getHeight()/7 - 1, "Chateautomate", Color.red);
		ttf.drawString(container.getWidth()/2 - 335, container.getHeight()/7, "Chateautomate", Color.black);
	}

	/**
	 * Mise à jour des attributs et des éléments du conteneur.
	 * @param container Le conteneur du jeu dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 * @param delta Le temps mis entre chaque mise à jour en millisecondes.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {	
		_bouton_jouer.update(container);
		//_bouton_fullScreen.update(container);
		_bouton_quitter.update(container);
		_bouton_son.update(container);
		_bouton_options.update(container);

		if(_bouton_options.isPressed())
		{
			ClassDialog classDialog = new ClassDialog(null, "Création d'une classe", true);
			classDialog.setVisible(true);
		}
		//Configuration du bouton jouer
		if (_bouton_jouer.isDown()) {
				game.enterState(InitGameState.ID);
		}

		//Configuration du bouton quitter
		if (_bouton_quitter.isPressed()) {
			container.exit();
		}

		/*//Configuration du bouton plein écran
		if (_bouton_fullScreen.isPressed()) {
			_input.clearMousePressedRecord();
			if (container.isFullscreen()) {
				_bouton_fullScreen.setText("Plein écran");
				((AppGameContainer) container).setDisplayMode(800,600, false);
			} else {
				_bouton_fullScreen.setText("Fenêtré");
				((AppGameContainer) container).setDisplayMode(container.getScreenWidth(),container.getScreenHeight(), true);
			}
		}*/

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
		_bouton_jouer.setLocation(container.getWidth()/2-62, container.getHeight()/2-40);
		//_bouton_fullScreen.setLocation(container.getWidth()/2-62, container.getHeight()/2);
		_bouton_son.setLocation(container.getWidth()/2-62, container.getHeight()/2);
		_bouton_quitter.setLocation(container.getWidth()/2-62, container.getHeight()/2+80);
		_bouton_options.setLocation(container.getWidth()/2-62, container.getHeight()/2+40);
	}
	
	@SuppressWarnings("unchecked")
	public void setTrueTypeFont(String chemin, int fontSize) throws SlickException {
	    ttf = new UnicodeFont( chemin, fontSize, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();
	}
	
	/**
	 * Notification que l'on entre dans cette boucle de jeu.
	 * @param container Le contexte dans lequels les composants sont crées et affichés.
	 * @param game Le contrôleur des différentes boucles de jeu.
	 */
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		((AppGameContainer) container).setDisplayMode(1200,700, false);
		_bouton_son.setText(container.getMusicVolume() > 0 ? "Désactiver son" : "activer son");
		//_bouton_fullScreen.setText(container.isFullscreen() ? "Fenêtré" : "Plein écran");
	}

	/**
	 * Récupère l'identifiant unique de la boucle de jeu.
	 * return L'identifiant de la boucle.
	 */
	public int getID() {
		return ID;
	}
}
