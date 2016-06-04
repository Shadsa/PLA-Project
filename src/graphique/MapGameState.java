package graphique;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import roles.Automate;
import roles.Cardinaux;
import roles.Personnage;
import roles.action.Avancer;
import roles.action.Joueur;
import roles.action.World;
import roles.conditions.Libre;

public class MapGameState extends BasicGameState {

	private GameContainer container;
	private Map map = new Map();
	private Player player = new Player();
	private Personnage personnage;
	private Hud hud = new Hud();
	public static final int ID = 2;

	/**
	 * Initialise le contenu du jeu, charge les animations
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Automate aut1 = new Automate(2);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.NORD, 1), new Libre(Cardinaux.NORD), 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.EST, 1), new Libre(Cardinaux.EST), 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.SUD, 0), new Libre(Cardinaux.SUD), 1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.OUEST, 0), new Libre(Cardinaux.OUEST), 1);

		aut1.ajoute_transition(1, new Avancer(Cardinaux.NORD, 0), new Libre(Cardinaux.NORD), 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.EST, 0), new Libre(Cardinaux.EST), 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.SUD, 1), new Libre(Cardinaux.SUD), 1);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.OUEST, 1), new Libre(Cardinaux.OUEST), 1);
		ArrayList<Automate> autlist = new ArrayList<Automate>();
		autlist.add(aut1);
		Joueur j = new Joueur("Moi", autlist);
		World.addPlayer(j);
		World.BuildMap();
		j.createPersonnage(0, 5, 5);
		personnage = j.getPersonnages().get(0);



		this.container = container;
		this.map.init();
		this.player.init();
		this.player.setX(100+300);
		this.player.setDestX(100+300);
		this.player.setY(100+300);
		this.player.setDestY(100+300);
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

	protected long _time = 0;

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.player.update(delta);
		_time += delta;
		if(_time > 1000)
		{
			_time -= 1000;
			World.nextTurn();
			player.setDestX(personnage.X()*20+300);
			player.setDestY(personnage.Y()*20+300);
		}
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
