package graphique;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {//532 184

	private Image playerBars;
	private static final int HUD_BAR_X = 10;
	private static final int HUD_BAR_Y = 150;

	public void init() throws SlickException {
		this.playerBars = new Image("src/asset/sprites/ui_big_pieces.png");
	}

	public void render (Graphics g) {
		if (MapGameState._targetp != null) {
			g.drawImage(this.playerBars, HUD_BAR_X, HUD_BAR_Y, HUD_BAR_X+83, HUD_BAR_Y+65, 447, 178, 530, 243);
			for(int i = 0; i<MapGameState._targetp.vie(); i++)
				g.drawImage(this.playerBars, HUD_BAR_X+83+7*i, HUD_BAR_Y+6, HUD_BAR_X+7*(i+1)+83, HUD_BAR_Y+15+6, 533, 184, 540, 199);
			/*for(int i = 0; i<10; i++)
				g.drawImage(this.playerBars, HUD_BAR_X+83+7*i, HUD_BAR_Y+6, HUD_BAR_X+7*(i+1)+83, HUD_BAR_Y+15+6, 533, 184, 540, 199);*/
			g.drawString("Vie : " + MapGameState._targetp.vie(), 20, 140);
		}
	}

}
