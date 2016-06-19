package graphique;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	protected boolean prerendu = false;
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
		setTrueTypeFont("src/asset/fonts/Berry Rotunda.ttf", 10);
	    _text = text;
	    width = ttf.getWidth(_text) + 22;
	    height = ttf.getHeight(_text) + 20;
	    setLocation(x, y);
	}
	
	/**
	 * Construit un bouton non extensible, c'est à dire un bouton avec une image fixée.
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
			if (isOver()) {
				overImage.draw(x, y);
			} else {
				normalImage.draw(x, y);
			}
			ttf.drawString(x + 12, y + 15, _text, Color.black);
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
	@SuppressWarnings("unchecked")
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
	
	private void chargementBouton1(Graphics g) {
		//Haut gauche
		g.drawImage(InitGameState.UI, x, y, x+24, y+9, 590, 424, 614, 433);
		//Haut droite
		g.drawImage(InitGameState.UI, x+24+width-46, y, x+24+width-46+25, y+10, 640, 424, 665, 434);
		int i = 0, j = 0;
		for(i = 0; i < height-15; i++) {
			//Milieu gauche
			g.drawImage(InitGameState.UI, x, y+9+i, x+24, y+9+i+1, 590, 435, 614, 436);
			//Milieu droite
			g.drawImage(InitGameState.UI, x+24+width-46, y+9+i, x+24+width-46+26, y+9+i+1, 640, 435, 666, 441);
			for(j = 0; j < width-45; j++) {
				if(i == 0) {
					//Haut milieu
					g.drawImage(InitGameState.UI, x+24+j, y, x+24+1+j, y+9, 616, 424, 617, 433);
					//Bas milieu
					g.drawImage(InitGameState.UI, x+24+j, y+10+height-16, x+24+1+j, y+10+height-6, 616, 442, 639, 452);
				}
				//Milieu milieu
				g.drawImage(InitGameState.UI, x+24+j, y+9+i, x+24+1+j, y+9+1+i, 616, 435, 617, 436);
			}
			}
		//Bas gauche
		g.drawImage(InitGameState.UI, x, y+9+(i), x+24, y+9+i+10, 590, 442, 614, 452);
		//Bas droite
		g.drawImage(InitGameState.UI, x+24+width-46, y+9+(i), x+24+width-46+26, y+9+i+10, 640, 442, 666, 452);
	}
	
	private void chargementBouton1Over(Graphics g) {
		//Haut gauche
		g.drawImage(InitGameState.UI, x, y, x+24, y+9, 590, 453, 614, 462);
		//Haut droite
		g.drawImage(InitGameState.UI, x+24+width-46, y, x+24+width-46+25, y+10, 640, 453, 665, 463);
		int i = 0, j = 0;
		for(i = 0; i < height-15; i++) {
			//Milieu gauche
			g.drawImage(InitGameState.UI, x, y+9+i, x+24, y+9+i+1, 590, 464, 614, 465);
			//Milieu droite
			g.drawImage(InitGameState.UI, x+24+width-46, y+9+i, x+24+width-46+26, y+9+i+1, 640, 464, 666, 471);
			for(j = 0; j < width-45; j++) {
				if(i == 0) {
					//Haut milieu
					g.drawImage(InitGameState.UI, x+24+j, y, x+24+1+j, y+9, 616, 453, 617, 462);
					//Bas milieu
					g.drawImage(InitGameState.UI, x+24+j, y+10+height-16, x+24+1+j, y+10+height-6, 616, 471, 639, 481);
				}
				//Milieu milieu
				g.drawImage(InitGameState.UI, x+24+j, y+9+i, x+24+1+j, y+9+1+i, 616, 464, 617, 465);
			}
			}
		//Bas gauche
		g.drawImage(InitGameState.UI, x, y+9+(i), x+24, y+9+i+10, 590, 471, 614, 481);
		//Bas droite
		g.drawImage(InitGameState.UI, x+24+width-46, y+9+(i), x+24+width-46+26, y+9+i+10, 640, 471, 666, 481);
	}
}
