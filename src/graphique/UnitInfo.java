package graphique;

import roles.Automate;
import roles.classe.Classe;

public class UnitInfo {
  public String nom, color, choixJoueur;
  public Automate automate;
  public Classe classe;

  public UnitInfo(String nom, Automate aut, Classe classe, String color, String choixJoueur){
	  this.nom = nom;
	  automate = aut;
	  this.color = color;
	  this.classe = classe;
	  this.choixJoueur = choixJoueur;
  }
}