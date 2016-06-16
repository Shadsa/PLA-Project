package graphique;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.w3c.dom.DOMException;

import XML.XML_Reader;
import roles.Automate;
import roles.Bonus;
import roles.Joueur;
import roles.World;
import roles.classe.Classe;
import workshop.WorkshopCreator;

public class InitGameState extends BasicGameState {


	static Image UI;




	//Identifiant unique de la boucle de jeu
	public static final int ID = 4;
	//Image de fond
	private Image background;
	//Le contrôleur des phases de jeu
	private static StateGame game;
	private String sizeScreen;
	private Input _input;

	//Bouton
	private Button _bouton_jouer;
	private Button _bouton_quitter;

	private Button my_button;

	private ArrayList<CrossButton> Personnages;
	private ArrayList<UnitInfo> UIFs;
	private ArrayList<Automate> autlist;
	private ArrayList<Classe> classes;

	public WorkshopCreator woks;

	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		woks = new WorkshopCreator();
		woks.createClasse("Ouvrier", null, "Ouvrier", "Ouvrier");
		woks.createClasse("Default", null, "RandomNoIdea", "ClassTest");


		Button.init();
		_input = container.getInput();
		UI = new Image("src/asset/sprites/ui_big_pieces.png");
		UIFs = new ArrayList<UnitInfo>();
		autlist = new ArrayList<Automate>();
		classes = new ArrayList<Classe>();
		Classe generique = woks.getDeckClasse("Ouvrier");//= new Classe(10,5,5,0,"default class",null);
		Classe boost = woks.getDeckClasse("Default");//new Classe(10,5,5,0,"default class",Bonus.VIE);
  		World.classes.add(generique);
  		World.classes.add(boost);
		background = new Image("src/asset/images/skeleton_army.jpg");
		InitGameState.game = (StateGame) game;
		//_bouton_jouer = new Bouton(container, new Image("src/asset/buttons/bouton_jouer_off.png"), new Image("src/asset/buttons/bouton_jouer_on.png"), container.getWidth()/2-62, container.getHeight()/2-80+200, 126, 30);
		my_button = new Button(container, "Ajouter unité",container.getWidth()/4, container.getHeight()/4);
		_bouton_jouer = new Button(container, "Jouer", container.getWidth()*3/4, container.getHeight()*3/4);
		_bouton_quitter = new Button(container, "Quitter", my_button.x, _bouton_jouer.y);
		sizeScreen = "Taille de l'ecran : " + container.getScreenWidth() + "x" + container.getScreenHeight();
		Personnages = new ArrayList<CrossButton>();
		/*// Chargement d'une nouvelle police de caractères
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("src/asset/fonts/Friedolin.ttf");

			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(50f); // set font size
			font = new TrueTypeFont(awtFont, false);

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Contentons nous d'afficher l'image de fond.
	 * Le texte est placé approximativement au centre.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

		background.draw(0, 0, container.getWidth(), container.getHeight());
		renderMenu(my_button.x - 10, my_button.y - 10, _bouton_jouer.x - my_button.x + _bouton_jouer.width + 20, _bouton_jouer.y - my_button.y + _bouton_jouer.height + 20);
		my_button.render(container, g);
		for(CrossButton p : Personnages)
			p.render(container, g);
		_bouton_jouer.render(container, g);
		_bouton_quitter.render(container, g);
		g.setColor(Color.white);
		/*if (_input.getControllerCount() >= 1) {
			g.drawString("Appuyez sur START pour commencer.", 240, 300);
		} else {*/
		//}
	}

	/**
	 * Passer à l’écran de jeu à l'appui de n'importe quelle touche.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		my_button.update(container);
		_bouton_jouer.update(container);
		_bouton_quitter.update(container);



		if(my_button.isPressed())
		{
			UnitInfo uInfo = new UnitDialog(null, "Ajouter une unite", true).showZDialog();
			if(uInfo != null)
			{
				Personnages.add(new CrossButton(container, uInfo.nom, my_button.x+15, (Personnages.size() == 0)? my_button.y+my_button.height+7 : Personnages.get(Personnages.size()-1).y + Personnages.get(Personnages.size()-1).height+7));
				UIFs.add(uInfo);
				autlist.add(uInfo.automate);
				classes.add(uInfo.classe);
			}
		}

		for(int i = Personnages.size()-1; i>=0; i--)
		{
			Personnages.get(i).update(container);
			if(Personnages.get(i).isXPressed())
			{
				Personnages.remove(i);
				UIFs.remove(i);
				autlist.remove(i);
				classes.remove(i);
				for(int j = Personnages.size()-1; j>=i; j--)
				{
					Personnages.get(j).setLocation(my_button.x+15, (j == 0)? my_button.y+my_button.height+7 : Personnages.get(j).y + Personnages.get(j).height+7);
				}
			}
			else if(Personnages.get(i).isPressed())
			{
				/*JFileChooser jfc = new JFileChooser("./creation_automates");

				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		            File file = jfc.getSelectedFile();System.out.println(file.getAbsolutePath());
		            ArrayList<Automate> autlist2 = null;
		            try {
			            File f = new File(file.getAbsolutePath());
		    			autlist2 = XML_Reader.readXML(f);
		    		} catch (Exception e) {
		    			e.printStackTrace();
		    		}
		    		if(autlist2 == null ){
		    			//Boîte du message d'erreur
		    			JOptionPane jop = new JOptionPane();
		    			jop.showMessageDialog(null, "Fichier automate invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
		    		}
		    		else
		    		{
		    			Personnages.get(i).setText("Valide");
		    			autlist.addAll(autlist2);
		    		}
		        }*/
			}
		}


		//Configuration du bouton jouer
		if (_bouton_jouer.isPressed()) {
			((DragAndDropState)InitGameState.game.getState(DragAndDropState.ID)).setGame(UIFs);
				InitGameState.game.enterState(DragAndDropState.ID, "src/asset/musics/game_music.ogg");
		}

		//Configuration du bouton quitter
		if (_bouton_quitter.pressed) {
			container.exit();
		}


		/*if (_input.isKeyPressed(Input.KEY_A)){
			game.enterState(DragAndDropState.ID);
		}*/

		//Gestion des boutons en plein écran
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	}

	public void keyPressed(int key, char c) {
	}

	/**
	 * L'identifiant permet d'identifier les différentes boucles.
	 * Pour passer de l'une à l'autre.
	 */
	public int getID() {
		return ID;
	}

	public static void renderMenu(int x, int y, int width, int height)
	{
		//UI.startUse();
		//Corner tl
		UI.draw(x, y, 32+x, 32+y, 15, 40, 47, 71);
		// center
		UI.draw(32+x, y, x+width-32, y+32, 50, 40, 80, 71);
		//Corner tr
		UI.draw(x+width-32, y, x+width, y+32, 82, 40, 113, 71);

		//border left
		UI.draw(x, y+32, x+32, y+height-32, 477, 25, 509, 55);
		// center
		UI.draw(x+32, y+32, x+width-32, y+height-32, 512, 25, 542, 55);
		//border right
		UI.draw(x+width-32, y+32, x+width, y+height-32, 544, 25, 575, 55);

		//corner bl
		UI.draw(x, y+height-32, x+32, y+height, 477, 80, 509, 111);
		// center
		UI.draw(x+32, y+height-32, x+width-32, y+height, 512, 80, 542, 111);
		//corner br
		UI.draw(x+width-32, y+height-32, x+width, y+height, 544, 80, 575, 111);

		//UI.endUse();
	}
}


























