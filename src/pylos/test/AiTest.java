package pylos.test;

import pylos.ai.State;

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

	public void testEval() {
		MainTest.gameSample();
		State s = new State();
		System.out.println(s.evaluate());
	}
}
