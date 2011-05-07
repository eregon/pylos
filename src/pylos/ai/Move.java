package pylos.ai;

import pylos.model.Ball;
import pylos.model.Position;

public class Move {
	Ball ball;
	Position position;
	public Move(Ball ball, Position pos) {
		this.ball = ball;
		position = pos;
	}
}
