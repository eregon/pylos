package pylos.test;

import java.util.LinkedList;
import java.util.List;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class MainTest extends PylosTestCase {

	public void testInit() {
		assertTrue(Model.board.isEmpty());
	}

	public void testAnyBallAt() {
		List<Position> balls = gameSample();
		for (Position pos : Position.all) {
			assertEquals(balls.contains(pos), Model.board.anyBallAt(pos));
		}
	}

	public void testIsBoardEmpty() {
		assertTrue(Model.board.isEmpty());
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		assertFalse(Model.board.isEmpty());
	}

	public void testGetPositionBalls() {
		List<Position> expected = gameSample();

		assertEquals(10, Model.getPositionBalls().size());
		for (Position actual : Model.getPositionBalls()) {
			assertFalse(expected.contains(actual));
		}
	}

	public void testBallAt() {

	}

	public void testCanPlaceBallAt() {
		assertTrue(Model.canPlaceBallAt(Position.at(0, 0, 0)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1))); // plateau vide donc pas de sens placer boule z = 1

		List<Position> balls = gameSample();
		for (Position pos : balls) {
			assertFalse(Model.canPlaceBallAt(pos));
		}
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 2)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 3)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 2, 0)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 0, 1)));
	}

	public void testAccessibleBalls() {

	}

	public void testGetMountableBalls() {
		gameSample();

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

	public void testIsMountable() {
		gameSample();
		Ball ball = Model.currentPlayer.getMountableBalls().get(0);

		for (Ball actual : Model.currentPlayer.getMountableBalls()) {
			if (actual.position == Position.at(0, 0, 0))
				ball = actual;
		}

		assertTrue(ball.isMountable());
	}

	public void testGetPositionsToRise() {

		gameSample();

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

	public void testBallsBySideAtLevel() {

	}

	public void testGetAllPositionsForLevelLineByLine() {

	}

	public void testGetAllPositionForLevelSquareBySquare() {

	}

	public void testIsWinner() {
		assertFalse(Model.isWinner());

		Model.currentPlayer.putBallOnBoard(Position.top);
		assertTrue(Model.isWinner());
	}

	public void testPlaceAt(Position position) {
		ball.placeAt(Position.at(0, 0, 0));
		ball.placeAt(Position.at(1, 2, 2));
		assertFalse(Model.board.anyBallAt(Position.at(0, 0, 0)));
		assertFalse(Model.board.anyBallAt(Position.at(1, 2, 2)));

		gameSample();
		ball.placeAt(Position.at(2, 2, 0));
		assertFalse(Model.board.anyBallAt(Position.at(2, 2, 0)));
	}

	/**
	 * ooo.
	 * ooo. o..
	 * ..o. ...
	 * .... ...
	 */
	public List<Position> gameSample() {
		int[][] positions = new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 0 }, { 1, 0, 0 }, { 0, 0, 1 }, { 2, 0, 0 }, { 2, 2, 0 }, { 2, 1, 0 } };
		List<Position> list = new LinkedList<Position>();
		for (int[] coords : positions) {
			Position pos = Position.at(coords[0], coords[1], coords[2]);
			Model.currentPlayer.putBallOnBoard(pos);
			list.add(pos);
		}
		return list;
	}
}
