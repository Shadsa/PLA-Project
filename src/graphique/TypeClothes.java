package graphique;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum TypeClothes {
	Cult("src/asset/sprites/cult_clothes.png","src/asset/sprites/cult_clothes_slash.png","src/asset/sprites/cult_clothes_hurt.png"),
	Civilian("src/asset/sprites/villager_vest.png","src/asset/sprites/villager_vest_slash.png","src/asset/sprites/villager_vest_hurt.png"),
	Naked("src/asset/sprites/no_clothes.png","src/asset/sprites/no_clothes_slash.png","src/asset/sprites/no_clothes_hurt.png"),
	Armor("src/asset/sprites/armor.png","src/asset/sprites/armor_slash.png","src/asset/sprites/armor_hurt.png");
	
	String _sprite;
	public Animation[] animations = new Animation[21];
	
	TypeClothes(String sprite1, String sprite2, String sprite3){
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
