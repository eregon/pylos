package pylos.model;

import java.util.LinkedList;
import java.util.List;

import pylos.exception.PylosError;
import pylos.view.PlayerGraphics;

public class Player {
	public enum Action {
		/** place a new ball or remove one to mount */
		PLACE,
		/** place the removed ball on a higher level */
		RISE,
		/** choose 1 or 2 balls to remove from board */
		REMOVE,
		/** Wait for other player */
		WAIT
	}

	public static final int nbBalls = Model.BALLS / 2;
	public final Ball[] balls = new Ball[nbBalls];
	public final int side;
	public PlayerGraphics graphics;
	public Action action = Action.PLACE;

	public Player(int side) {
		this.side = side;
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(this);
		}
		graphics = new PlayerGraphics(this);
	}

	@Override
	public String toString() {
		return "Player " + (this == Model.player1 ? 1 : 2);
	}

	public LinkedList<Ball> partitionBalls(LinkedList<Ball> ballsOnBoard) {
		LinkedList<Ball> ballsOnSide = new LinkedList<Ball>();
		for (Ball ball : balls) {
			if (ball.onBoard) {
				ballsOnBoard.add(ball);
			} else {
				ballsOnSide.add(ball);
			}
		}
		return ballsOnSide;
	}

	public void putBallOnBoard(Position position) {
		Ball last = lastBallOnSide();
		last.placeAt(position);
	}

	private Ball lastBallOnSide() {
		for (int i = nbBalls - 1; i >= 0; i--) {
			if (!balls[i].onBoard) {
				return balls[i];
			}
		}
		throw new PylosError("Could not find ball in the side to put on the pyramid");
	}

	/**
	 * Balls which can be removed from the board and be placed back on the side
	 */
	public List<Ball> getRemovableBalls() {
		List<Ball> list = new LinkedList<Ball>();
		for (Ball ball : balls) {
			if (ball.isRemovable())
				list.add(ball);
		}
		return list;
	}

	public List<Ball> getMountableBalls() {
		List<Ball> list = new LinkedList<Ball>();
		for (Ball ball : balls) {
			if (ball.isMountable())
				list.add(ball);
		}
		return list;
	}

	public boolean anyLineOrSquare(Position position) {
		return anyLine(position) || anySquare(position);
	}

	private boolean anyLine(Position position) {
		if (position.z >= 2)
			return false;
		for (List<Position> line : position.lines()) {
			boolean validLine = true;
			for (Position pos : line) {
				if (!Model.board.anyBallAt(pos) || Model.board.ballAt(pos).owner != this) {
					validLine = false;
					break;
				}
			}
			if (validLine)
				return true;
		}
		return false;
	}

	private boolean anySquare(Position position) {
		if (position.z >= 2)
			return false;
		for (List<Position> list : position.fourSquare()) {
			boolean validSquare = true;
			for (Position pos : list) {
				if (!Model.board.anyBallAt(pos) || Model.board.ballAt(pos).owner != this) {
					validSquare = false;
					break;
				}
			}
			if (validSquare)
				return true;
		}
		return false;
	}

	public void riseBall(Ball ball) {
		ball.removeFromBoard();
		action = Action.RISE;
	}

	public boolean allBallsOnBoard() {
		for (Ball ball : balls) {
			if (!ball.onBoard)
				return false;
		}
		return true;
	}

	// Action methods
	public void resetAction() {
		action = Action.PLACE;
	}

	public boolean canRise() {
		return action == Action.PLACE;
	}
}
