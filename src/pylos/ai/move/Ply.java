package pylos.ai.move;

import pylos.ai.State;
import pylos.model.Position;

public class Ply {

	public Position at;
	public Position[] removes;

	public Ply(Position at, Position[] removes) {
		this.at = at;
		this.removes = removes;
	}

	public State apply(State s) {
		return null;
	}

	public void makeMove() {

	}
}
