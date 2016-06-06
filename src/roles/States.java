package roles;
public class States {
	public enum Statut {

	    AVANCE, ATTAQUE, ATTENDS;

	}

    public Statut statut;
    public Cardinaux direction;
    public States(Statut stat, Cardinaux dir)
    {
    	statut = stat;
    	direction = dir;
    }
}