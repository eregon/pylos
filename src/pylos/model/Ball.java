package pylos.model;

import pylos.view.BallGraphics;

public class Ball {
	public float x, y, z;
	public Player owner;
	public boolean onBoard = false;
	public BallGraphics graphics;

	public Ball(Player owner, float x, float y, float z) {
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
