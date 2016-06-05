package graphique;

import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapGameState extends BasicGameState {
	
	private GameContainer container;
	//private Map map = new Map();
	private Player player = new Player();
	private Hud hud = new Hud();
	public static final int ID = 2;
	private String playerData;
	private String mouse;
	private int mouseX;
	private int mouseY;
	private boolean showhud = false;
	
	//Test
	private MapTest map = new MapTest();
	

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
		this.map.render(g);
		this.player.render(g);
		g.resetTransform(); 
		g.scale(1, 1);
		if (showhud) {
			this.hud.render(g);
		}
		g.setColor(Color.white);
		g.drawString(playerData, 10, 30);
		g.drawString(mouse, 10, 50);
		g.drawString("Appuie sur le personnage pour afficher ses données !" , 10, 70);
		
	}

	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.player.update(delta);
		playerData = "Coord X :" + this.player.getX() + ", Coord Y : " + this.player.getY() + ", action_finie : " + this.player.getAction_finie();
		Input input = container.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		mouse = "MouseX : " + mouseX + ", MouseY : " + mouseY;
		
	}
 
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
		container.exit();
		}
	}
	
	public void mousePressed(int arg0, int arg1, int arg2) {
		if (Input.MOUSE_LEFT_BUTTON == arg0 && mouseX >= this.player.getX()-32 && mouseX <= this.player.getX()+32 && mouseY >= this.player.getY()-60 && mouseY <= this.player.getY()+4) {
			this.showhud = true;
		}
		
	}
	
	public void mouseReleased(int arg0, int arg1, int arg2) {
		if (Input.MOUSE_LEFT_BUTTON == arg0) {
			this.showhud = false;
		}
		
	}
 
	public int getID() {
		return ID;
	}
}
