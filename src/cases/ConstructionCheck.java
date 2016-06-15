package cases;

public class ConstructionCheck implements CaseProperty{

	@Override
	public boolean check(Case c) {
		return c!=null && (c.type() instanceof Construction || c.type() instanceof Batiment);
	}
}
