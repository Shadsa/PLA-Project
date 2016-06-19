package graphique;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {//550 182

	static public Image playerBars;
	private static final int HUD_BAR_X = 10;
	private static int HUD_BAR_Y = 150;
	private int debug = 10;

	public void init() throws SlickException {
		playerBars = new Image("src/asset/sprites/ui_big_pieces.png");
	}

	public void render (Graphics g, int j) { HUD_BAR_Y = j;
		if (MapGameState._targetp != null) {
			g.resetTransform();
			g.drawImage(playerBars, HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug, HUD_BAR_X+83, MapGameState.debug ? HUD_BAR_Y+65 : debug+65, 447, 178, 530, 243);
			for(int i = 0; i<MapGameState._targetp.classe().HP(); i++)
				g.drawImage(playerBars, HUD_BAR_X+83+7*i, MapGameState.debug ? HUD_BAR_Y+6 : debug+6, HUD_BAR_X+7*(i+1)+83, MapGameState.debug ? HUD_BAR_Y+15+6 : debug+15+6, 533, 184, 540, 199);
			for(int i = 0; i<MapGameState._targetp.vie(); i++)
				g.drawImage(playerBars, HUD_BAR_X+83+7*i-1, MapGameState.debug ? HUD_BAR_Y+6+2 : debug+6+2, HUD_BAR_X+7*(i+1)+83, MapGameState.debug ? HUD_BAR_Y+15+6+1 : debug+15+6+1, 591, 198, 600, 184);
			g.drawImage(playerBars, HUD_BAR_X+83+7*MapGameState._targetp.classe().HP()-1, MapGameState.debug ? HUD_BAR_Y+6+2-4 : debug+6+2-4, HUD_BAR_X+7*(MapGameState._targetp.classe().HP()+1)+83+32-7, MapGameState.debug ? HUD_BAR_Y+15+6+1+5-4 : 10+15+6+1+5-4, 550, 182, 583, 202);
			//g.drawString("Vie : " + MapGameState._targetp.vie(), 20, 140);
			//g.drawImage(this.playerBars, HUD_BAR_X+83+7, HUD_BAR_Y+6, HUD_BAR_Y+6, HUD_BAR_X+7*(1)+83,  591, 198, 600, 184);
			renderPers(g);
		}
	}


	private void renderPers(Graphics g)  {

			    if(MapGameState._target._Abody != -1)
			    	g.drawAnimation(MapGameState._target._Body[MapGameState._target._Abody], HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug);

				if(MapGameState._target._Awear != -1)
					g.drawImage(MapGameState._target._clothes.animations[MapGameState._target._Abody].getImage(MapGameState._target._human.animations[MapGameState._target._Abody].getFrame()), HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug);

			    if(MapGameState._target._Aweapon != -1)
			    	g.drawAnimation(Player.Danimations[MapGameState._target._Aweapon], HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug);
	}
}
