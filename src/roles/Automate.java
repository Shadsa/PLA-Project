

package roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import roles.classe.*;
import cases.Batiment;
import cases.CaseAction;
import roles.States.Statut;
import roles.action.Action;
import roles.conditions.Condition;

public class Automate implements Serializable{
	ArrayList<ArrayList<CaseAction>> _action;
	ArrayList<ArrayList<Condition>> _condition;
	ArrayList<ArrayList<Integer>> _poids;
	ArrayList<ArrayList<Integer>> _next;

	public Automate(int nb_etat)
	{
		_action = new ArrayList<ArrayList<CaseAction>>();
		_condition = new ArrayList<ArrayList<Condition>>();
		_poids = new ArrayList<ArrayList<Integer>>();
		_next = new ArrayList<ArrayList<Integer>>();
		while(nb_etat>0)
		{
			_action.add(new ArrayList<CaseAction>());
			_condition.add(new ArrayList<Condition>());
			_poids.add(new ArrayList<Integer>());
			_next.add(new ArrayList<Integer>());
			nb_etat--;
		}
	}


	public ArrayList<ArrayList<CaseAction>> get_action() {
		return _action;
	}


	public void ajoute_transition(int etat, Action a, Condition c, int etat_suivant, int poids)
	{
		_action.get(etat).add(new CaseAction(new Batiment(a)));
		_condition.get(etat).add(c);
		_poids.get(etat).add(poids);
		_next.get(etat).add(etat_suivant);
	}

	public void agir(Personnage pers) {
		ArrayList<Integer> choice = new ArrayList<Integer>();
		for(int id = _condition.get(pers.etat()).size()-1; id >= 0; id--)
			if(_condition.get(pers.etat()).get(id).value(pers))
				choice.add(id);

		//le personnage ne fait rien, penser à Attendre quand cela sera implémenté
		if(choice.size() == 0)
		{
			pers.paralyse();
			pers.setState(new States(Statut.ATTENDS, Cardinaux.NORD));
			return;
		}

		//comparateur utilisé pour comparer en utilisant les poids des transitions
		Collections.sort(choice, (new Comparator<Integer>() {
			int _etat;
			@Override
			public int compare(Integer i1, Integer i2)
		    {
				return  _poids.get(_etat).get(i1) - _poids.get(_etat).get(i2);
		    }

			Comparator<Integer> init(int etat)
			{
				_etat = etat;
				return this;
			}
		}).init(pers.etat()));

		int poids = _poids.get(pers.etat()).get(choice.get(choice.size()-1));
		/*for(Integer id : choice)
		{
			if(poids != _action.get(pers.etat()).get(id).poids())
				choice.remove(id);
		}*/
		for(int id = choice.size()-1; id>=0; id--){
			if(poids != _poids.get(pers.etat()).get(choice.get(id)))
				choice.remove(id);
		}

		Collections.shuffle(choice);
		//System.out.println("Je vais " + _action.get(pers.etat()).get(choice.get(0)).action().getClass().getName());
		_action.get(pers.etat()).get(choice.get(0)).Act(pers);
		pers.setetat(_next.get(pers.etat()).get(choice.get(0)));
	}

	public boolean match(Classe classe){
		for(int i=0;i<_action.size();i++){
			for(int j=0;j<_action.get(i).size();j++){
				if(!classe.isAction(_action.get(i).get(j).action().getClass())){
					return false;
				}
			}
		}
		for(int i=0;i<_condition.size();i++){
			for(int j=0;j<_condition.get(i).size();j++){
				if(!classe.isCondition(_condition.get(i).get(j).getClass())){
					return false;
				}
			}
		}

		return true;
	}


	private String _name = "Unknow";
	public String nom() {
		return _name;
	}

	public void setnom(String nom) {
		_name = nom;
	}
}
