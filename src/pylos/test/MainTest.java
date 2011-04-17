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

		gameSample(false);
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 0)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1)));
		assertFalse(Model.canPlaceBallAt(Position.at(1, 0, 0)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 2, 0)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 0, 1)));
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

		gameSample(false);
		ball.placeAt(Position.at(2, 2, 0));
		assertFalse(Model.board.anyBallAt(Position.at(2, 2, 0)));
	}

	/**
	 * The parametre error is to create a game were there is an error like one ball at z = 1 and no balls under.
	 * No matter, i'll delete this if no need ...
	 * 
	 * @param error
	 */
	public void gameSample(boolean error) {
		if (error) {
			Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
			Model.currentPlayer.putBallOnBoard(Position.at(1, 1, 1));
			Model.currentPlayer.putBallOnBoard(Position.at(1, 2, 1));
			Model.currentPlayer.putBallOnBoard(Position.at(0, 1, 1));
			Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 1));
		} else {
			Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
			Model.currentPlayer.putBallOnBoard(Position.at(0, 1, 0));
			Model.currentPlayer.putBallOnBoard(Position.at(1, 1, 0));
			Model.currentPlayer.putBallOnBoard(Position.at(1, 0, 0));
			Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 1));
			Model.currentPlayer.putBallOnBoard(Position.at(2, 0, 0));
			Model.currentPlayer.putBallOnBoard(Position.at(2, 1, 0));
		}
	}
}
