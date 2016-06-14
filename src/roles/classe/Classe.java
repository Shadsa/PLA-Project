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
	private ArrayList<Class<Action>> avaibleAction;
	private ArrayList<Class<Condition>> avaibleCondition;


	public Classe(int HP, int damage, int heal, int armor, String name, Bonus bonus){
		_HP = HP; _damage=damage; _heal=heal; _armor=armor; _name= name; _bonus=bonus;
		modifier(bonus);
		avaibleAction = new ArrayList<Class<Action>>();
		avaibleCondition = new ArrayList<Class<Condition>>();
	}

	public Classe(int HP, int damage, int heal, int armor, String name, Bonus bonus, ArrayList<Class<Action>> act , ArrayList<Class<Condition>> cond){
		_HP = HP; _damage=damage; _heal=heal; _armor=armor; _name= name; _bonus=bonus; avaibleAction = act; avaibleCondition = cond;
		modifier(bonus);
		avaibleAction = new ArrayList<Class<Action>>();
		avaibleCondition = new ArrayList<Class<Condition>>();
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

	public void addAction(Class act){
		avaibleAction.add(act);
	}

	public void addCondition(Class cond){
		avaibleCondition.add(cond);
	}

	public boolean isAction(Class act){
		for(int i=0;i<avaibleAction.size();i++){
			if(act == avaibleAction.get(i)){return true;}
		}
		return false;
	}

	public boolean isCondition(Class act){
		for(int i=0;i<avaibleCondition.size();i++){
			if(act == avaibleCondition.get(i)){return true;}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return _name;
	}
}
