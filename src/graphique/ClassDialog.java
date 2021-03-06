package graphique;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import XML.XML_Reader;
import roles.Automate;
import roles.Bonus;
import roles.World;
import roles.classe.Classe;
import workshop.WorkshopCreator;


public class ClassDialog extends JDialog {

	private Classe classinfo;

	public ClassDialog(JFrame parent, String title, boolean modal){
	    super(parent, title, modal);
	    this.setSize(850, 180);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    classinfo = new Classe(0,0,0,0,"default", true,Bonus.NONE);
		initComponent();
	  }


	  private void initComponent(){
			JLabel icon;
			JTextField nom,HP,Heal,Damage,Armor,Cost;
	    //Icône
		ImageIcon temp = new ImageIcon("src/asset/sprites/BODY_male.png");
		Image image = temp.getImage();
		image = createImage(new FilteredImageSource(image.getSource(),
		            new CropImageFilter(0, 128, 64, 64)));

	    icon = new JLabel(new ImageIcon(image));
	    JPanel panIcon = new JPanel();
	    panIcon.setLayout(new BorderLayout());
	    panIcon.add(icon);

	    //Le nom
	    JPanel panNom = new JPanel();
	    nom = new JTextField();
	    nom.setPreferredSize(new Dimension(100, 25));
	    panNom.setBorder(BorderFactory.createTitledBorder("Nom Classe"));
	    panNom.add(nom);
	    
	    //AJOUT DES RESTRICTIONS
	    JPanel panAct = new JPanel();
	    panAct.setBorder(BorderFactory.createTitledBorder("Deck Action"));
	    JComboBox<String> action = new JComboBox<String>();
	    
	    for(String b : StateGame.workshop.actionListName())
	    	action.addItem(b);
	    panAct.add(action);
	    
	    JPanel panCond = new JPanel();
	    panCond.setBorder(BorderFactory.createTitledBorder("Deck Conditon"));
	    JComboBox<String> condition = new JComboBox<String>();	    
	    for(String b : StateGame.workshop.conditionListName())
	    	condition.addItem(b);
	    panCond.add(condition);
	    


	    //AJOUT DES ATTRIBUTS
	    JPanel panHP = new JPanel();
	    panHP.setBorder(BorderFactory.createTitledBorder("Santé"));
	    HP = new JTextField();
	    HP.setPreferredSize(new Dimension(50, 25));
	    panHP.add(HP);

	    JPanel panDamage = new JPanel();
	    panDamage.setBorder(BorderFactory.createTitledBorder("Attaque"));
	    Damage = new JTextField();
	    Damage.setPreferredSize(new Dimension(75, 25));
	    panDamage.add(Damage);

	    JPanel panHeal = new JPanel();
	    panHeal.setBorder(BorderFactory.createTitledBorder("Puissance de soin"));
	    Heal = new JTextField();
	    Heal.setPreferredSize(new Dimension(150, 25));
	    panHeal.add(Heal);

	    JPanel panArmor = new JPanel();
	    panArmor.setBorder(BorderFactory.createTitledBorder("Armure"));
	    Armor = new JTextField();
	    Armor.setPreferredSize(new Dimension(75, 25));
	    panArmor.add(Armor);

	    JPanel panBonus = new JPanel();
	    panBonus.setBorder(BorderFactory.createTitledBorder("Bonus"));
	    JComboBox<Bonus> bonus = new JComboBox<Bonus>();

	    for(Bonus b : Bonus.values())
	    	bonus.addItem(b);
	    panBonus.add(bonus);
	    
	    JPanel panWalker = new JPanel();
	    panWalker.setBorder(BorderFactory.createTitledBorder("Traverse arbre"));
	    JComboBox<String> walker = new JComboBox<String>();
	    walker.addItem("oui");
	    walker.addItem("non");
	    panWalker.setPreferredSize(new Dimension(150, 55));
	    panWalker.add(walker);

	    JPanel panCost = new JPanel();
	    panCost.setBorder(BorderFactory.createTitledBorder("Coût unité"));
	    Cost = new JTextField();
	    Cost.setPreferredSize(new Dimension(100, 25));
	    Cost.setEditable(false);
	    panCost.add(Cost);

	    JPanel content = new JPanel();
	    content.setLayout(new FlowLayout());
	    content.add(panNom);
	    content.add(panAct);
	    content.add(panCond);
	    content.add(panHP);
	    content.add(panDamage);
	    content.add(panHeal);
	    content.add(panArmor);
	    content.add(panBonus);
	    content.add(panWalker);
	    content.add(panCost);



	    JPanel control = new JPanel();
	    JButton okBouton = new JButton("OK");
	    JButton cost = new JButton("Coût");

	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  	cost.doClick();
	    	  	try {
					StateGame.workshop.saveClass(classinfo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	  	
	      }
	    });

	    cost.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {
		    	  classinfo.name(nom.getText());
		    	  classinfo.armor(Integer.parseInt(Armor.getText()));
		    	  classinfo.heal(Integer.parseInt(Heal.getText()));
		    	  classinfo.HP(Integer.parseInt(HP.getText()));
		    	  classinfo.damage(Integer.parseInt(Damage.getText()));
		    	  classinfo.bonus((Bonus) bonus.getSelectedItem());
		    	  classinfo.loadDeckAction(StateGame.workshop.getDeckAction(action.getSelectedItem().toString()));
		    	  classinfo.loadDeckCondition(StateGame.workshop.getDeckCondition(condition.getSelectedItem().toString()));	 
		    	  if(walker.getSelectedItem().toString().equalsIgnoreCase("oui")){
		    		  classinfo.hard_walker(true);
		    	  }else{
		    		  classinfo.hard_walker(false);
		    	  }
		    	  Cost.setText(String.valueOf(classinfo.cost()));
		    	  setVisible(false);
		      }
		    });


	    JButton cancelBouton = new JButton("Annuler");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	        setVisible(false);
	      }
	    });
	    
	    control.add(cost);
	    control.add(okBouton);
	    control.add(cancelBouton);

	    this.getContentPane().add(panIcon, BorderLayout.WEST);
	    this.getContentPane().add(content, BorderLayout.CENTER);
	    this.getContentPane().add(control, BorderLayout.SOUTH);

	  }

}
