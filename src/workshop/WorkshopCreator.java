package workshop;

import java.util.ArrayList;
import roles.*;
import roles.action.*;
import roles.conditions.Condition;
import java.io.File;

public class WorkshopCreator {
	
	private String filepath = "../../Workshop/";
	private ArrayList<String> deckActionName;
	private ArrayList<String> deckConditionName;	
	private ArrayList<ArrayList<Action>> deckAction;
	private ArrayList<ArrayList<Condition>> deckCondition;
	
	public WorkshopCreator(){
		deckAction = new ArrayList<ArrayList<Action>>();
		deckCondition = new ArrayList<ArrayList<Condition>>();
		deckActionName = new ArrayList<String>();
		deckConditionName = new ArrayList<String>();
		load();
	}
	
	public void load(){
		// WorkShop Action
		File f = new File(filepath+"/action.deck/");
		//initialisation de la liste
		ArrayList<File> files=new ArrayList<File>();
		for (File file : f.listFiles()){
			files.add(file);
		// création d'une ArrayList regroupant tous les fichiers
		}
		long taille = files.size(); // Nombre de fichiers total dans le dossier
		for (int i=0;i< taille;i++){
			files.get(i); // me donne le fichier
			deckActionName.add(files.get(i).getName());
			deckAction.add(new ArrayList<Action>()); //initialisation d'un arraylist
			while (){ //tant que ce n'est pas la fin du fichier
				//Lire le fichier ligne par ligne
				²
						
				}
			}
			//deckAction.get(i);
		}

		// WorkShop Condition
	}
	
	
	public ArrayList<Action> getDeckAction(String name){
		for(int i=0;i<deckActionName.size();i++){
			if(deckActionName.get(i)==name){
				return deckAction.get(deckActionName.indexOf(deckActionName.get(i)));
			}
		}
		return null;
	}
	
	public ArrayList<Condition> getDeckCondition(String name){
		for(int i=0;i<deckConditionName.size();i++){
			if(deckConditionName.get(i)==name){
				return deckCondition.get(deckConditionName.indexOf(deckConditionName.get(i)));
			}
		}
		return null;
	}
	
	public ArrayList<ArrayList<Action>> actionList(){
		return deckAction;
	}
	
	public ArrayList<ArrayList<Condition>> conditionList(){
		return deckCondition;
	}
}
