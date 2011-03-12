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

	public List<PositionBall> getPositionsToPlaceBallOnBoard() {
		List<PositionBall> list = new LinkedList<PositionBall>();
		// TODO
		if (isBoardEmpty()) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					list.add(new PositionBall(i, 0, j));
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

	public List<PositionBall> getWhereToPlaceBallToCarryUp(Ball ball) {
		List<PositionBall> list = new LinkedList<PositionBall>();
		// TODO
		return list;
	}
}