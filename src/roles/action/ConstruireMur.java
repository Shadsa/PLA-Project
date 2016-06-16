package roles.action;  
 
import cases.Caillou;
import cases.Mur;
import cases.Piege;
import cases.Plaine;
import roles.Cardinaux;  
import roles.Personnage;  
import roles.States;
import roles.World;
import roles.States.Statut;  
 
public class ConstruireMur extends Action {  
 
	Cardinaux _direction;  
    
	private static int _Id = Action.getId(1);  
 
	public ConstruireMur(Cardinaux card) {  
		super();  
		_direction = card;  
	}  
    
	@Override  
	public void Act(Personnage pers) {  
		int destX = pers.X() + ((_direction == Cardinaux.OUEST)? (-1) : ((_direction == Cardinaux.EST)? 1 : 0));  
		int destY = pers.Y() + ((_direction == Cardinaux.NORD)? (-1) : ((_direction == Cardinaux.SUD)? 1 : 0));  
		if(pers.owner().changerRessource(-50) && (World.Case(destX, destY).type() instanceof Plaine && World.Case(destX, destY).type() instanceof Caillou))
			World.modifierCase(new Mur(pers), destX, destY);  
		pers.setState(new States(Statut.ATTAQUE, _direction));  
	}  
 
	@Override  
	public int toInt() {  
		return _Id;  
	}  
 
}  
