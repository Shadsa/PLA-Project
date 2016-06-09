

package roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import roles.classe.*;
import cases.Batiment;
import cases.CaseAction;
import roles.action.Action;
import roles.conditions.Condition;

public class Automate {
	ArrayList<ArrayList<CaseAction>> _action;
	ArrayList<ArrayList<Condition>> _condition;
	ArrayList<ArrayList<Integer>> _next;

	public Automate(int nb_etat)
	{
		_action = new ArrayList<ArrayList<CaseAction>>();
		_condition = new ArrayList<ArrayList<Condition>>();
		_next = new ArrayList<ArrayList<Integer>>();
		while(nb_etat>0)
		{
			_action.add(new ArrayList<CaseAction>());
			_condition.add(new ArrayList<Condition>());
			_next.add(new ArrayList<Integer>());
			nb_etat--;
		}
	}


	public ArrayList<ArrayList<CaseAction>> get_action() {
		return _action;
	}

	
	public void ajoute_transition(int etat, Action a, Condition c, int etat_suivant)
	{
		_action.get(etat).add(new CaseAction(new Batiment(a)));
		_condition.get(etat).add(c);
		_next.get(etat).add(etat_suivant);
	}

	public void agir(Personnage pers) {
		ArrayList<Integer> choice = new ArrayList<Integer>();
		for(int id = _condition.get(pers.etat()).size()-1; id >= 0; id--)
			if(_condition.get(pers.etat()).get(id).value(pers))
				choice.add(id);

		if(choice.size() == 0)
		{
			pers.parralyse();
			return;
		}

		Collections.sort(choice, (new Comparator<Integer>() {
			int _etat;
			@Override
			public int compare(Integer i1, Integer i2)
		    {
				return  _action.get(_etat).get(i1).poids() - _action.get(_etat).get(i2).poids();
		    }

			Comparator<Integer> init(int etat)
			{
				_etat = etat;
				return this;
			}
		}).init(pers.etat()));

		int poids = _action.get(pers.etat()).get(choice.get(choice.size()-1)).poids();
		/*for(Integer id : choice)
		{
			if(poids != _action.get(pers.etat()).get(id).poids())
				choice.remove(id);
		}*/
		for(int id = choice.size()-1; id>=0; id--){
			if(poids != _action.get(pers.etat()).get(choice.get(id)).poids())
				choice.remove(id);
		}

		Collections.shuffle(choice);
		_action.get(pers.etat()).get(choice.get(0)).Act(pers);
		pers.setetat(_next.get(pers.etat()).get(choice.get(0)));
	}
	
	public boolean match(Classe classe){
		return true;
	}
}
