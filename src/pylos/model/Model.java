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

	public Model() {
		Position.createPositions();
	}

	public List<Position> getPositionBalls() {
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

	public static boolean ballAt(Position position) { // does ball is on this position ?
		for (Ball ball : balls) {
			if (ball.onBoard && ball.position == position)
				return true;
		}
		return false;
	}

	/**
	 * Checks if there are 4 balls under the position or if it is on the first level
	 * and if there is no ball at that place
	 * (so one can place a ball there)
	 */
	public boolean canPlaceBallAt(Position position) {
		if (ballAt(position))
			return false;
		if (position.z == 0)
			return true;
		for (int x = position.x; x < position.x + 2; x++) {
			for (int y = position.y; y < position.y + 2; y++) {
				if (!ballAt(Position.at(x, y, position.z - 1)))
					return false;
			}
		}
		return true;
	}

	public List<Position> accessibleBalls(int level) {
		List<Position> list = new LinkedList<Position>();
		Position position;
		for (int x = 0; x < LEVELS - level; x++) {
			for (int y = 0; y < LEVELS - level; y++) {
				position = Position.at(x, y, level);
				if (canPlaceBallAt(position))
					list.add(position);
			}
		}
		return list;
	}

	/*
	 * this needs to change onBoard to true, when the ball is placed in his new position
	 */
	public List<Position> getPositionsToRise(Ball ball) {
		ball.onBoard=false;
		List<Position> list = new LinkedList<Position>();
		for (int z = ball.position.z+1; z < LEVELS; z++) {
			for (int x = 0; x < 4-z; x++) {
				for (int y = 0; y < 4-z; y++) {
					if(canPlaceBallAt(Position.at(x, y, z)))
						list.add(Position.at(x,y,z));
				}
			}
		}
		return list;
	}

	public static int ballsBySideAtLevel(int level) {
		return LEVELS - level;
	}

	public static List<Position> getAllPositionsForLevelLineByLine(int level, int line) { // to check if any lines
		List<Position> list = new LinkedList<Position>();
		for (int x = 0; x < LEVELS - level; x++) {
			list.add(Position.at(x, line, level));
		}
		return list;
	}

	public static List<List<Position>> getAllPositionForLevelSquareBySquare(int level) { // to check if any squares
		List<List<Position>> squares = new LinkedList<List<Position>>();
		for (int line = 1; line < LEVELS - level; line++) {
			for (int column = 1; column < LEVELS - level; column++) {
				List<Position> square = new LinkedList<Position>();
				square.add(Position.at(line, column, level));
				square.add(Position.at(line - 1, column, level));
				square.add(Position.at(line, column - 1, level));
				square.add(Position.at(line - 1, column - 1, level));
				squares.add(square);
			}
		}
		return squares;
	}

	public boolean isWinner() {
		return ballAt(Position.top);
	}
}
