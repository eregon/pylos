package pylos.model;

/**
 * Represent an (immutable) position on the Board.
 * x and y are horizontal coordinates, z is the level in the pyramid.
 */
public class Position {
	public final int x, y, z;

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
