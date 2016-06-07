package roles.classe;

import java.util.ArrayList;

import roles.action.*;
import roles.conditions.*;

public class Classe {
	
	private int _HP;
	private int _damage;
	private int _armor;
	private String _name;
	private String _bonus;
	private ArrayList<Action> avaibleAction;
	private ArrayList<Condition> avaibleCondition;
	
	
	public Classe(int HP, int damage, int armor, String name, String bonus){
		_HP = HP; _damage=damage; _armor=armor; _name= name; _bonus=bonus;
	}
	
	public int HP(){
		return _HP;
	}
	
	public int armor(){
		return _armor;
	}
	
	public int damage(){
		return _damage;
	}
	
	
	public String name(){
		return _name;
	}
	
	public void addAction(Action act){
		avaibleAction.add(act);
	}
	
	public void addCondition(Condition cond){
		avaibleCondition.add(cond);
	}

}
