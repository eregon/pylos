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
		 * ooo.
		 * oo..
		 * ....
		 * ....
		 */
		// We should alternate which player place a Ball, but it does not matter for this test
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 0, 0));

		List<Ball> mountables = p.getMountableBalls();
		assertEquals(1, mountables.size());
		Ball ball = mountables.get(0);
		assertEquals(Model.board.ballAt(Position.at(2, 0, 0)), ball);

		List<Position> positionsToRise = Model.getPositionsToRise(ball);

		assertEquals(1, positionsToRise.size());
		assertEquals(Position.at(0, 0, 1), positionsToRise.get(0));
	}

	public void testIsMountable() {
		MainTest.gameSample();
		Ball ball = Model.currentPlayer.getMountableBalls().get(0);

		for (Ball actual : Model.currentPlayer.getMountableBalls()) {
			if (actual.position == Position.at(0, 0, 0))
				ball = actual;
		}

		assertTrue(ball.isMountable());
	}

	public void testGetMountableBalls() {
		MainTest.gameSample();

		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */

		for (Ball ball : Model.currentPlayer.getMountableBalls()) {
			System.out.println(ball.position);
		}

		assertEquals(1, Model.currentPlayer.getMountableBalls().size());

		assertEquals(Model.board.ballAt(Position.at(2, 2, 0)), Model.currentPlayer.getMountableBalls().get(0));
	}

	public void testGetPositionsToRise() {

		MainTest.gameSample();

		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */

		List<Position> positionToRise = Model.getPositionsToRise(Model.board.ballAt(Position.at(2, 2, 0)));
		assertEquals(1, positionToRise.size());

		assertEquals(Position.at(1, 0, 1), positionToRise.get(0));

		assertEquals(0, Model.getPositionsToRise(Model.board.ballAt(Position.at(2, 0, 0))).size());
	}

}
