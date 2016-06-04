package graphique;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {
	private int _destX = 300, _destY = 300;
	//Position d'origine du personnage (x, y)
	private float x = 300, y = 300;
	//direction : 0 -> haut, 1 -> gauche, 2 -> bas, droit -> 3
	private int direction = 0;
	//Boolean pour savoir si le personnage bouge
	private boolean moving = false;
	//Tableau des modèles d'animation
	private Animation[] animations = new Animation[8];

	public void init() throws SlickException {
		SpriteSheet spriteSheet = new SpriteSheet("src/asset/sprites/BODY_skeleton.png", 64, 64);
	    this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
	    this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
	    this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
	    this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
	    this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
	    this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
	    this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
	    this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

	}

	/**
	 * Permet de charger les animations à partir du la feuille de sprite pour créer les modèles d'animations.
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
		//Affichage du personnage avec l'ombre et modification des coordonnées des pieds du personnage
				g.setColor(new Color(0, 0, 0, .5f));
			    g.fillOval(x - 16, y - 8, 32, 16);
			    g.drawAnimation(animations[direction + (moving ? 4 : 0)], x-32, y-60);
	}

	/**
	 * Met à jour le conteneur du jeu
	 */
	public void update(int delta) {
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
	}

	  public float getX() { return x; }
	  public void setX(float x) { this.x = x; }
	  public float getY() { return y; }
	  public void setY(float y) { this.y = y; }
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
}
