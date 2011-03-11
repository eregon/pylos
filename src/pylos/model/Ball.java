package pylos.model;

import pylos.view.BallGraphics;

public class Ball {
	public final Player owner;
	public boolean onBoard = false;
	public int i, x, y, z;
	public BallGraphics graphics;

	public Ball(Player owner, int i) {
		this.owner = owner;
		this.i = i;
		Model.balls.add(this);
		graphics = new BallGraphics(this);
	}
}
