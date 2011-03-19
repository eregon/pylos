package pylos.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
	public static final int LEVELS = 4;
	public static final int BALLS = 30;
	public static final List<Ball> balls = new LinkedList<Ball>();
	public static final Player player1 = new Player(+1);
	public static final Player player2 = new Player(-1);
	public static final Player[] players = { player1, player2 };

	public static Player currentPlayer = player1;

	public List<Position> getPositionsToPlaceBallOnBoard() {
		List<Position> list = new LinkedList<Position>();
		for (int level = 0; level < LEVELS; level++) {
			for (Position position : accessibleBalls(level)) {
				list.add(position);
			}
		}
		return list;
	}

	public boolean isBoardEmpty() {
		for (Ball ball : balls) {
			if (ball.onBoard)
				return false;
		}
		return true;
	}

	public boolean isBallAt(Position position) { // does ball is on this position ?
		for (Ball ball : balls) {
			if (ball.onBoard) {
				if (ball.position.equals(position))
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there are 4 balls under the position or if is it on the first level
	 * (so one can place a ball there)
	 */
	public boolean canPlaceBallAt(Position position) {
		if (position.z == 0)
			return true;
		for (int x = position.x; x < position.x + 2; x++) {
			for (int y = position.y; y < position.y + 2; y++) {
				if (!isBallAt(new Position(x, y, position.z - 1)))
					return false;
			}
		}
		return true;
	}

	public List<Position> accessibleBalls(int level) {
		List<Position> list = new LinkedList<Position>();
		for (int x = 0; x < LEVELS - level; x++) {
			for (int y = 0; y < LEVELS - level; y++) {
				if (canPlaceBallAt(new Position(x, y, level)))
					if (!isBallAt(new Position(x, y, level)))
						list.add(new Position(x, y, level));
			}
		}
		return list;
	}

	public List<Position> getWhereToPlaceBallToCarryUp(Ball ball) {
		List<Position> list = new LinkedList<Position>();
		// TODO
		return list;
	}

	public static int ballsBySideAtLevel(int level) {
		return LEVELS - level;
	}

	public static List<Position> getAllPositionsForLevelLineByLine(int level, int line) { // to check if any lines
		List<Position> list = new LinkedList<Position>();
		for (int x = 0; x < LEVELS - level; x++) {
			list.add(new Position(x, line, level));
		}
		return list;
	}

	public static List<List<Position>> getAllPositionForLevelSquareBySquare(int level) { // to check if any squares
		List<List<Position>> squares = new LinkedList<List<Position>>();
		for (int line = 1; line < LEVELS - level; line++) {
			for (int column = 1; column < LEVELS - level; column++) {
				List<Position> square = new LinkedList<Position>();
				square.add(new Position(line, column, level));
				square.add(new Position(line - 1, column, level));
				square.add(new Position(line, column - 1, level));
				square.add(new Position(line - 1, column - 1, level));
				squares.add(square);
			}
		}
		return squares;
	}
}
