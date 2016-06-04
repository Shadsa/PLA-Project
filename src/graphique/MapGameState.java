package graphique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapGameState extends BasicGameState {
	
	private GameContainer container;
	private Map map = new Map();
	private Player player = new Player();
	private Hud hud = new Hud();
	public static final int ID = 2;

	/**
	 * Initialise le contenu du jeu, charge les animations
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.map.init();
		this.player.init();
		PlayerController controller = new PlayerController(player);
		container.getInput().addKeyListener(controller);
		this.hud.init();
	}
	
	/**
	 * Affichage des différents éléments du jeu
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		this.map.render();
		this.player.render(g);
		this.hud.render(g);
	}

	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.player.update(delta);
	}
 
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
		container.exit();
	}
	}
 
	public int getID() {
		return ID;
	}
}
