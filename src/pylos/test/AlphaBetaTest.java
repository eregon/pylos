package pylos.test;

import pylos.ai.AlphabetaAi;
import pylos.ai.GameState;

public class AlphaBetaTest extends PylosTestCase {
	public void testAlphaBeta() {
		// MainTest.gameSample();
		// /**
		// * ooo.
		// * ooo. o..
		// * ..o. ...
		// * .... ...
		// */
		for (int i = 0; i < 45; i++) {
			AlphabetaAi.AI(3);
		}
		GameState gs = new GameState();
		gs.state.printState();

	}

	// public void testAi() {
	// MainTest.gameSample();
	// /**
	// * ooo.
	// * ooo. o..
	// * ..o. ...
	// * .... ...
	// */
	// GameState gs = new GameState();
	// gs.generatePosibleMoves();
	// Iterator<GameState> iterator = gs.iterator();
	// GameState ok = null;
	// GameState tmp;
	// int alpha = 0;
	// while (iterator.hasNext()) {
	// tmp = iterator.next();
	// EvaluateGame.evaluate(tmp);
	// // if (tmp.score > alpha) {
	// // System.out.println("s = " + tmp.score + " game : ");
	// // tmp.state.printState();
	// // System.out.println("ball on side current J: " + gs.state.ballOnSide[0]
	// // + " ball on side opponnent: " +
	// // gs.state.ballOnSide[1]);
	// // }
	// }
	// }
}
