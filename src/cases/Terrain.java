package cases;

public abstract class Terrain extends Case {
	int i;
	protected String type;
	
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

