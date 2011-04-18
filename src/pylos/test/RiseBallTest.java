package pylos.test;

import java.util.List;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Player;
import pylos.model.Position;

public class RiseBallTest extends PylosTestCase {
	public void testRiseBall() {
		Player p = Model.currentPlayer;
		assertTrue(p.getMountableBalls().isEmpty());

		/*
		 * xxx0
		 * xx00
		 * 0000
		 * 0000
		 */
		// We should alternate which player place a Ball, but it does not matter for this test
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 0, 0));

		List<Ball> mountables = p.getMountableBalls();
		assertEquals(1, mountables.size());
		assertEquals(Model.board.ballAt(Position.at(2, 0, 0)), mountables.get(0));

		Ball ball = mountables.get(0);
		List<Position> positionsToRise = Model.getPositionsToRise(ball);

		assertEquals(1, positionsToRise.size());
		assertEquals(Position.at(0, 0, 1), positionsToRise.get(0));
	}
}
