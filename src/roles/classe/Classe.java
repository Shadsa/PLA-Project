package roles.classe;

import java.util.ArrayList;

import roles.Bonus;
import roles.action.*;
import roles.conditions.*;

public class Classe {



	private int _HP;
	private int _damage;
	private int _heal;
	private int _armor;
	private String _name;
	private Bonus _bonus;
	private ArrayList<Action> avaibleAction;
	private ArrayList<Condition> avaibleCondition;


	public Classe(int HP, int damage, int heal, int armor, String name, Bonus bonus){
		_HP = HP; _damage=damage; _heal=heal; _armor=armor; _name= name; _bonus=bonus;
		modifier(bonus);
	}

	public Classe(int HP, int damage, int heal, int armor, String name, Bonus bonus, ArrayList<Action> act , ArrayList<Condition> cond){
		_HP = HP; _damage=damage; _heal=heal; _armor=armor; _name= name; _bonus=bonus; avaibleAction = act; avaibleCondition = cond;
		modifier(bonus);
	}

	public void modifier(Bonus x){
		if(x != null)
		switch(x){
			case FORCE :
				_damage += 10;
				break;
			case VIE :
				_HP+=10;
				break;
			case ARMURE:
				_armor+=10;
				break;
			case OPBUFF:
				_HP+=30;_damage+=30;_armor+=30;
				break;
		}

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
	
	public int heal(){
		return _heal;
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

	@Override
	public String toString()
	{
		return _name;
	}
}
