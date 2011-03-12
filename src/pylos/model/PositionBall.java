package pylos.model;

import pylos.view.PositionBallGraphics;

public class PositionBall {
	public int x, y, z;
	public PositionBallGraphics graphics;

	public PositionBall(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		graphics = new PositionBallGraphics(this);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
