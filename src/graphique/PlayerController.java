package graphique;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class PlayerController implements KeyListener {
	
	private Player player = new Player();
	
	public PlayerController(Player player) {
		this.player = player;
	}

	public void setInput(Input input) { }


	public boolean isAcceptingInput() { return true; }
	 
	public void inputEnded() { }

	public void inputStarted() { }

	/**
	* Gestion d'évènements lors de la pression sur la touche de clavier
	*/
	public void keyPressed(int key, char c) {
		if(player.getSelectedPlayer()) {
			if(!player.isMoving()) {
				switch (key) {
					case Input.KEY_UP:    player.setDirection(0); player.setMoving(true); player.setAction_finie(96); break;
					case Input.KEY_LEFT:  player.setDirection(1); player.setMoving(true); player.setAction_finie(96); break;
					case Input.KEY_DOWN:  player.setDirection(2); player.setMoving(true); player.setAction_finie(96); break;
					case Input.KEY_RIGHT: player.setDirection(3); player.setMoving(true); player.setAction_finie(96); break;
				}
			}
		}
	}
		
	/**
	 * Gestion d'évènements lors du relâchement d'une touche de clavier
	 */
	public void keyReleased(int key, char c) {
		//player.setMoving(false);
	}
}
