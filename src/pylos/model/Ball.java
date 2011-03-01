package pylos.model;

import pylos.view.BallGraphics;

public class Ball {
	public Player owner;
	public boolean onBoard = false;
	public BallGraphics graphics;

	public Ball(Player owner, Model model) {
		this.owner = owner;
		model.balls.add(this);
	}
}
