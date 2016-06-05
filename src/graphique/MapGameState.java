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
import roles.action.Attaquer;
import roles.action.Avancer;
import roles.action.Joueur;
import roles.action.World;
import roles.conditions.Ennemy;
import roles.conditions.Libre;

public class MapGameState extends BasicGameState {

	private GameContainer container;
	private Map map = new Map();
	private ArrayList<Player> _players = new ArrayList<Player>();
	//private Personnage personnage;
	private Hud hud = new Hud();
	public static final int ID = 2;

	/**
	 * Initialise le contenu du jeu, charge les animations
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Automate aut1 = new Automate(2);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.NORD, 1), new Libre(Cardinaux.NORD), 0);
		// décommenter pour tester attaque
		//aut1.ajoute_transition(0, new Attaquer(Cardinaux.SUD, 1), new Ennemy(Cardinaux.SUD), 0);
		//aut1.ajoute_transition(0, new Attaquer(Cardinaux.NORD, 1), new Ennemy(Cardinaux.NORD), 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.EST, 1), new Libre(Cardinaux.EST), 0);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.SUD, 0), new Libre(Cardinaux.SUD), 1);
		aut1.ajoute_transition(0, new Avancer(Cardinaux.OUEST, 0), new Libre(Cardinaux.OUEST), 1);

		aut1.ajoute_transition(1, new Avancer(Cardinaux.NORD, 0), new Libre(Cardinaux.NORD), 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.EST, 0), new Libre(Cardinaux.EST), 0);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.SUD, 1), new Libre(Cardinaux.SUD), 1);
		aut1.ajoute_transition(1, new Avancer(Cardinaux.OUEST, 1), new Libre(Cardinaux.OUEST), 1);
		ArrayList<Automate> autlist = new ArrayList<Automate>();
		autlist.add(aut1);
		Joueur j2 = new Joueur("Moi", autlist);
		World.addPlayer(j2);
		World.BuildMap();
		j2.createPersonnage(0, 5, 5);
		j2.createPersonnage(0, 6, 6);
		j2.createPersonnage(0, 3, 6);
		j2.createPersonnage(0, 6, 2);
		j2.createPersonnage(0, 0, 0);
		j2.createPersonnage(0, 2, 1);
		j2.createPersonnage(0, 4, 7);

		j2.createPersonnage(0, 0, 1);
		j2.createPersonnage(0, 0, 2);
		j2.createPersonnage(0, 0, 3);
		j2.createPersonnage(0, 0, 4);
		j2.createPersonnage(0, 0, 5);
		j2.createPersonnage(0, 0, 6);
		j2.createPersonnage(0, 0, 7);
		j2.createPersonnage(0, 0, 8);
		j2.createPersonnage(0, 0, 9);

		j2.createPersonnage(0, 1, 1);
		j2.createPersonnage(0, 1, 2);
		j2.createPersonnage(0, 1, 3);
		j2.createPersonnage(0, 1, 4);
		j2.createPersonnage(0, 1, 5);
		j2.createPersonnage(0, 1, 6);
		j2.createPersonnage(0, 1, 7);
		j2.createPersonnage(0, 1, 8);
		j2.createPersonnage(0, 1, 9);
		//personnage = j2.getPersonnages().get(0);


		this.container = container;
		this.map.init();

		Player pla;
		for(Joueur j : World.getPlayers())
		for(Personnage pers : j.getPersonnages())
		{
			pla = new Player();
			pla.init(pers);
			_players.add(pla);
		}

		//System.out.print(_players.size());


		/*_players.add(new Player());
		_players.get(0).init();
		_players.get(0).setX(100+300);
		_players.get(0).setDestX(100+300);
		_players.get(0).setY(100+300);
		_players.get(0).setDestY(100+300);*/
		//PlayerController controller = new PlayerController(_players.get(0));
		//container.getInput().addKeyListener(controller);
		this.hud.init();
	}

	/**
	 * Affichage des différents éléments du jeu
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		this.map.render();
		for(Player p : _players)
			p.render(g);
		this.hud.render(g);
	}

	protected long _time = 0;

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for(Player player : _players)
				player.update(delta);

		_time += delta;
		if(_time > 400)
		{
			_time -= 400;
			World.nextTurn();
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
