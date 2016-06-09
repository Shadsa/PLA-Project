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
import java.lang.reflect.InvocationTargetException;

public class WorkshopCreator {

	private String filepath = "../../Workshop/";
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
		load();
	}

	public void load(){
		// WorkShop Action
		File f = new File(filepath+"/action.deck/");
		ArrayList<File> files=new ArrayList<File>();
		for (File file : f.listFiles()){
			files.add(file);
		}
		int taille = files.size(); // Nombre de fichiers total dans le dossier
		for (int i=0;i< taille;i++){
			files.get(i); // me donne le fichier
			deckActionName.add(files.get(i).getName());
			deckAction.add(new ArrayList<Class<Action>>()); //initialisation d'un arraylist
			files.get(i).list();
			FileInputStream fis = null;
			      try {
			         fis = new FileInputStream(new File(files.get(i).getName()));
			         byte[] buf = new byte[8];
			         int n = 0;
			       /*  while ((n = fis.read(buf)) >= 0) {
			            // On écrit dans notre deuxième fichier avec l'objet adéquat
			        	Class.forName()
			            // On affiche ce qu'a lu notre boucle au format byte et au
			            // format char
			            for (byte bit : buf) {
			               System.out.print("\t" + bit + "(" + (char) bit + ")");
			               System.out.println("");
			            }
			            buf = new byte[8];

			         }

			      } catch (FileNotFoundException e) {
			         e.printStackTrace();
			      } catch (IOException e) {
			         e.printStackTrace();
			      } finally {
			         try {
			            if (fis != null)
			               fis.close();
			         } catch (IOException e) {
			            e.printStackTrace();
			         }
			         }
			      }
			   }
			}*/
				//Class.forName(ligne lue)


			      }

			deckAction.get(i);
		}

		// WorkShop Condition
	}


	public ArrayList<Action> getDeckAction(String name){
		
	}
	
//STRUCTURE
	
	public void createClasse(String name, Bonus bonus, String deckActName, String deckCondName){
			Classe newclass = new Classe(10,5,2,name,bonus);
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
