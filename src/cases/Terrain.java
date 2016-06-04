package cases;

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
	 public Caillou (String type, int i){
	 	 super();
	 	 this.type = caillou;
	 	 this.i = 0;

	}


	 public Arbre (String type, int i){
		super();
		this.type = arbre;
		this.i = 1;

	}

	 public Batiment (String type, int i){
		super();
		this.type = batiment;
		this.i = 2;

	}
	*/

	public void Ressources (String type){
		this.type = type ;
	}
}

