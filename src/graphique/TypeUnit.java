package graphique;

public enum TypeUnit {
	Human("src/asset/sprites/BODY_male.png"),
	Zombie("src/asset/sprites/BODY_skeleton.png");
	
	String _sprite;
	TypeUnit(String sprite){
		_sprite=sprite;
	}
	
	public String sprite(){
		return _sprite;
	}
}
