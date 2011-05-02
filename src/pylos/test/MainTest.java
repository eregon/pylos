package pylos.test;

import java.util.LinkedList;
import java.util.List;

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

	public void testAccessibleBalls() {
		List<Position> balls = gameSample();

		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */

		List<List<Position>> accessibleBalls = new LinkedList<List<Position>>();
		for (int i = 0; i < Model.LEVELS; i++) {
			accessibleBalls.add(Model.accessibleBalls(i));
		}

		for (List<Position> list : accessibleBalls) {
			for (Position accessible : list) {
				assertFalse(balls.contains(accessible));
			}
		}
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
	public static List<Position> gameSample() {
		int[][] positions = new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 0 }, { 1, 0, 0 }, { 0, 0, 1 }, { 2, 0, 0 }, { 2, 2, 0 }, { 2, 1, 0 } };
		List<Position> list = new LinkedList<Position>();
		for (int[] coords : positions) {
			Position pos = Position.at(coords[0], coords[1], coords[2]);
			Model.currentPlayer.putBallOnBoard(pos);
			list.add(pos);
		}
		return list;
	}

	/**
	 * o..o
	 * ooo. ...
	 * ooo. oo. ..
	 * ooo. oo. .. .
	 */
	public static List<Position> complexGameSample() {
		int[][] positions = new int[][] { { 0, 0, 0 }, { 3, 0, 0 }, { 0, 1, 0 }, { 1, 1, 0 }, { 2, 1, 0 }, { 0, 2, 0 }, { 1, 2, 0 }, { 2, 2, 0 }, { 0, 3, 0 }, { 1, 3, 0 }, { 2, 3, 0 }, { 0, 1, 1 },
				{ 1, 1, 1 }, { 0, 2, 1 }, { 1, 2, 1 } };
		List<Position> list = new LinkedList<Position>();
		for (int[] coords : positions) {
			Position pos = Position.at(coords[0], coords[1], coords[2]);
			Model.currentPlayer.putBallOnBoard(pos);
			list.add(pos);
		}
		return list;
	}
}