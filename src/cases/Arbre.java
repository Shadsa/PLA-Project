package cases;

public final class Arbre extends Terrain {

	public Arbre(int x, int y) {
		super(x, y);
	}

	private static int _id = getId(1);

	@Override
	public int value() {
		return _id;
	}

}
