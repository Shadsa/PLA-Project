package graphique;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum TypeClothes {
	Cult("src/asset/sprites/cult_clothes.png","src/asset/sprites/cult_clothes_slash.png","src/asset/sprites/cult_clothes_hurt.png","src/asset/sprites/cult_clothes_spell.png"),
	Civilian("src/asset/sprites/villager_vest.png","src/asset/sprites/villager_vest_slash.png","src/asset/sprites/villager_vest_hurt.png","src/asset/sprites/villager_vest_spell.png"),
	Naked("src/asset/sprites/no_clothes.png","src/asset/sprites/no_clothes_slash.png","src/asset/sprites/no_clothes_hurt.png","src/asset/sprites/no_clothes_spell.png"),
	Armor("src/asset/sprites/armor.png","src/asset/sprites/armor_slash.png","src/asset/sprites/armor_hurt.png","src/asset/sprites/armor_spell.png");
	
	String _sprite;
	public Animation[] animations = new Animation[25];
	
	TypeClothes(String walk, String slash, String hurt, String spell){
		_sprite=walk;
		
		SpriteSheet walkSheet;
		SpriteSheet slashSheet;
		SpriteSheet hurtSheet;
		SpriteSheet spellSheet;
		try {
			walkSheet = new SpriteSheet(walk, 64, 64);
			slashSheet = new SpriteSheet(slash, 64, 64);
			hurtSheet = new SpriteSheet(hurt, 64, 64);
			spellSheet = new SpriteSheet(spell, 64, 64);
			Player.initAnimation(animations, walkSheet, slashSheet, hurtSheet, spellSheet);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public String sprite(){
		return _sprite;
	}
}