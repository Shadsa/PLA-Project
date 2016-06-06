package roles;
public class States {
	public enum Statut {

	    AVANCE, ATTAQUE, ATTENDS, MORT;

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