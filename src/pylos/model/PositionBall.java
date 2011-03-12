package pylos.model;

import pylos.view.PositionBallGraphics;

public class PositionBall {
	public int x, y, level;
	public PositionBallGraphics graphics;

	public PositionBall(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		graphics = new PositionBallGraphics(this);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + level + ")";
	}
}
