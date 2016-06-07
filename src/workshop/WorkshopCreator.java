package workshop;

import java.util.ArrayList;
import roles.*;
import roles.action.Action;
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
