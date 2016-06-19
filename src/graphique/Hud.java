package graphique;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {//550 182

	static public Image playerBars;
	private static final int HUD_BAR_X = 10;
	private static final int HUD_BAR_Y = 150;
	private int debug = 10;

	public void init() throws SlickException {
		playerBars = new Image("src/asset/sprites/ui_big_pieces.png");
	}

	public void render (Graphics g) {
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
		int anim = 0;
		int dir = 0;
		int danim = -1;
		//Affichage du personnage avec l'ombre et modification des coordonnées des pieds du personnage
				/*g.setColor(new Color(0, 0, 0, .5f));
			    g.fillOval(16, 8, 32, 16);*/

			    if(MapGameState._target._state.direction != null)
				switch(MapGameState._target._state.direction)
				{
				case NORD: dir = 0; break;
				case SUD: dir = 2; break;
				case EST: dir = 3; break;
				case OUEST: dir = 1; break;
				}

			    switch(MapGameState._target._state.statut)
			    {
			    case AVANCE:
			    	anim = 4;
			    break;
			    case ATTENDS:
			    	anim = 0;
			    break;
				case ATTAQUE:
					anim = 8;
					danim = dir;
				break;
			    }

			    anim += dir;

			    if(MapGameState._target.isDead() && MapGameState._target.AnimDuration() <= 0) {
			    	anim = 12;
			    }
			    //System.out.println(_state.statut);
			    //if(MapGameState._target.human() == TypeUnit.Human)
			    //	g.drawAnimation(MapGameState._target.human().animations[anim], HUD_BAR_X+1, MapGameState.debug ? HUD_BAR_Y-3 : 10-3);
			    /*else
			    	g.drawAnimation(Player.animations[anim], HUD_BAR_X, HUD_BAR_Y);*/

			    /*if(danim != -1 && anim != 12)
			    	g.drawAnimation(Player.Danimations[danim], HUD_BAR_X, HUD_BAR_Y);*/




			    if(MapGameState._target._Abody != -1)
				{
					    /*g.setColor(new Color(0, 0, 0, .5f));
					    g.fillOval(HUD_BAR_X - 16, HUD_BAR_Y - 8, 32, 16);*/
			    		g.drawAnimation(MapGameState._target._Body[MapGameState._target._Abody], HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug);
				}

				if(MapGameState._target._Awear != -1)
					g.drawImage(MapGameState._target._clothes.animations[MapGameState._target._Abody].getImage(MapGameState._target._human.animations[MapGameState._target._Abody].getFrame()), HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug);

			    if(MapGameState._target._Aweapon != -1)
			    	g.drawAnimation(Player.Danimations[MapGameState._target._Aweapon], HUD_BAR_X, MapGameState.debug ? HUD_BAR_Y : debug);
	}
}
