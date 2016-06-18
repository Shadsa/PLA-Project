package roles;

public enum Cardinaux {
	NORD,
	SUD,
	EST,
	OUEST;

	public static Cardinaux oppose(Cardinaux direction) {
		switch(direction)
		{
		case NORD: return SUD;
		case SUD: return NORD;
		case EST: return OUEST;
		case OUEST: return EST;
		}
		return null;
	}
}
