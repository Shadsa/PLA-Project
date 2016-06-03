package roles.action;

import java.util.Vector;

import roles.Case;

public abstract class World {

	static Vector<Vector<Case>> _map;

	public static Boolean isfree(int x, int y) {
		return World.Case(x, y).isfree();
	}

	public static Case Case(int x, int y) {
		return (x < 0 || y < 0 || x >= _map.size() | y >= _map.size())? null : _map.get(x).get(y);
	}

}
