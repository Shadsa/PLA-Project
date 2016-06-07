package workshop;

import java.util.ArrayList;
import roles.*;
import roles.action.Action;
import roles.conditions.Condition;

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
