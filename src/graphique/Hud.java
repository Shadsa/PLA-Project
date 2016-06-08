package graphique;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {

	private Image playerBars;
	private static final int HUD_BAR_X = 10;
	private static final int HUD_BAR_Y = 150;

	public void init() throws SlickException {
		this.playerBars = new Image("src/asset/sprites/hud.png");
	}

	public void render (Graphics g) {
		if (MapGameState._targetp != null) {
			g.drawImage(this.playerBars, HUD_BAR_X, HUD_BAR_Y);
			g.drawString("Vie : " + MapGameState._targetp.vie(), 20, 140);
		}
	}

}
