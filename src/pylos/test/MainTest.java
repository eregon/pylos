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
		assertFalse(Model.board.anyBallAt(Position.at(0, 0, 0)));

		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		assertTrue(Model.board.anyBallAt(Position.at(0, 0, 0)));
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
			assertFalse(actual.isContained(expected));
		}
	}

	public void testBallAt() {

	}

	public void testCanPlaceBallAt() {
		assertTrue(Model.canPlaceBallAt(Position.at(0, 0, 0)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1))); // plateau vide donc pas de sens placer boule z = 1

		gameSample();
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 0)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1)));
		assertFalse(Model.canPlaceBallAt(Position.at(1, 0, 0)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 2, 0)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 0, 1)));
	}

	public void testAccessibleBalls() {

	}

	public void testGetMountableBalls() {
		List<Position> canRise = gameSample();
		canRise.remove(2);
		canRise.remove(3);
		canRise.remove(4);

		assertEquals(5, Model.currentPlayer.getMountableBalls().size());

		for (Ball actual : Model.currentPlayer.getMountableBalls()) {
			assertTrue(actual.position.isContained(canRise));
		}
	}

	public void testIsMountable() {
		gameSample();
		Ball ball = Model.currentPlayer.getMountableBalls().get(0);

		for (Ball actual : Model.currentPlayer.getMountableBalls()) {
			if (actual.position.equals(Position.at(0, 0, 0)))
				ball = actual;
		}

		assertTrue(ball.isMountable());
	}

	public void testGetPositionsToRise() {
		Ball ball = Model.currentPlayer.getMountableBalls().get(0);

		for (Ball actual : Model.currentPlayer.getMountableBalls()) {
			if (actual.position.equals(Position.at(0, 0, 0)))
				ball = actual;
		}

		assertEquals(1, Model.getPositionsToRise(ball).size());

		for (Ball actual : Model.currentPlayer.getMountableBalls()) {
			if (actual.position.equals(Position.at(2, 2, 0)))
				ball = actual;
		}

		assertEquals(1, Model.getPositionsToRise(ball).size());
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
	 * oooo
	 * oo.o ooo
	 * ...o ooo
	 * ...o .oo
	 */
	public List<Position> gameSample() {
		List<Position> pos = new LinkedList<Position>();
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0)); // index 0
		pos.add(Position.at(0, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 1, 0)); // index 1
		pos.add(Position.at(0, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 1, 0)); // index 2
		pos.add(Position.at(1, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 0, 0)); // index 3
		pos.add(Position.at(1, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 1)); // index 4
		pos.add(Position.at(0, 0, 1));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 0, 0)); // index 5
		pos.add(Position.at(2, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 2, 0)); // index 6
		pos.add(Position.at(2, 2, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 1, 0)); // index 7
		pos.add(Position.at(2, 1, 0));
		return pos;
	}
}
