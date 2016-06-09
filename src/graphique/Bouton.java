package graphique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

public class Bouton extends MouseOverArea {
  
	//private Image _image;
	public Bouton(GameContainer container, Image image, Image mouseOverImage, int x, int y, int width, int height) {
		super(container, image, x, y, width, height);
		setMouseOverImage(mouseOverImage);
	}
	
	public void update(GameContainer container, int delta) {
	}
}
