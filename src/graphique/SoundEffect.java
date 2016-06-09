package graphique;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundEffect {
	private Sound _dead_human;
	private Sound _dead_skeleton;
	


	public void init() throws SlickException {
		_dead_human = new Sound("src/asset/sounds/cri_douleur_homme_08.ogg");
		_dead_skeleton = new Sound("src/asset/sounds/os_broye_03.ogg");
	}

	public Sound dead_human() {
		return _dead_human;
	}
	
	public Sound dead_skeleton() {
		return _dead_skeleton;
	}
}
