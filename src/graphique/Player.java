package graphique;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public class Player implements Observer{

	private float _destX, _destY;

	//Position d'origine du personnage (x, y)
	private float x, y;
	//direction : 0 -> haut, 1 -> gauche, 2 -> bas, droit -> 3
	private int direction = 0;
	//Boolean pour savoir si le personnage bouge
	private boolean moving = false;
	//Tableau des modèles d'animation
	private static Animation[] animations = new Animation[13];
	private static Animation[] Hanimations = new Animation[13];
	private static Animation[] Danimations = new Animation[4];
	//
	private int AnimDuration;
	public int AnimDead;
	private Boolean _isDead;
	private Boolean _human;
	private SoundEffect soundEffect;

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

	public void init(Personnage pers, Boolean human) throws SlickException {
		_human = human;
		_id = nextID();
		_isDead = false;
		AnimDuration = MapGameState.Tick;
		AnimDead = MapGameState.Tick;
		_state = new States(Statut.ATTENDS, Cardinaux.SUD);
		x = MapGameState.toX(pers.X());
		y = MapGameState.toY(pers.Y());
		_destX = MapGameState.toX(pers.X());
		_destY = MapGameState.toX(pers.Y());
		pers.addObserver(this);
		soundEffect = new SoundEffect();
		soundEffect.init();
	}

public static void sinit() throws SlickException
{
		SpriteSheet spriteSheet = new SpriteSheet("src/asset/sprites/BODY_skeleton.png", 64, 64);
		SpriteSheet spriteSheet2 = new SpriteSheet("src/asset/sprites/slash_skeleton.png", 64, 64);
		SpriteSheet spriteSheet3 = new SpriteSheet("src/asset/sprites/Die_skeleton.png", 64, 64);
	    animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
	    animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
	    animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
	    animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
	    animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
	    animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
	    animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
	    animations[7] = loadAnimation(spriteSheet, 1, 9, 3);


	    animations[8] = loadAnimation(spriteSheet2, 0, 5, 0);
	    animations[9] = loadAnimation(spriteSheet2, 0, 5, 1);
	    animations[10] = loadAnimation(spriteSheet2, 0, 5, 2);
	    animations[11] = loadAnimation(spriteSheet2, 0, 5, 3);

	    animations[12] = new Animation();
	    for (int x = 0; x < 5; x++) {
	    	animations[12].addFrame(spriteSheet3.getSprite(x, 0), 40);
	    }
	    for (int x = 0; x < 30; x++) {
	    	animations[12].addFrame(spriteSheet3.getSprite(5, 0), 40);
	    }






	    SpriteSheet HspriteSheet = new SpriteSheet("src/asset/sprites/BODY_male.png", 64, 64);
		SpriteSheet HspriteSheet2 = new SpriteSheet("src/asset/sprites/BODY_human_slash_test.png", 64, 64);
		SpriteSheet HspriteSheet3 = new SpriteSheet("src/asset/sprites/Human_Die.png", 64, 64);
	    Hanimations[0] = loadAnimation(HspriteSheet, 0, 1, 0);
	    Hanimations[1] = loadAnimation(HspriteSheet, 0, 1, 1);
	    Hanimations[2] = loadAnimation(HspriteSheet, 0, 1, 2);
	    Hanimations[3] = loadAnimation(HspriteSheet, 0, 1, 3);
	    Hanimations[4] = loadAnimation(HspriteSheet, 1, 9, 0);
	    Hanimations[5] = loadAnimation(HspriteSheet, 1, 9, 1);
	    Hanimations[6] = loadAnimation(HspriteSheet, 1, 9, 2);
	    Hanimations[7] = loadAnimation(HspriteSheet, 1, 9, 3);


	    Hanimations[8] = loadAnimation(HspriteSheet2, 0, 5, 0);
	    Hanimations[9] = loadAnimation(HspriteSheet2, 0, 5, 1);
	    Hanimations[10] = loadAnimation(HspriteSheet2, 0, 5, 2);
	    Hanimations[11] = loadAnimation(HspriteSheet2, 0, 5, 3);

	    Hanimations[12] = new Animation();
	    for (int x = 0; x < 5; x++) {
	    	Hanimations[12].addFrame(HspriteSheet3.getSprite(x, 0), 40);
	    }
	    for (int x = 0; x < 30; x++) {
	    	Hanimations[12].addFrame(HspriteSheet3.getSprite(5, 0), 40);
	    }

	    SpriteSheet spriteSheetW = new SpriteSheet("src/asset/sprites/WEAPON_dagger.png", 64, 64);
	    Danimations[0] = loadAnimation(spriteSheetW, 0, 5, 0);
	    Danimations[1] = loadAnimation(spriteSheetW, 0, 5, 1);
	    Danimations[2] = loadAnimation(spriteSheetW, 0, 5, 2);
	    Danimations[3] = loadAnimation(spriteSheetW, 0, 5, 3);

	}

	/**
	 * Permet de charger les animations à partir de la feuille de sprite pour créer les modèles d'animations.
	 * @param spriteSheet : la feuille de sprites
	 * @param startX :
	 * @param endX
	 * @param y
	 * @return
	 */
	private static Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
	    Animation animation = new Animation();
	    for (int x = startX; x < endX; x++) {
	        animation.addFrame(spriteSheet.getSprite(x, y), 100);
	    }
	    return animation;
	}

	public void render(Graphics g) throws SlickException {
		int anim = 0;
		int dir = 0;
		int danim = -1;
		//Affichage du personnage avec l'ombre et modification des coordonnées des pieds du personnage
				g.setColor(new Color(0, 0, 0, .5f));
			    g.fillOval(x - 16, y - 8, 32, 16);

			    if(_state.direction != null)
				switch(_state.direction)
				{
				case NORD: dir = 2; break;
				case SUD: dir = 0; break;
				case EST: dir = 3; break;
				case OUEST: dir = 1; break;
				}

			    switch(_state.statut)
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

			    if(_isDead && AnimDuration <= 0) {
			    	anim = 12;
			    }
			    //System.out.println(_state.statut);
			    if(_human)
			    	g.drawAnimation(Hanimations[anim], x-32, y-60);
			    else
			    	g.drawAnimation(animations[anim], x-32, y-60);

			    if(danim != -1 && anim != 12)
			    	g.drawAnimation(Danimations[danim], x-32, y-60);
	}

	/**
	 * Met à jour le conteneur du jeu
	 */
	public void update(int delta) {
		AnimDuration -= delta;
		if(_isDead)
		if(AnimDuration<0)
		{
			AnimDead -= delta;
		}
		if(_state.statut != Statut.AVANCE) return;
	    if (_destX > x)
	    {

	    	if(_destX-x-MapGameState.MoveSpeed * delta<0)
	    		x = _destX;
	    	else
	    		this.x += MapGameState.MoveSpeed * delta;
	    }
	    else if(_destX < x)
	    {
	    	if(x-_destX-MapGameState.MoveSpeed * delta<0)
	    		x = _destX;
	    	else
	    		this.x -= MapGameState.MoveSpeed * delta;
	    }
	    if (_destY > y)
	    {

	    	if(_destY-y-MapGameState.MoveSpeed * delta<0)
	    		y = _destY;
	    	else
	    		this.y += MapGameState.MoveSpeed * delta;
	    }
	    else if(_destY < y)
	    {
	    	if(y-_destY-MapGameState.MoveSpeed * delta<0)
	    		y = _destY;
	    	else
	    		this.y -= MapGameState.MoveSpeed * delta;
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

		public float DestX() {
			return _destX;
		}

		public void setDestY(int i) {
			_destY = i;
		}

		public float DestY() {
			return _destY;
		}

		public void update(Observable obs, Object obj) {
			if(obs instanceof Personnage){
				Personnage pers = (Personnage)obs;
				_destX = MapGameState.toX(pers.X());
				_destY = MapGameState.toX(pers.Y());
				if(((States)obj).statut != Statut.MORT)
				{
					_state = (States)obj;
					AnimDuration += MapGameState.Tick;
				}
				else {
					_isDead = true;
					if(_human)
						soundEffect.dead_human().play();
					if(!_human)
						soundEffect.dead_skeleton().play();
				}
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
