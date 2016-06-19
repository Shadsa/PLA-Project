package graphique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.MouseOverArea;

public class Bouton extends MouseOverArea {
  
	//private Image _image;
	public Bouton(GameContainer container, Image image, Image mouseOverImage, int x, int y, int width, int height) {
		super(container, image, x, y, width, height);
		setMouseOverImage(mouseOverImage);
	}
	
	public void update(GameContainer container, int delta) {
	}
	
	public boolean isMouseButtonDownOnArea(Input input, int button) {
		return super.isMouseOver() && input.isMouseButtonDown(button);
	}
	
	public boolean isMouseButtonPressedOnArea(Input input, int button) {
		return super.isMouseOver() && input.isMousePressed(button);
	}
}
