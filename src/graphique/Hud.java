package graphique;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {
	
	private Image playerBars;
	private static final int HUD_BAR_X = 10;
	private static final int HUD_BAR_Y = 130;
	
	public void init() throws SlickException {
		this.playerBars = new Image("src/asset/sprites/hud.png");
	}
	
	public void render (Graphics g) {
		g.drawImage(this.playerBars, HUD_BAR_X, HUD_BAR_Y);
	}
	
}
