package roles;
public class States {
	public enum Statut {

	    AVANCE, ATTAQUE, SOIGNE, ATTENDS, MORT, HIDE, HIDING, REVEAL;

	}

    public Statut statut;
    public Cardinaux direction;
    public States(Statut stat)
    {
    	statut = stat;
    	direction = null;
    }
    public States(Statut stat, Cardinaux dir)
    {
    	statut = stat;
    	direction = dir;
    }
}