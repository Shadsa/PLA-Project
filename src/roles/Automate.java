

package roles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import roles.action.Action;
import roles.conditions.Condition;

public class Automate {
	ArrayList<ArrayList<Action>> _action;
	ArrayList<ArrayList<Condition>> _condition;
	ArrayList<ArrayList<Integer>> _next;
	int _etat;

	public Automate(int nb_etat)
	{
		_etat = 0;
		_action = new ArrayList<ArrayList<Action>>();
		_condition = new ArrayList<ArrayList<Condition>>();
		_next = new ArrayList<ArrayList<Integer>>();
		while(nb_etat>0)
		{
			_action.add(new ArrayList<Action>());
			_condition.add(new ArrayList<Condition>());
			_next.add(new ArrayList<Integer>());
			nb_etat--;
		}
	}

	public void ajoute_transition(int etat, Action a, Condition c, int etat_suivant)
	{
		_action.get(etat).add(a);
		_condition.get(etat).add(c);
		_next.get(etat).add(etat_suivant);
	}

	public void agir(Personnage pers) {
		ArrayList<Integer> choice = new ArrayList<Integer>();
		for(int id = _condition.get(_etat).size()-1; id >= 0; id--)
			if(_condition.get(_etat).get(id).value(pers))
				choice.add(id);

		if(choice.size() == 0)
		{
			pers.parralyse();
			return;
		}

		Collections.sort(choice, new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2)
		    {
				return  _action.get(_etat).get(i1).poids() - _action.get(_etat).get(i2).poids();
		    }
		});

		int poids = _action.get(_etat).get(choice.get(choice.size()-1)).poids();
		/*for(Integer id : choice)
		{
			if(poids != _action.get(_etat).get(id).poids())
				choice.remove(id);
		}*/
		for(int id = choice.size()-1; id>=0; id--){
			if(poids != _action.get(_etat).get(choice.get(id)).poids())
				choice.remove(id);
		}

		Collections.shuffle(choice);
		_action.get(_etat).get(choice.get(0)).Act(pers);
		_etat = _next.get(_etat).get(choice.get(0));
	}
}
