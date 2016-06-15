package cases;

public class Terrain extends Case {

	public Terrain(int x, int y, TypeCase type) {
		super(x, y, type);
	}

	protected String _ressource;

	public void Ressources (String type){
		this._ressource = type ;
	}

}

