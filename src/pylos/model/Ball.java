package pylos.model;

import pylos.view.BallGraphics;

public class Ball {
	public int x, y, z;
	public Player owner;
	public boolean onBoard = false;
	public BallGraphics graphics;

	public Ball(Player owner) {
		this.owner = owner;
		graphics = new BallGraphics(this);
	}
}
