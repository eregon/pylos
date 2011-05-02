package pylos.test;

import pylos.model.Model;
import pylos.model.Position;

public class AnyLineOrSquareTest extends PylosTestCase {

	public void testAnySquare() {
		MainTest.gameSample();

		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */

		assertTrue(Model.currentPlayer.anyLineOrSquare(Position.at(0, 0, 0)));
		assertTrue(Model.currentPlayer.anyLineOrSquare(Position.at(2, 1, 0)));
		assertFalse(Model.currentPlayer.anyLineOrSquare(Position.at(2, 2, 0)));
	}
}
