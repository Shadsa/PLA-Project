package graphique;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import XML.XML_Reader;
import roles.Automate;
import roles.Bonus;
import roles.World;
import roles.classe.Classe;

public class UnitDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private UnitInfo uInfo;

	private Automate aut = null;
	private Classe cla = null;
	private String choixJoueur;

  public UnitDialog(JFrame parent, String title, boolean modal){
    super(parent, title, modal);
    this.setSize(850, 120);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    this.setAlwaysOnTop(true);
	initComponent();
	uInfo = null;
  }

  public UnitInfo showZDialog(){
    this.setVisible(true);
    return this.uInfo;
  }

  private void initComponent(){
		JLabel icon, iconH;
		JTextField nom;
    //Icône
	ImageIcon temp = new ImageIcon("src/asset/sprites/BODY_male.png");
	Image image = temp.getImage();
	image = createImage(new FilteredImageSource(image.getSource(),
	            new CropImageFilter(0, 128, 64, 64)));
	
	ImageIcon tempH = new ImageIcon("src/asset/sprites/cult_clothes.png");
	Image imageH = tempH.getImage();
	imageH = createImage(new FilteredImageSource(imageH.getSource(),
	            new CropImageFilter(0, 128, 64, 64)));
	/*
	BufferedImage imageF = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = imageF.createGraphics();
	g2.drawImage(image, 0, 0, null);
	g2.drawImage(imageH, 0, 0, null);
	g2.dispose();
	ImageIcon newImg = new ImageIcon(imageF);*/

    icon = new JLabel(new ImageIcon(image));
    icon.setOpaque(false);
    iconH = new JLabel(new ImageIcon(imageH));
    iconH.setOpaque(false);
    JLayeredPane panIcon = new JLayeredPane();
    panIcon.setPreferredSize(new Dimension(64,64));
    //panIcon.setLayout(new BorderLayout());
    icon.setBounds(10, 0, 64, 64);
    iconH.setBounds(10, 0, 64, 64);
    panIcon.add(icon,JLayeredPane.DEFAULT_LAYER);
    panIcon.add(iconH,JLayeredPane.PALETTE_LAYER);

    //Le nom
    JPanel panNom = new JPanel();
    nom = new JTextField();
    nom.setPreferredSize(new Dimension(100, 25));
    panNom.setBorder(BorderFactory.createTitledBorder("Nom Unité"));
    panNom.add(nom);

    //L'automate
    Pattern p = Pattern.compile("(.)*.xml");
    String [] s = new File("./creation_automates").list();
    List<String> listeFichiers = new ArrayList<String>();
    for (int i=0; i<s.length;i++)
    {
    Matcher m = p.matcher(s[i]);
    if ( m.matches())
    	listeFichiers.add(s[i]);
    }

    JPanel panFile = new JPanel();
    panFile.setBorder(BorderFactory.createTitledBorder("Automate"));
    JComboBox<String> fichier = new JComboBox<String>();
    for(String f : listeFichiers)
    	fichier.addItem(f);
    panFile.add(fichier);

    JPanel panClasse = new JPanel();
    panClasse.setBorder(BorderFactory.createTitledBorder("Classe"));
    JComboBox<Classe> classe = new JComboBox<Classe>();
    for(Classe cla : StateGame.workshop.classeList())
    	classe.addItem(cla);
    panClasse.add(classe);

    JPanel panSkin = new JPanel();
    panSkin.setBorder(BorderFactory.createTitledBorder("Apparence"));
    JComboBox<String> color = new JComboBox<String>();
    for(TypeUnit t : TypeUnit.values()){
    	color.addItem(t.toString());
    }
   /* color.addItem("Noir");
    color.addItem("Vert");*/
    panSkin.add(color);
    
    JPanel panClothes = new JPanel();
    panClothes.setBorder(BorderFactory.createTitledBorder("Habits"));
    JComboBox<String> clothes = new JComboBox<String>();
    for(TypeClothes t : TypeClothes.values()){
    	clothes.addItem(t.toString());
    }
    panClothes.add(clothes);

    JPanel panJoueur = new JPanel();
    panJoueur.setBorder(BorderFactory.createTitledBorder("Joueur"));
    JComboBox<String> joueur = new JComboBox<String>();
    joueur.addItem("Joueur1");
    joueur.addItem("Joueur2");
    panJoueur.add(joueur);
    
    JPanel content = new JPanel();
    content.setLayout(new FlowLayout());
    content.add(panNom);
    content.add(panFile);
    content.add(panClasse);
    content.add(panSkin);
    content.add(panClothes);
    content.add(panJoueur);

    JPanel control = new JPanel();
    JButton okBouton = new JButton("OK");

    okBouton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
    	  if(aut == null)
    		  getAutomate((String)fichier.getSelectedItem());
    	  if(aut == null)
    		  return;
    	  if(nom.getText().equals(""))
    		  return;
    	  if(cla == null)
    		  cla = (Classe)classe.getSelectedItem();//getClasse((String)classe.getSelectedItem());
    	  if(choixJoueur == null)
    		  choixJoueur = (String)joueur.getSelectedItem();
    	  /*if(!aut.match(World.classes.get(0)))
    	  {
    		  JOptionPane jop = new JOptionPane();
    		  jop.showMessageDialog(null, "Fichier automate ne convient pas à cette classe.", "Erreur", JOptionPane.ERROR_MESSAGE);
    		  return;
    	  }*/
        uInfo = new UnitInfo(nom.getText(), aut, cla,TypeUnit.valueOf(color.getSelectedItem().toString()), TypeClothes.valueOf(clothes.getSelectedItem().toString()), choixJoueur);
        setVisible(false);
      }
    });

    fichier.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {
        	getAutomate((String)fichier.getSelectedItem());
          }
        });

    classe.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {
        	cla = (Classe)classe.getSelectedItem();//getClasse((String)classe.getSelectedItem());
          }
        });
    
    joueur.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {
        	choixJoueur = (String)joueur.getSelectedItem();//getClasse((String)classe.getSelectedItem());
          }
        });

    color.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
			ImageIcon typeCorps = new ImageIcon(TypeUnit.valueOf(color.getSelectedItem().toString()).sprite());
			
			Image image = createImage(new FilteredImageSource(
					typeCorps.getImage().getSource(),
					new CropImageFilter(0, 128, 64, 64)));
			
			icon.setIcon(new ImageIcon(image));
        }               
      });
    
    clothes.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
			ImageIcon typeHabit = new ImageIcon(TypeClothes.valueOf(clothes.getSelectedItem().toString()).sprite());
			
			Image imageH = createImage(new FilteredImageSource(
					typeHabit.getImage().getSource(),
					new CropImageFilter(0, 128, 64, 64)));
			
			iconH.setIcon(new ImageIcon(imageH));
        }               
      });


    JButton cancelBouton = new JButton("Annuler");
    cancelBouton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        setVisible(false);
      }
    });

    control.add(okBouton);
    control.add(cancelBouton);

    this.getContentPane().add(panIcon, BorderLayout.WEST);
    this.getContentPane().add(content, BorderLayout.CENTER);
    this.getContentPane().add(control, BorderLayout.SOUTH);
  }

  void getAutomate(String nom)
  {
	  File file = new File("./creation_automates/" + nom);
      try {
          File f = new File(file.getAbsolutePath());
			aut = XML_Reader.readXML(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(aut == null ){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Fichier automate invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
  }

  /*void getClasse(String nom)
  {
	  cla = null;
	  for(Classe cl : World.classes)
		  if(nom.contentEquals(cl.toString()))
			  cla = cl;
	  if(aut != null && !aut.match(cla))
		  cla = null;

		if(cla == null){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "La classe ne correspond pas à l'automate.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
  }*/
}