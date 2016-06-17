package graphique;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum TypeUnit {
	Human("src/asset/sprites/BODY_male.png","src/asset/sprites/Human_Slash.png","src/asset/sprites/Human_Die.png","src/asset/sprites/BODY_male_spell.png"),
	Zombie("src/asset/sprites/BODY_grey.png","src/asset/sprites/BODY_grey_slash.png","src/asset/sprites/BODY_grey_hurt.png","src/asset/sprites/BODY_grey_spell.png"),
	Skeleton("src/asset/sprites/BODY_skeleton.png","src/asset/sprites/slash_skeleton.png","src/asset/sprites/Die_skeleton.png","src/asset/sprites/BODY_skeleton_spell.png");
	
	String _sprite;
	public Animation[] animations = new Animation[25];
	
	TypeUnit(String walk, String slash, String hurt, String spell){
		_sprite=walk;
		
		SpriteSheet walkSheet, slashSheet, hurtSheet, spellSheet;

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