package pylos.ai.move;

import pylos.model.Position;

public class MountBall extends Ply {

	public MountBall(Position from, Position to, Position[] removes) {
		super(to, addRemove(from, removes));
		mount = true;
	}

	public static Position[] addRemove(Position from, Position[] removes) {
		Position[] tmp = new Position[removes.length + 1];
		for (int i = 0; i < removes.length; i++) {
			tmp[i + 1] = removes[i];
		}
		tmp[0] = from;
		return tmp;

	}
}
