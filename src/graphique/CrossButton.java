package graphique;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class CrossButton extends AbstractComponent {

	static Font Friedolin;
	static Font Rotunda;

	protected Font font;
	protected UnicodeFont ttf;
	protected String _text;
	public int x;
	public int y;
	protected int width;
	protected int height;
	protected boolean pressed;
	protected boolean Xpressed;
	protected Rectangle hitbox;
	protected Rectangle Xhitbox;

	public CrossButton(GUIContext container, String text, int x, int y) throws SlickException
	{
		super(container);
		//font = Rotunda.deriveFont(Font.TRUETYPE_FONT, 12);
		//font = new Font("src/asset/fonts/Friedolin.ttf", Font.PLAIN, 12);
	    ttf = new UnicodeFont("src/asset/fonts/Berry Rotunda.ttf", 12, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();


	    _text = text;
	    width = ttf.getWidth(_text) + 47;
	    height = ttf.getHeight(_text) + 20;
	    setLocation(x, y);
	}

	/*@Override
	public void render(GUIContext container, Graphics g) throws SlickException
	{
		g.setFont(ttf);
		InitGameState.UI.draw(x, y, x+23, y+height, 634, 118, 657, 141);
		InitGameState.UI.draw(x+23, y, x+width-23, y+height, 659, 118, 681, 141);
		InitGameState.UI.draw(x+width-23, y, x+width, y+height, 683, 118, 707, 141);

		ttf.drawString(x + 12, y + 13, _text, Color.black);
	}*/

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException
	{
		g.setFont(ttf);
		InitGameState.UI.draw(x, y, x+31, y+height, 650, 228, 681, 251);
		InitGameState.UI.draw(x+31, y, x+width-23, y+height, 683, 228, 714, 251);
		InitGameState.UI.draw(x+width-23, y, x+width, y+height, 717, 228, 731, 251);

		ttf.drawString(x + 33, y + 13, _text, Color.black);
	}

	public static void init()
	{
		try {
			Friedolin = Font.createFont(Font.TRUETYPE_FONT, new File("src/asset/fonts/Friedolin.ttf"));
			Rotunda = Font.createFont(Font.TRUETYPE_FONT, new File("src/asset/fonts/Berry Rotunda.ttf"));
		} catch (FontFormatException | IOException e) {
			Friedolin = new Font("Verdana", Font.PLAIN, 12);
			Rotunda = new Font("Verdana", Font.PLAIN, 12);
			e.printStackTrace();
		}
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

	public boolean isPressed() {
	    return pressed;
	}

	public boolean isXPressed() {
	    return Xpressed;
	}
	public void update(GUIContext container) {
	    float mouseX = container.getInput().getMouseX();
	    float mouseY = container.getInput().getMouseY();

	    if (hitbox.contains(mouseX, mouseY) && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
	        pressed = true;
	    }else{
	        pressed = false;
	    }

	    if (Xhitbox.contains(mouseX, mouseY) && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
	        Xpressed = true;
	    }else{
	        Xpressed = false;
	    }
	}

	@Override
	public void setLocation(int x, int y) {
	    this.x = x;
	    this.y = y;
	    hitbox = new Rectangle(x+23, y, width, height);
	    Xhitbox = new Rectangle(x, y, 23, 23);
	}

	public void setText(String string) {
	    _text = string;
	    width = ttf.getWidth(_text) + 47;
	    height = ttf.getHeight(_text) + 20;
	    setLocation(x, y);
	}
}
