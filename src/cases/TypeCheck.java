package cases;

public class TypeCheck implements CaseProperty {

	int _type;
	
	public TypeCheck(TypeCase t){
		_type=t.value();
	}
	@Override
	public boolean check(Case c) {
		return c!=null && c.type().value()==_type;
	}

}
