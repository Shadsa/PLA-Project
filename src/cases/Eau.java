package cases;

public final class Eau extends Terrain {

	public Eau(int x, int y) {
		super(x, y);
	}

	public final static int _id = getId(1);

	@Override
	public int value() {
		return _id;
	}

	@Override
	public Boolean isfree() {
		return false;
	}

}