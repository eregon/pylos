package pylos.model;

import java.util.LinkedList;
import java.util.List;

public class Player {
	public static final int nbBalls = Model.nbBalls / 2;
	public final Ball[] balls = new Ball[nbBalls];
	public final int side;

	public Player(int side) {
		this.side = side;
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(this, i);
		}
	}

	public LinkedList<Ball> ballsInSide() {
		LinkedList<Ball> list = new LinkedList<Ball>();
		for (Ball ball : balls) {
			if (!ball.onBoard) {
				list.add(ball);
			}
		}
		return list;
	}

	public List<Ball> getBallsToRemove() {
		List<Ball> list = new LinkedList<Ball>();
		// TODO
		return list;
	}

	public List<Ball> getBallsToCarryUp() {
		List<Ball> list = new LinkedList<Ball>();
		// TODO
		return list;
	}

	public boolean checkIfAnyLineOrSquare() {
		// TODO
		return false;
	}

	public boolean checkIfAnyLine() {
		// TODO
		return false;
	}

	public boolean checkIfAnySquare() {
		// TODO
		return false;
	}
}
