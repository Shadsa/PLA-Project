package graphique;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Button {

	static Font Friedolin;
	static Font Rotunda;

	private Font font;
	private UnicodeFont ttf;
	private String _text;
	public int x;
	public int y;
	private int width;
	private int height;

	public Button(String text, int x, int y) throws SlickException
	{
		//font = Rotunda.deriveFont(Font.TRUETYPE_FONT, 12);
		//font = new Font("src/asset/fonts/Friedolin.ttf", Font.PLAIN, 12);
	    ttf = new UnicodeFont("src/asset/fonts/Berry Rotunda.ttf", 12, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();

	    
	    _text = text;
	    this.x = x;
	    this.y = y;
	    width = ttf.getWidth(_text) + 20;
	    height = ttf.getHeight(_text) + 16;
	}

	public void render(Graphics g)
	{
		g.setFont(ttf);
		InitGameState.UI.draw(x, y, x+23, y+height, 634, 118, 657, 141);
		InitGameState.UI.draw(x+23, y, x+width-23, y+height, 659, 118, 681, 141);
		InitGameState.UI.draw(x+width-23, y, x+width, y+height, 683, 118, 707, 141);

		ttf.drawString(x + 10, y + 11, _text, Color.black);
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
}
