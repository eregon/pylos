package pylos.model;

import java.util.LinkedList;
import java.util.List;

import pylos.Pylos;
import pylos.exception.PylosError;
import pylos.view.PlayerGraphics;

public class Player {
	public static final int nbBalls = Model.nbBalls / 2;
	public final Ball[] balls = new Ball[nbBalls];
	public final int side;
	public PlayerGraphics graphics;

	public Player(int side) {
		this.side = side;
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(this, i);
		}
		graphics = new PlayerGraphics(this);
	}

	@Override
	public String toString() {
		return "Player " + (this == Model.player1 ? 1 : 2);
	}

	public LinkedList<Ball> partitionBalls(LinkedList<Ball> ballsOnBoard) {
		LinkedList<Ball> ballsInSide = new LinkedList<Ball>();
		for (Ball ball : balls) {
			if (!ball.onBoard) {
				ballsInSide.add(ball);
			} else {
				ballsOnBoard.add(ball);
			}
		}
		return ballsInSide;
	}

	public void putBallOnBoard(PositionBall position) {
		Ball last = lastBallOnSide();
		last.onBoard = true;
		last.x = position.x;
		last.y = position.y;
		last.level = position.level;
		Pylos.logger.info(this + " place his " + last + " at " + position);
	}

	private Ball lastBallOnSide() {
		for (int i = nbBalls - 1; i >= 0; i--) {
			if (!balls[i].onBoard) {
				return balls[i];
			}
		}
		throw new PylosError("Could not find ball in the side to put on the pyramid");
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
