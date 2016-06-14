package cases;

public class TypeCheck implements CaseProperty {

	Class<? extends TypeCase> _type;
	
	public TypeCheck(Class<? extends TypeCase> t){
		_type=t;
	}
	@Override
	public boolean check(Case c) {
		return c!=null && _type.isInstance(c);
	}

}
