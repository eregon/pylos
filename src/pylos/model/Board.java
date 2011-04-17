package pylos.model;

import java.util.LinkedList;
import java.util.List;

public class Board {
	public final List<Ball> balls = new LinkedList<Ball>();

	public boolean isEmpty() {
		for (Ball ball : balls) {
			if (ball.onBoard)
				return false;
		}
		return true;
	}

	/**
	 * @return whether there is a ball at position
	 */
	public boolean anyBallAt(Position position) {
		for (Ball ball : balls) {
			if (ball.onBoard && ball.position == position)
				return true;
		}
		return false;
	}

	public Ball ballAt(Position position) {
		for (Ball ball : balls) {
			if (ball.onBoard && ball.position == position)
				return ball;
		}
		return null;
	}
}
