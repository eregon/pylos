package pylos.ai.move;

import pylos.model.Position;

public class MountBall extends Ply {

	public MountBall(Position from, Position to, Position[] removes) {
		super(to, addRemove(from, removes));
		mount = true;
	}

	/**
	 * add a remove action corresponding to the ball removed before being mounted 
	 */
	public static Position[] addRemove(Position from, Position[] oldRemoves) {
		Position[] removes = new Position[oldRemoves.length + 1];
		for (int i = 0; i < oldRemoves.length; i++) {
			removes[i + 1] = oldRemoves[i];
		}
		removes[0] = from;
		return removes;

	}
}
