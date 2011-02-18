package pylos.model;

public class Ball {
	public int x, y, z;
	private Player owner;
	private boolean onBoard = false;

	public Ball(Player owner) {
		this.owner = owner;
	}
}
