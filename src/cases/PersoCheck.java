package cases;

public class PersoCheck implements CaseProperty{

	@Override
	public boolean check(Case c) {
		return c!=null && c.Personnage() != null;
	}
}
