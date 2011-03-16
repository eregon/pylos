package pylos.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
	public static final int levels = 4;
	public static final int nbBalls = 30;
	public static final List<Ball> balls = new LinkedList<Ball>();
	public static final Player player1 = new Player(+1);
	public static final Player player2 = new Player(-1);
	public static final Player[] players = { player1, player2 };

	public static Player currentPlayer = player1;

	public List<Position> getPositionsToPlaceBallOnBoard() {
		List<Position> list = new LinkedList<Position>();
		// TODO
		if (isBoardEmpty()) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					list.add(new Position(x, y, 0));
				}

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

	public List<Position> getWhereToPlaceBallToCarryUp(Ball ball) {
		List<Position> list = new LinkedList<Position>();
		// TODO
		return list;
	}

	public static int ballsBySideAtLevel(int level) {
		return levels - level;
	}
}