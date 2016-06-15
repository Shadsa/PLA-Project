package workshop;

import java.util.ArrayList;
import roles.*;
import roles.action.Action;
import roles.classe.Classe;
import roles.conditions.Condition;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

public class WorkshopCreator {

	private String filepath = "WorkShop/";//"../../WorkShop/";
	private ArrayList<String> deckActionName;
	private ArrayList<String> deckConditionName;
	private ArrayList<String> deckClasseName;
	private ArrayList<ArrayList<Class<Action>>> deckAction;
	private ArrayList<ArrayList<Class<Condition>>> deckCondition;
	private ArrayList<Classe> deckClasse;

	public WorkshopCreator(){
		deckAction = new ArrayList<ArrayList<Class<Action>>>();
		deckCondition = new ArrayList<ArrayList<Class<Condition>>>();
		deckActionName = new ArrayList<String>();
		deckConditionName = new ArrayList<String>();
		deckClasse = new ArrayList<Classe>();
		deckClasseName = new ArrayList<String>();
		try {
			load();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void load() throws IOException, ClassNotFoundException{
		ArrayList<File> files = new ArrayList<File>();
		FileInputStream fis = null;
	    FileOutputStream fos = null;
		int res = 0;
		String buf = "";

		//LOAD OF ACTION DECK
		File f = new File(filepath+"action.deck/");
		for(File name : f.listFiles()){
			if(name.isFile()){
				files.add(name);
			}
		}

		for(int i=0;i<files.size();i++){
			deckActionName.add(files.get(i).getName());
			ArrayList<Class<Action>> newdeck = new ArrayList<Class<Action>>();
			fis = new FileInputStream(files.get(i));
			while((res = fis.read()) != -1){
				if(res == '\n'){
					Class<Action> newAction = (Class<Action>) Class.forName("roles.action." + buf);
					newdeck.add(newAction);
					buf="";
				}else if(res != '\r'){
					buf+= (char)res;
				}
			}
			if(buf != ""){
				Class<Action> newAction = (Class<Action>) Class.forName("roles.action." + buf);
				newdeck.add(newAction);
				buf="";
			}
			deckAction.add(newdeck);
		}

		//LOAD OF COND DECK
		files.clear();
		buf = "";

		f = new File(filepath+"condition.deck/");
		for(File name : f.listFiles()){
			if(name.isFile()){
				files.add(name);
			}
		}

		for(int i=0;i<files.size();i++){
			deckConditionName.add(files.get(i).getName());
			ArrayList<Class<Condition>> newdeck = new ArrayList<Class<Condition>>();
			fis = new FileInputStream(files.get(i));
			while((res = fis.read()) != -1){
				if(res == '\r'){
					//do nothing
				}else if(res == '\n'){
					Class<Condition> newAction = (Class<Condition>)Class.forName("roles.conditions." + buf);
					newdeck.add(newAction);
					buf="";
				}else if(res != '\r'){
					buf+= (char)res;
				}
			}
			if(buf != ""){
				Class<Condition> newAction = (Class<Condition>) Class.forName("roles.conditions." + buf);
				newdeck.add(newAction);
				buf="";
			}
			deckCondition.add(newdeck);
		}




	}

// API FUNCTION
	
	public void loadClass(){ //désérialisation
		ObjectInputStream ois;
	    ObjectOutputStream oos;
	    
	}
	
	public void saveClass(Classe c){//sérialisation
		ObjectInputStream ois;
	    ObjectOutputStream oos;
	    
	}

//STRUCTURE

	public void createClasse(String name, Bonus bonus, String deckActName, String deckCondName){
			Classe newclass = new Classe(10,5,2,2,name,false,bonus,getDeckAction(deckActName),getDeckCondition(deckCondName));
			deckClasse.add(newclass);
			deckClasseName.add(name);
	}


	public ArrayList<Class<Action>> getDeckAction(String name){
		for(int i=0;i<deckActionName.size();i++){
			if(deckActionName.get(i)==name){
				return deckAction.get(deckActionName.indexOf(deckActionName.get(i)));
			}
		}
		return null;
	}

	public ArrayList<Class<Condition>> getDeckCondition(String name){
		for(int i=0;i<deckConditionName.size();i++){
			if(deckConditionName.get(i)==name){
				return deckCondition.get(deckConditionName.indexOf(deckConditionName.get(i)));
			}
		}
		return null;
	}

	public Classe getDeckClasse(String name){
		for(int i=0;i<deckClasseName.size();i++){
			if(deckClasseName.get(i)==name){
				return deckClasse.get(deckClasseName.indexOf(deckClasseName.get(i)));
			}
		}
		return null;
	}

	public ArrayList<Classe> classeList(){
		return deckClasse;
	}

	public ArrayList<ArrayList<Class<Action>>> actionList(){
		return deckAction;
	}

	public ArrayList<ArrayList<Class<Condition>>> conditionList(){
		return deckCondition;
	}



//BUILDER

	public Action getAction(String name, Object[] parameter) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		return (Action) Class.forName(name).getDeclaredConstructor(Cardinaux.class).newInstance(parameter);
	}

	public Condition getCondition(String name, Object parameter) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		return (Condition) Class.forName(name).getDeclaredConstructor(Cardinaux.class).newInstance(parameter);
	}

}
