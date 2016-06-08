package cases;

import java.lang.reflect.InvocationTargetException;

public abstract class Terrain extends Case {


	Terrain(int x, int y) {
		super(x, y);
	}

	protected String type;

	private static int _nextId = 0;

	// @ensure range>0
	protected static int getId(int range)
	{
		_nextId += range;
		return _nextId - range;
	}

	/*

	 public Batiment (String type, int i){
		super();
		this.type = batiment;
		this.i = 2;

	}
	*/
	
	@Override
	public Case modifierCase(Class<? extends Case> c) {
		Case nouvelleCase = null;
		Class[] arg = new Class[2];
		arg[0] = int.class;
		arg[1] = int.class;
		try {
			nouvelleCase = c.getDeclaredConstructor(arg).newInstance(this.X(),this.Y());
		} catch (Exception e) {
			e.printStackTrace();
			return this;
		}
		nouvelleCase.setPersonnage(Personnage());	
		Personnage().setCase(nouvelleCase);
		return nouvelleCase;
	}

	public void Ressources (String type){
		this.type = type ;
	}
}

