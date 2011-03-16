package pylos.model;

import pylos.view.ball.BallGraphics;

public class Ball {
	public Position position;
	public final Player owner;
	public boolean onBoard = false;
	public BallGraphics graphics;

	public Ball(Player owner, int i) {
		this.owner = owner;
		Model.balls.add(this);
		graphics = new BallGraphics(this);
	}

	@Override
	public String toString() {
		return "Ball (" + owner.side + ")";
	}

	public boolean checkIfRemovable() {
		// TODO
		return false;
	}
}
