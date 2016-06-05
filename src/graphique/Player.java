package graphique;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {
	//Position d'origine du personnage (x, y)
	private float x = 240, y = 148;
	//direction : 0 -> haut, 1 -> gauche, 2 -> bas, droit -> 3
	private int direction = 0;
	//Boolean pour savoir si le personnage bouge
	private boolean moving = false;
	//Tableau des modèles d'animation
	private Animation[] animations = new Animation[8];
	
	//Test walking player
	private int type;
	private float action_finie = 0;
	private boolean selectedPlayer = false;
	
	public Player() {
	}
	
	public Player(int type) {
		this.type = type;
	}
	
	public void init() throws SlickException {
		SpriteSheet spriteSheet;
		if (this.getType() == 0) {
			spriteSheet = new SpriteSheet("src/asset/sprites/BODY_skeleton_leather.png", 64, 64);
		    this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		    this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		    this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		    this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		    this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		    this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		    this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		    this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
		}
		else if (this.getType() == 1) {
			spriteSheet = new SpriteSheet("src/asset/sprites/BODY_male_iron.png", 64, 64);
		    this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		    this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		    this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		    this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		    this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		    this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		    this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		    this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
		}

	
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
		if (action_finie == 0) {
			moving = false;
		}
		if (this.moving) {
	        switch (this.direction) {
	            /*case 0: this.y -= .1f * delta; break;
	            case 1: this.x -= .1f * delta; break;
	            case 2: this.y += .1f * delta; break;
	            case 3: this.x += .1f * delta; break;*/
		        case 0: this.y -= 1; action_finie -= 1; break;
	            case 1: this.x -= 1; action_finie -= 1; break;
	            case 2: this.y += 1; action_finie -= 1; break;
	            case 3: this.x += 1; action_finie -= 1; break;
	        }
	    }
	}
	
	  public float getX() { return x; }
	  public void setX(float x) { this.x = x; }
	  public float getY() { return y; }
	  public void setY(float y) { this.y = y; }
	  
	  //Test walking player
	  public float getAction_finie() { return action_finie; }
	  public void setAction_finie(float x) { this.action_finie = x; }
	  
	  //Test with severals players
	  public boolean getSelectedPlayer() { return selectedPlayer; }
	  public void setSelectedPlayer(boolean select) { this.selectedPlayer = select; }
	  public int getType() {return type;}
	  
	  public int getDirection() { return direction; }  
	  public void setDirection(int direction) { this.direction = direction; }
	  public boolean isMoving() { return moving; }
	  public void setMoving(boolean moving) { this.moving = moving; }
}
