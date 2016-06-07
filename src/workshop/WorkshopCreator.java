package workshop;

import java.util.ArrayList;
import roles.*;
import roles.action.Action;
import roles.conditions.Condition;

public class WorkshopCreator {
	
	private ArrayList<String> deckActionName;
	private ArrayList<String> deckConditionName;	
	private ArrayList<ArrayList<Action>> deckAction;
	private ArrayList<ArrayList<Condition>> deckCondition;
	
	public WorkshopCreator(){
		
	}
	
	public void load(){
		
	}
	
	public ArrayList<ArrayList<Action>> actionList(){
		return deckAction;
	}
	
	public ArrayList<ArrayList<Condition>> conditionList(){
		return deckCondition;
	}
}
