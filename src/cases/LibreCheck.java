package cases;

public class LibreCheck implements CaseProperty {

	@Override
	public boolean check(Case c) {
		return c!=null && c.isfree();
	}

}
