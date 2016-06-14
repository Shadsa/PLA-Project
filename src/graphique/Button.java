package graphique;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class Button extends AbstractComponent {

	/*static Font Friedolin;
	static Font Rotunda;*/

	protected Font font;
	protected UnicodeFont ttf;
	protected String _text;
	public int x;
	public int y;
	protected int xText;
	protected int yText;
	protected Image normalImage;
	protected Image overImage;
	protected Image downImage;
	protected boolean extensible = true;
	protected int width;
	protected int height;
	protected boolean down;
	protected boolean pressed;
	protected boolean over;
	protected Rectangle hitbox;

	/**
	 * Construit un bouton extensible.
	 * @param container
	 * @param text
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Button(GUIContext container, String text, int x, int y) throws SlickException
	{
		super(container);
		//font = Rotunda.deriveFont(Font.TRUETYPE_FONT, 12);
		//font = new Font("src/asset/fonts/Friedolin.ttf", Font.PLAIN, 12);
		setTrueTypeFont("src/asset/fonts/Berry Rotunda.ttf", 10);
	    _text = text;
	    width = ttf.getWidth(_text) + 22;
	    height = ttf.getHeight(_text) + 20;
	    setLocation(x, y);
	}
	
	/**
	 * Construit un bouton non extensible.
	 * @param container
	 * @param text
	 * @param x
	 * @param y
	 * @param extensible
	 * @param normalImage
	 * @param overImage
	 * @param downImage
	 * @throws SlickException
	 */
	public Button(GUIContext container, String text, int x, int y, Image normalImage, Image overImage, Image downImage) throws SlickException {
		super(container);
		this.extensible = false;
		setTrueTypeFont("src/asset/fonts/Berry Rotunda.ttf", 10);
		this.normalImage = normalImage;
		this.overImage = overImage;
		this.downImage = downImage;
		_text = text;
	    width = normalImage.getWidth();
	    height = normalImage.getHeight();
	    setLocation(x, y);
	    xText = this.x + width/2 - ttf.getWidth(_text)/2;
	    yText = this.y + 10;
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException
	{
		g.setFont(ttf);
		if (extensible) { 
			InitGameState.UI.draw(x, y, x+23, y+height, 634, 118, 657, 141);
			InitGameState.UI.draw(x+23, y, x+width-23, y+height, 659, 118, 681, 141);
			InitGameState.UI.draw(x+width-23, y, x+width, y+height, 683, 118, 707, 141);
			ttf.drawString(x + 12, y + 13, _text, Color.black);
		} else {
			if (isDown()) {
				downImage.draw(x, y);
			} else if (isOver()) {
				overImage.draw(x, y);
			} else {
				normalImage.draw(x, y);
			}
			xText = this.x + width/2 - ttf.getWidth(_text)/2;
		    yText = this.y + 11;
			ttf.drawString(xText, yText, _text, Color.black);
		}
		
	}

	public static void init()
	{
/*		try {
			Friedolin = Font.createFont(Font.TRUETYPE_FONT, new File("src/asset/fonts/Friedolin.ttf"));
			Rotunda = Font.createFont(Font.TRUETYPE_FONT, new File("src/asset/fonts/Berry Rotunda.ttf"));
		} catch (FontFormatException | IOException e) {
			Friedolin = new Font("Verdana", Font.PLAIN, 12);
			Rotunda = new Font("Verdana", Font.PLAIN, 12);
			e.printStackTrace();
		}*/
	}
	
	/**
	 * Initialise la police importée ttf utilisée.
	 * @param chemin
	 * @param fontSize
	 * @throws SlickException
	 */
	public void setTrueTypeFont(String chemin, int fontSize) throws SlickException {
	    ttf = new UnicodeFont( chemin, fontSize, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();
	}

	/**
	 * Initialise le texte dans le bouton.
	 * @param text
	 */
	public void setText(String text) {
		this._text = text;
	}
	
	/**
	 * Initialise la localisation du texte.
	 * @param x
	 * @param y
	 */
	public void setTextLocation(int x, int y) {
		this.xText = x;
		this.yText = y;
	}
	
	@Override
	public int getHeight() {
	    return height;
	}

	@Override
	public int getWidth() {
	    return width;
	}

	@Override
	public int getX() {
	    return x;
	}

	@Override
	public int getY() {
	    return y;
	}
	
	/**
	 * Retourne si la souris est au dessus du bouton.
	 * @return Le booléen over du bouton.
	 */
	public boolean isOver() {
		return over;
	}

	/**
	 * Retourne si le bouton est appuyé.
	 * @return Le booléen down du bouton.
	 */
	public boolean isDown() {
		return down;
	}
	
	/**
	 * Retourne si le bouton est cliqué.
	 * @return Le booléen pressed du bouton.
	 */
	public boolean isPressed() {
	    return pressed;
	}
	public void update(GUIContext container) {
	    float mouseX = container.getInput().getMouseX();
	    float mouseY = container.getInput().getMouseY();
	    this.over = hitbox.contains(mouseX, mouseY);

	    if (over && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
	        pressed = true;
	    }else{
	        pressed = false;
	    }	    
	    
	    if (over && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
	        down = true;
	    }else{
	        down = false;
	    }
	}

	public void setLocation(int x, int y) {
	    this.x = x;
	    this.y = y;
	    hitbox = new Rectangle(x, y, width, height);
	}
}
