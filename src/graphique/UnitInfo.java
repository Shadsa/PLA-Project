package graphique;

import roles.Automate;
import roles.classe.Classe;

public class UnitInfo {

  public String nom, choixJoueur;
  public TypeUnit color;
  public Automate automate;
  public Automate automatec;
  public Classe classe;
  public TypeClothes clothes;

  public UnitInfo(String nom, Automate aut, Automate autc, Classe classe, TypeUnit color, TypeClothes clothes, String choixJoueur){
	  this.nom = nom;
	  automate = aut;
	  automatec = autc;
	  this.color = color;
	  this.clothes = clothes;
	  this.classe = classe;
	  this.choixJoueur = choixJoueur;
  }
}