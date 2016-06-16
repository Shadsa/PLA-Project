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
	protected Image normalImage;
	protected Image overImage;
	protected Image downImage;
	protected boolean pressed;
	protected boolean overX;
	protected boolean Xpressed;
	protected boolean prerendu = false;
	protected Rectangle hitbox;
	protected Rectangle Xhitbox;

	public CrossButton(GUIContext container, String text, int x, int y) throws SlickException
	{
		super(container);
	    ttf = new UnicodeFont("src/asset/fonts/Berry Rotunda.ttf", 10, false, false);
	    ttf.addAsciiGlyphs();
	    ttf.getEffects().add(new ColorEffect());
	    ttf.loadGlyphs();


	    _text = text;
	    width = ttf.getWidth(_text) + 47;
	    height = ttf.getHeight(_text) + 22;
	    setLocation(x, y);
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException
	{
		g.setFont(ttf);
		/*InitGameState.UI.draw(x, y, x+31, y+height, 650, 228, 681, 251);
		InitGameState.UI.draw(x+31, y, x+width-23, y+height, 683, 228, 714, 251);
		InitGameState.UI.draw(x+width-23, y, x+width, y+height, 717, 228, 731, 251);*/ 
		if(!prerendu) {
			//Prérendu normalImage
			normalImage = new Image(container.getScreenWidth(),container.getScreenHeight());
			Graphics g1 = normalImage.getGraphics();
			chargementBouton1(g1);
			g1.flush();
			normalImage = normalImage.getSubImage(x, y, 200, 100);
			
			//Prérendu overImage
			overImage = new Image(container.getScreenWidth(),container.getScreenHeight());
			Graphics g2 = overImage.getGraphics();
			chargementBouton1Over(g2);
			g2.flush();
			overImage = overImage.getSubImage(x, y, 200, 100);
			
			prerendu = true;
		}
		if (isOverX()) {
			overImage.draw(x, y);
		} else {
			normalImage.draw(x, y);
		}

		ttf.drawString(x + 35, y + 16, _text, Color.black);
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

	public boolean isOverX() {
		return overX;
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
	    overX = Xhitbox.contains(mouseX, mouseY);

	    if (hitbox.contains(mouseX, mouseY) && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
	        pressed = true;
	    }else{
	        pressed = false;
	    }

	    if (overX && container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
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
	    Xhitbox = new Rectangle(x+5, y+6, 15, 7+height-10);
	}

	public void setText(String string) {
	    _text = string;
	    width = ttf.getWidth(_text) + 47;
	    height = ttf.getHeight(_text) + 20;
	    setLocation(x, y);
	}
	
	private void chargementBouton1(Graphics g) {
		//Haut gauche
		g.drawImage(InitGameState.UI, x, y, x+33, y+10, 590, 482, 623, 492);
		//Haut droite
		g.drawImage(InitGameState.UI, x+33+width-46, y, x+33+width-46+16, y+10, 657, 482, 673, 492);
		int i = 0, j = 0;
		for(i = 0; i < height-15; i++) {
			//Milieu gauche
			g.drawImage(InitGameState.UI, x, y+10+i, x+33, y+10+i+2, 590, 493, 623, 495);
			//Milieu droite
			g.drawImage(InitGameState.UI, x+33+width-46, y+10+i, x+33+width-46+16, y+10+i+2, 657, 493, 673, 495);
			for(j = 0; j < width-45; j++) {
				if(i == 0) {
					//Haut milieu
					g.drawImage(InitGameState.UI, x+33+j, y, x+33+2+j, y+10, 624, 482, 626, 492);
					//Bas milieu
					g.drawImage(InitGameState.UI, x+33+j, y+11+height-16, x+33+2+j, y+11+height-6, 624, 500, 626, 510);
				}
				//Milieu milieu
				g.drawImage(InitGameState.UI, x+33+j, y+10+i, x+33+1+j, y+10+1+i, 624, 493, 625, 494);
			}
			}
		//Bas gauche
		g.drawImage(InitGameState.UI, x, y+10+(i), x+33, y+10+i+10, 590, 500, 623, 510);
		//Bas droite
		g.drawImage(InitGameState.UI, x+33+width-46, y+10+(i), x+33+width-46+16, y+10+i+10, 657, 500, 673, 510);
	}
	
	private void chargementBouton1Over(Graphics g) {
		//Haut gauche
		g.drawImage(InitGameState.UI, x, y, x+33, y+10, 590, 512, 623, 522);
		//Haut droite
		g.drawImage(InitGameState.UI, x+33+width-46, y, x+33+width-46+16, y+10, 657, 512, 673, 522);
		int i = 0, j = 0;
		for(i = 0; i < height-15; i++) {
			//Milieu gauche
			g.drawImage(InitGameState.UI, x, y+10+i, x+33, y+10+i+2, 590, 523, 623, 525);
			//Milieu droite
			g.drawImage(InitGameState.UI, x+33+width-46, y+10+i, x+33+width-46+16, y+10+i+2, 657, 523, 673, 525);
			for(j = 0; j < width-45; j++) {
				if(i == 0) {
					//Haut milieu
					g.drawImage(InitGameState.UI, x+33+j, y, x+33+2+j, y+10, 624, 512, 626, 522);
					//Bas milieu
					g.drawImage(InitGameState.UI, x+33+j, y+11+height-16, x+33+2+j, y+11+height-6, 624, 530, 626, 540);
				}
				//Milieu milieu
				g.drawImage(InitGameState.UI, x+33+j, y+10+i, x+33+1+j, y+10+1+i, 624, 523, 625, 524);
			}
			}
		//Bas gauche
		g.drawImage(InitGameState.UI, x, y+10+(i), x+33, y+10+i+10, 590, 530, 623, 540);
		//Bas droite
		g.drawImage(InitGameState.UI, x+33+width-46, y+10+(i), x+33+width-46+16, y+10+i+10, 657, 530, 673, 540);
	}
}
