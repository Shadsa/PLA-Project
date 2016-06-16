package graphique;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum TypeUnit {
	Human("src/asset/sprites/BODY_male.png","src/asset/sprites/Human_Slash.png","src/asset/sprites/Human_Die.png"),
	Zombie("src/asset/sprites/BODY_skeleton.png","src/asset/sprites/slash_skeleton.png","src/asset/sprites/Die_skeleton.png");
	
	String _sprite;
	public Animation[] animations = new Animation[21];
	
	TypeUnit(String sprite1, String sprite2, String sprite3){
		_sprite=sprite1;
		
		SpriteSheet spriteSheet1;
		SpriteSheet spriteSheet2;
		SpriteSheet spriteSheet3;
		try {
			spriteSheet1 = new SpriteSheet(sprite1, 64, 64);
			spriteSheet2 = new SpriteSheet(sprite2, 64, 64);
			spriteSheet3 = new SpriteSheet(sprite3, 64, 64);
			Player.initAnimation(animations, spriteSheet1, spriteSheet2, spriteSheet3);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public String sprite(){
		return _sprite;
	}
}
