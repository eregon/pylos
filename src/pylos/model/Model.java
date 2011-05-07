package pylos.model;

import java.util.LinkedList;
import java.util.List;

/**
 * The Pylos Model.
 * Implements the singleton pattern by having everything static and no instance (the object is the class).
 * Contains the global model logic and pointers to the board and players.
 */
public abstract class Model {
	public static final int LEVELS = 4;
	public static final int LEVELS_1 = LEVELS - 1;
	public static final int BALLS = 30;
	public static Board board;
	public static Player player1;
	public static Player player2;
	public static Player[] players;

	public static Player currentPlayer;

	public static void initialize() {
		board = new Board();
		player1 = new Player(+1);
		player2 = new Player(-1);
		players = new Player[] { player1, player2 };
		currentPlayer = player1;
	}

	public static Player otherPlayer() {
		return currentPlayer == player1 ? player2 : player1;
	}

	public static boolean isWinner() {
		return board.anyBallAt(Position.top);
	}

	/**
	 * Checks if there are 4 balls under the position or if it is on the first level
	 * and if there is no ball at that place
	 * (so one can place a ball there)
	 */
	public static boolean canPlaceBallAt(Position position) {
		if (board.anyBallAt(position))
			return false;
		if (position.z == 0)
			return true;
		for (int x = position.x; x < position.x + 2; x++) {
			for (int y = position.y; y < position.y + 2; y++) {
				if (!board.anyBallAt(Position.at(x, y, position.z - 1)))
					return false;
			}
		}
		return true;
	}

	public static boolean canPlaceBallAtIgnoring(Position position, Position ignored) {
		if (board.anyBallAt(position))
			return position == ignored;
		if (position.z == 0)
			return true;
		for (int x = position.x; x < position.x + 2; x++) {
			for (int y = position.y; y < position.y + 2; y++) {
				Position pos = Position.at(x, y, position.z - 1);
				if (!board.anyBallAt(pos) || pos == ignored)
					return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return positions to place ball at any level
	 */
	public static List<Position> getPositionBalls() {
		List<Position> list = new LinkedList<Position>();
		for (int level = 0; level < LEVELS; level++) {
			for (Position position : accessibleBalls(level)) {
				list.add(position);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param level
	 * @return positions to place a ball at referenced level
	 */
	public static List<Position> accessibleBalls(int level) {
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

	/**
	 * this needs to change onBoard to true, when the ball is placed in his new position
	 * 
	 * @return positions where referenced ball can be rising
	 */
	public static List<Position> getPositionsToMount(Ball ball) {
		List<Position> list = new LinkedList<Position>();
		for (int z = ball.position.z + 1; z < LEVELS; z++) {
			for (int x = 0; x < 4 - z; x++) {
				for (int y = 0; y < 4 - z; y++) {
					if (canPlaceBallAtIgnoring(Position.at(x, y, z), ball.position))
						list.add(Position.at(x, y, z));
				}
			}
		}
		return list;
	}

	public static int ballsBySideAtLevel(int level) {
		return LEVELS - level;
	}
}
