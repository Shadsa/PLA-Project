package graphique;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import roles.Cardinaux;
import roles.Personnage;
import roles.States;
import roles.States.Statut;

public class Player implements Observer{

	private Animation[] _Body;
	private int _Abody;
	private int _Aweapon;
	private int _Awear;

	private float _destX, _destY;
	private boolean _hide;

	//Position d'origine du personnage (x, y)
	private float x, y;
	//direction : 0 -> haut, 1 -> gauche, 2 -> bas, droit -> 3
	private int direction = 0;
	//Boolean pour savoir si le personnage bouge
	private boolean moving = false;
	//Tableau des modèles d'animation
	/*public static Animation[][] _Bodys = new Animation[2][21];
	public static Animation[] animations = _Bodys[0];
	public static Animation[] Hanimations = _Bodys[1];
	public static Animation[] Habits = new Animation[21];*/
	public static Animation[] Danimations = new Animation[4];
	//
	private int AnimDuration;
	public int AnimDead;
	private Boolean _isDead;
	private TypeUnit _human;
	private TypeClothes _clothes;	
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

	public Player(Personnage pers, TypeUnit _type_unit, TypeClothes _type_clothes) {
		_hide = false;
		_human = _type_unit;
		_clothes = _type_clothes;
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
		try {
			soundEffect.init();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		_Body = _human.animations;//(_human != TypeUnit.Zombie)? Hanimations : animations ;
		_Abody = 0;
		_Aweapon = -1;
		_Awear = -1;
	}

public static void sinit() throws SlickException
{
		/*SpriteSheet spriteSheet = new SpriteSheet("src/asset/sprites/BODY_skeleton.png", 64, 64);
		SpriteSheet spriteSheet2 = new SpriteSheet("src/asset/sprites/slash_skeleton.png", 64, 64);
		SpriteSheet spriteSheet3 = new SpriteSheet("src/asset/sprites/Die_skeleton.png", 64, 64);
		initAnimation(animations, spriteSheet, spriteSheet2, spriteSheet3);

	    SpriteSheet HspriteSheet = new SpriteSheet("src/asset/sprites/BODY_male.png", 64, 64);
		SpriteSheet HspriteSheet2 = new SpriteSheet("src/asset/sprites/Human_Slash.png", 64, 64);
		SpriteSheet HspriteSheet3 = new SpriteSheet("src/asset/sprites/Human_Die.png", 64, 64);
		initAnimation(Hanimations, HspriteSheet, HspriteSheet2, HspriteSheet3);
*/
		/*SpriteSheet Habitsprite = new SpriteSheet("src/asset/sprites/villager_vest.png", 64, 64);
		SpriteSheet Habitsprite2 = new SpriteSheet("src/asset/sprites/villager_vest_slash.png", 64, 64);
		SpriteSheet Habitsprite3 = new SpriteSheet("src/asset/sprites/villager_vest_hurt.png", 64, 64);
		SpriteSheet Habitsprite = new SpriteSheet("src/asset/sprites/cult_clothes.png", 64, 64);
		SpriteSheet Habitsprite2 = new SpriteSheet("src/asset/sprites/cult_clothes_slash.png", 64, 64);
		SpriteSheet Habitsprite3 = new SpriteSheet("src/asset/sprites/cult_clothes_hurt.png", 64, 64);
		initAnimation(Habits, Habitsprite, Habitsprite2, Habitsprite3);*/



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
		/*int anim = 0;
		int dir = 0;
		int danim = -1;
		int vanim = -1;
		//Affichage du personnage avec l'ombre et modification des coordonnées des pieds du personnage
				if(_state.direction != null)
				switch(_state.direction)
				{
				case NORD: dir = 0; break;
				case SUD: dir = 2; break;
				case EST: dir = 3; break;
				case OUEST: dir = 1; break;
				}

			    switch(_state.statut)
			    {
			    case AVANCE:
			    	anim = 4;
			    	vanim = anim;
			    break;
			    case ATTENDS:
			    	anim = 0;
			    	vanim = anim;
			    break;
				case ATTAQUE:
					anim = 8;
			    	vanim = anim;
					danim = dir;
				break;
				case HIDING:
					anim = 13;
				break;
				case HIDE:
					return;
				case REVEAL:
					anim = 17;
				break;
			    }

			    anim += dir;

			    if(_isDead && AnimDuration <= 0) {
			    	anim = 12;
			    }
			    //System.out.println(_state.statut);
			    g.setColor(new Color(0, 0, 0, .5f));
			    g.fillOval(x - 16, y - 8, 32, 16);

			    if(_human == TypeUnit.Human)
			    	g.drawAnimation(Hanimations[anim], x-32, y-60);
			    else
			    	g.drawAnimation(animations[anim], x-32, y-60);

			    if(_human != TypeUnit.Zombie && vanim != -1)
			    	g.drawImage(Habits[anim].getImage(Hanimations[anim].getFrame()), x-32, y-60);

			    if(danim != -1 && anim != 12)
			    	g.drawAnimation(Danimations[danim], x-32, y-60);*/

		if(_Abody != -1)
		{
			    g.setColor(new Color(0, 0, 0, .5f));
			    g.fillOval(x - 16, y - 8, 32, 16);
	    		g.drawAnimation(_Body[_Abody], x-32, y-60);
		}

	    if(_Awear != -1)
	    	g.drawImage(_clothes.animations[_Abody].getImage(_human.animations[_Abody].getFrame()), x-32, y-60);

	    if(_Aweapon != -1)
	    	g.drawAnimation(Danimations[_Aweapon], x-32, y-60);
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
		//if(_state.statut != Statut.AVANCE) return;
		if(_destX == x && _destY==y) return;
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
	    {
	    	if(_state.statut == Statut.HIDE || _state.statut == Statut.HIDING)
	    		_state.statut = Statut.HIDE;
	    	else
	    		_state.statut = Statut.ATTENDS;
			refreshAnimation();
	    }
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
				if(((States)obj).statut == Statut.INVOQUE) {
					soundEffect.invoquer().play(1.0f, 0.4f);
				} else if(((States)obj).statut == Statut.MORT) {
					_isDead = true;
					if(_human == TypeUnit.Human)
						soundEffect.dead_human().play();
					else
						soundEffect.dead_skeleton().play();
				}
				if(_state.statut == Statut.HIDE || _state.statut == Statut.HIDING) {
					if(((States)obj).statut != Statut.REVEAL)
						_state.statut = Statut.HIDE;
					else
						_state = (States)obj;
				}
				else
				{
					_state = (States)obj;
					AnimDuration += MapGameState.Tick;
				}
				refreshAnimation();
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
		public boolean isDead() {
			return _isDead;
		}
		public TypeUnit human() {
			return _human;
		}
		public int AnimDuration() {
			return AnimDuration;
		}

		private void refreshAnimation()
		{
			int anim = 0;
			int dir = 0;
			int danim = -1;
			_Awear = -1;
			//Affichage du personnage avec l'ombre et modification des coordonnées des pieds du personnage
					if(_state.direction != null)
					switch(_state.direction)
					{
					case NORD: dir = 0; break;
					case SUD: dir = 2; break;
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
					case HIDING:
						anim = 13;
					break;
					case REVEAL:
						anim = 17;
					break;
					case INVOQUE:
						anim = 21;
					break;
				    }

				    anim += dir;
				    if(_state.statut == Statut.HIDE)
				    	anim = -1;

				    if(_isDead && AnimDuration <= 0) {
				    	anim = 12;
				    }
					_Awear = anim;

				    _Abody = anim;
				    /*if(_human == TypeUnit.Zombie)
				    	_Awear = -1;*/
				    _Aweapon = danim;
		}

		static void initAnimation(Animation[] anim, SpriteSheet moveS, SpriteSheet slashS, SpriteSheet dieS, SpriteSheet spellS)
		{

			anim[0] = loadAnimation(moveS, 0, 1, 0);
		    anim[1] = loadAnimation(moveS, 0, 1, 1);
		    anim[2] = loadAnimation(moveS, 0, 1, 2);
		    anim[3] = loadAnimation(moveS, 0, 1, 3);
		    anim[4] = loadAnimation(moveS, 1, 9, 0);
		    anim[5] = loadAnimation(moveS, 1, 9, 1);
		    anim[6] = loadAnimation(moveS, 1, 9, 2);
		    anim[7] = loadAnimation(moveS, 1, 9, 3);


		    anim[8] = loadAnimation(slashS, 0, 5, 0);
		    anim[9] = loadAnimation(slashS, 0, 5, 1);
		    anim[10] = loadAnimation(slashS, 0, 5, 2);
		    anim[11] = loadAnimation(slashS, 0, 5, 3);
		    
		    anim[21] = loadAnimation(spellS, 0, 6, 0);
		    anim[22] = loadAnimation(spellS, 0, 6, 1);
		    anim[23] = loadAnimation(spellS, 0, 6, 2);
		    anim[24] = loadAnimation(spellS, 0, 6, 3);

		    anim[21].setLooping(false);
		    anim[22].setLooping(false);
		    anim[23].setLooping(false);
		    anim[24].setLooping(false);

		    anim[12] = new Animation();
		    for (int x = 0; x < 6; x++) {
		    	anim[12].addFrame(dieS.getSprite(x, 0), 40);
		    }
		    anim[12].setLooping(false);


		    for(int j=0; j<4; j++)
		    {
			    anim[13+j] = new Animation();
			    anim[13+j].addFrame(moveS.getSprite(0, j), 100);
			    anim[13+j].addFrame(moveS.getSprite(1, j), 100);

			    Image iii = moveS.getSprite(2, j);
			    iii.setAlpha(0.95f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));

			    iii = moveS.getSprite(3, j);
			    iii.setAlpha(0.9f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(4, j);
			    iii.setAlpha(0.8f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(5, j);
			    iii.setAlpha(0.7f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(6, j);
			    iii.setAlpha(0.6f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(7, j);
			    iii.setAlpha(0.3f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(8, j);
			    iii.setAlpha(0f);
			    anim[13+j].addFrame(iii, (int) (MapGameState.AnimTick/9));

			    anim[13+j].setLooping(false);
		    }
		    /*for (int x = 0; x < 30; x++) {
		    	anim[12].addFrame(dieS.getSprite(5, 0), 40);
		    }*/


		    for(int j=0; j<4; j++)
		    {
			    anim[17+j] = new Animation();

			    Image iii = moveS.getSprite(2, j);
			    iii.setAlpha(0);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));

			    iii = moveS.getSprite(3, j);
			    iii.setAlpha(0.3f);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));

			    iii = moveS.getSprite(3, j);
			    iii.setAlpha(0.6f);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));

			    iii = moveS.getSprite(3, j);
			    iii.setAlpha(0.7f);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(4, j);
			    iii.setAlpha(0.8f);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(5, j);
			    iii.setAlpha(0.9f);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(6, j);
			    iii.setAlpha(0.95f);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(7, j);
			    iii.setAlpha(1);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));


			    iii = moveS.getSprite(8, j);
			    iii.setAlpha(1);
			    anim[17+j].addFrame(iii, (int) (MapGameState.AnimTick/9));

			    anim[17+j].setLooping(false);
		    }
		}
}
