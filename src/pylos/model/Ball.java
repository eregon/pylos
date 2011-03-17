package pylos.model;

import pylos.view.ball.PlayerBallGraphics;

public class Ball {
	public Position position;
	public final Player owner;
	public boolean onBoard = false;
	public PlayerBallGraphics graphics;

	public Ball(Player owner) {
		this.owner = owner;
		Model.balls.add(this);
		graphics = new PlayerBallGraphics(this);
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
