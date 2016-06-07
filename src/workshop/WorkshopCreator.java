package workshop;

import java.util.ArrayList;
import roles.*;
import roles.action.Action;
import roles.conditions.Condition;
import java.io.File;

public class WorkshopCreator {
	
	private ArrayList<String> deckActionName;
	private ArrayList<String> deckConditionName;	
	private ArrayList<ArrayList<Action>> deckAction;
	private ArrayList<ArrayList<Condition>> deckCondition;
	
	public WorkshopCreator(){
		
	}
	
	public void load(){
		File f = new File(filepath+"/action.deck/");
		//initialiser le tableau
		ArrayList<File> files=new ArrayList<File>();
		for (File file : f.listFiles()){
			files.add(file);
			// création d'un tableau regroupant tous les fichiers
		}
		int x = 0;
		for (int i = 0 ; i < files.length ; i++) {
		  if (f[i].isFile()) {
		    x++;
		  }
		}
		
	}
	
	public ArrayList<ArrayList<Action>> actionList(){
		return deckAction;
	}
	
	public ArrayList<ArrayList<Condition>> conditionList(){
		return deckCondition;
	}
}
