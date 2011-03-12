package pylos.model;

public class PositionBall {
	public int x, y, z;

	public PositionBall(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
