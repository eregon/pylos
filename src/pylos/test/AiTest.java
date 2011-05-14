package pylos.test;

import pylos.ai.State;

public class AiTest extends PylosTestCase {

	// public void testAi() {
	// MainTest.gameSample();
	// /**
	// * ooo.
	// * ooo. o..
	// * ..o. ...
	// * .... ...
	// */
	// for (int i = 0; i < 8; i++) {
	// AlphabetaAi.AI();
	// }
	// }

	public void testEval() {
		MainTest.gameSample();
		State s = new State();
		System.out.println(s.evaluate());
	}
}
