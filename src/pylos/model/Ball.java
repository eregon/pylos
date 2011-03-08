package pylos.model;

import pylos.view.BallGraphics;

public class Ball {
	public final Player owner;
	public boolean onBoard = false;
	public int x, y, z;
	public BallGraphics graphics;

	public Ball(Player owner) {
		this.owner = owner;
		Model.balls.add(this);
	}
}
