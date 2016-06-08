package workshop;
import java.util.Observable;
import java.util.Observer;

import roles.*;

public class WorkshopInit implements Observer {
	private WorkshopCreator workshop;
	
	
	public WorkshopInit(){
		workshop = new WorkshopCreator();
	}
	
	public void init(){
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
