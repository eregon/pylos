package pylos.test;

import pylos.ai.State;
import pylos.model.Model;
import pylos.model.Position;

public class AITest extends PylosTestCase {

	// public void testAI() {
	// MainTest.gameSample();
	// /**
	// * ooo.
	// * ooo. o..
	// * ..o. ...
	// * .... ...
	// */
	// for (int i = 0; i < 8; i++) {
	// AlphaBeta.AI();
	// }
	// }

	// public void testEval() {
	// MainTest.gameSample();
	// State s = new State();
	// // System.out.println(s.evaluate());
	// }

	public void testEval2() {
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(2, 1, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 2, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(1, 2, 0));
		Model.currentPlayer.putBallOnBoard(Position.at(0, 3, 0));
		Model.otherPlayer().putBallOnBoard(Position.at(3, 0, 0));
		Model.otherPlayer().putBallOnBoard(Position.at(1, 1, 0));
		Model.otherPlayer().putBallOnBoard(Position.at(1, 3, 0));
		Model.otherPlayer().putBallOnBoard(Position.at(2, 3, 0));
		Model.otherPlayer().putBallOnBoard(Position.at(0, 2, 1));
		/**
		 * o..x
		 * .xo. ...
		 * oo.. ...
		 * oxx. x..
		 */
		State s = new State();
		System.out.println(s.evaluate());
	}

	// public void testGetLines() {
	// State s = new State();
	// assertEquals(18, s.getLines().size());
	// assertEquals(13, s.getSquare().size());
	// }
	//
	// public void testCountPoint() {
	// MainTest.gameSample();
	// /**
	// * ooo.
	// * ooo. o..
	// * ..o. ...
	// * .... ...
	// */
	// State s = new State();
	// assertEquals(70, s.anyLinesOrSquarePoint(s.currentPlayer));
	// assertEquals(2, s.evaluate());
	// }
	public void testEval() {
		MainTest.gameSample();
		State s = new State();
		System.out.println(s.evaluate());
	}
}
