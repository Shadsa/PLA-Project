package graphique;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	private TiledMap tileMap;

	public void init() throws SlickException {
		this.tileMap = new TiledMap("src/asset/maps/maptest.tmx");
	}
	
	public void render() {
		this.tileMap.render(0, 0);
	}
}
