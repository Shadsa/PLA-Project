package cases;

import java.lang.reflect.InvocationTargetException;

public class Terrain extends Case {

	public Terrain(int x, int y, TypeCase type) {
		super(x, y, type);
	}

	protected String _ressource;


	/*

	 public Batiment (String type, int i){
		super();
		this.type = batiment;
		this.i = 2;

	}
	*/

	public void Ressources (String type){
		this._ressource = type ;
	}

}

