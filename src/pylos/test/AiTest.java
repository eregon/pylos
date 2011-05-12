package pylos.test;

import pylos.ai.AlphabetaAi;
import pylos.ai.State;

public class AiTest extends PylosTestCase {

	public void testAi() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		State s = new State();
		for (int i = 0; i < 3; i++) {
			AlphabetaAi.AI();
		}

	}

}
