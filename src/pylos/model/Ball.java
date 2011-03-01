package pylos.model;

import pylos.view.BallGraphics;

public class Ball {
	public Player owner;
	public boolean onBoard = false;
	public BallGraphics graphics;

	public Ball(Player owner) {
		this.owner = owner;
		Model.balls.add(this);
	}
}
