package graphique;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public class Player implements Observer{
	private int _distanceDeplacement = 96;
	private int _destX, _destY;
	//Position d'origine du personnage (x, y)
	private float x, y;
	//direction : 0 -> haut, 1 -> gauche, 2 -> bas, droit -> 3
	private int direction = 0;
	//Boolean pour savoir si le personnage bouge
	private boolean moving = false;
	//Tableau des modèles d'animation
	private Animation[] animations = new Animation[12];

	public final int x0 = 116, y0 = 116;
	public final int tx = 40, ty = 40;

	protected int _id;

	protected States _state;

	private static int _nextID = 0;
	protected static int nextID()
	{
		return _nextID++;
	}
	public int ID()
	{
		return _id;
	}

	public void init(Personnage pers) throws SlickException {
		_id = nextID();
		_state = new States(Statut.ATTENDS, Cardinaux.SUD);
		x = pers.X()*tx+x0;
		y = pers.Y()*ty+y0;
		_destX = pers.X()*tx+x0;
		_destY = pers.Y()*ty+y0;
		pers.addObserver(this);
		SpriteSheet spriteSheet = new SpriteSheet("src/asset/sprites/BODY_skeleton.png", 64, 64);
		SpriteSheet spriteSheet2 = new SpriteSheet("src/asset/sprites/slash_skeleton.png", 64, 64);
	    this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
	    this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
	    this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
	    this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
	    this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
	    this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
	    this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
	    this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);


	    this.animations[8] = loadAnimation(spriteSheet2, 0, 5, 0);
	    this.animations[9] = loadAnimation(spriteSheet2, 0, 5, 1);
	    this.animations[10] = loadAnimation(spriteSheet2, 0, 5, 2);
	    this.animations[11] = loadAnimation(spriteSheet2, 0, 5, 3);
	}

	/**
	 * Permet de charger les animations à partir de la feuille de sprite pour créer les modèles d'animations.
	 * @param spriteSheet : la feuille de sprites
	 * @param startX :
	 * @param endX
	 * @param y
	 * @return
	 */
	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
	    Animation animation = new Animation();
	    for (int x = startX; x < endX; x++) {
	        animation.addFrame(spriteSheet.getSprite(x, y), 100);
	    }
	    return animation;
	}

	public void render(Graphics g) throws SlickException {
		int anim = 0;
		//Affichage du personnage avec l'ombre et modification des coordonnées des pieds du personnage
				g.setColor(new Color(0, 0, 0, .5f));
			    g.fillOval(x - 16, y - 8, 32, 16);

			    if(_state.direction != null)
				switch(_state.direction)
				{
				case NORD: anim = 2; break;
				case SUD: anim = 0; break;
				case EST: anim = 3; break;
				case OUEST: anim = 1; break;
				}

			    switch(_state.statut)
			    {
			    case AVANCE:
			    	anim += 4;
			    break;
			    case ATTENDS:
			    	anim += 0;
			    break;
				case ATTAQUE:
					anim += 8;
				break;
			    }//
			    //System.out.println(_state.statut);
			    g.drawAnimation(animations[anim], x-32, y-60);
	}

	/**
	 * Met à jour le conteneur du jeu
	 */
	public void update(int delta) {
		if(_state.statut != Statut.AVANCE) return;
	    if (_destX > x)
	    {

	    	if(_destX-x-.1f * delta<0)
	    		x = _destX;
	    	else
	    		this.x += .1f * delta;
	    }
	    else if(_destX < x)
	    {
	    	if(x-_destX-.1f * delta<0)
	    		x = _destX;
	    	else
	    		this.x -= .1f * delta;
	    }
	    if (_destY > y)
	    {

	    	if(_destY-y-.1f * delta<0)
	    		y = _destY;
	    	else
	    		this.y += .1f * delta;
	    }
	    else if(_destY < y)
	    {
	    	if(y-_destY-.1f * delta<0)
	    		y = _destY;
	    	else
	    		this.y -= .1f * delta;
	    }

	    if(_destX == x && _destY == y)
	    	_state.statut = Statut.ATTENDS;
	}

	  public float getX() { return x; }
	  public void setX(float x) { this.x = x; }
	  public float getY() { return y; }
	  public void setY(float y) { this.y = y; }

	 /* //Test walking player
	  public float getAction_finie() { return action_finie; }
	  public void setAction_finie(float x) { this.action_finie = x; }

	  //Test with severals players
	  public boolean getSelectedPlayer() { return selectedPlayer; }
	  public void setSelectedPlayer(boolean select) { this.selectedPlayer = select; }
	  public int getType() {return type;}
	  */
	  public int getDirection() { return direction; }
	  public void setDirection(int direction) { this.direction = direction; }
	  public boolean isMoving() { return moving; }
	  public void setMoving(boolean moving) { this.moving = moving; }

		public void setDestX(int f) {
			_destX = f;
		}

		public int DestX() {
			return _destX;
		}

		public void setDestY(int i) {
			_destY = i;
		}

		public int DestY() {
			return _destY;
		}

		public void update(Observable obs, Object obj) {
			if(obs instanceof Personnage){
				Personnage pers = (Personnage)obs;
				_destX = pers.X()*tx+x0;
				_destY = pers.Y()*ty+y0;
				if(_state.statut != Statut.MORT)
					_state = (States)obj;
			}
		}
		private void setDirection(Cardinaux dir) {
			switch(dir)
			{
			case NORD: direction = 2; break;
			case SUD: direction = 0; break;
			case EST: direction = 3; break;
			case OUEST: direction = 1; break;
			}
		}
		public States states() {
			return _state;
		}
}
