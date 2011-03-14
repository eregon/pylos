package pylos.model;

import pylos.view.ball.BallGraphics;

public class Ball {
	public final Player owner;
	public boolean onBoard = false;
	public int i, x, y, level;
	public BallGraphics graphics;

	public Ball(Player owner, int i) {
		this.owner = owner;
		this.i = i;
		Model.balls.add(this);
		graphics = new BallGraphics(this);
	}

	@Override
	public String toString() {
		return "Ball #" + i + " (" + owner.side + ")";
	}

	public boolean checkIfRemovable() {
		// TODO
		return false;
	}
}
