package pylos.test;

import java.util.List;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Player;
import pylos.model.Position;

public class MountBallTest extends PylosTestCase {
	public void testMountBall() {
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

		List<Position> positionsToMount = Model.getPositionsToMount(ball);

		assertEquals(1, positionsToMount.size());
		assertEquals(Position.at(0, 0, 1), positionsToMount.get(0));
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

		assertEquals(1, Model.currentPlayer.getMountableBalls().size());

		assertEquals(Model.board.ballAt(Position.at(2, 2, 0)), Model.currentPlayer.getMountableBalls().get(0));
	}

	public void testGetPositionsToMount() {

		MainTest.gameSample();

		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */

		List<Position> positionToMount = Model.getPositionsToMount(Model.board.ballAt(Position.at(2, 2, 0)));
		assertEquals(1, positionToMount.size());

		assertEquals(Position.at(1, 0, 1), positionToMount.get(0));

		assertEquals(0, Model.getPositionsToMount(Model.board.ballAt(Position.at(2, 0, 0))).size());
	}

}
