package pylos.test;

import pylos.ai.AlphabetaAi;

public class AiTest extends PylosTestCase {

	public void testAi() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		for (int i = 0; i < 8; i++) {
			AlphabetaAi.AI();
		}

	}

}
