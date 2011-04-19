package pylos.test;

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
		assertFalse(Model.canPlaceBallAt(Position.at(1, 0, 1)));
	}

	public void testAccessibleBalls() {

	}

	public void testGetPositionsToRise() {

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
	 * xxxx
	 * xx0x	xxx
	 * 00xx	xxx
	 * 000x	0xx
	 */
	public void gameSample() {
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 1));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 2, 0));
	}
}
