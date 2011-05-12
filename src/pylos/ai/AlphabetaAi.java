package pylos.ai;

import java.util.Enumeration;

public class AlphabetaAi {
	/**
	 * if depth == 0 then AI makes random moves
	 * 
	 * @param depth
	 * 
	 */
	static final int depth = 2;

	public static void AI() {

		State s = new State();
		StateNode node = new StateNode(null, s);
		node.getBestMove(depth).makeMove();// attention NPE
	}

	public static int alphaBeta(int depth, int beta, int alpha, StateNode node, int which) {
		if (depth == 0) {
			return node.state.evaluate() * which;
		}

		Enumeration<StateNode> sne = node.successors();
		StateNode current = null;
		int a = alpha, b = beta;

		while (sne.hasMoreElements()) {
			current = sne.nextElement();
			int tmp = AlphabetaAi.alphaBeta(depth - 1, b, a, current, which * -1);
			if (which == 1) {
				if (beta < tmp) {
					b = tmp;
				}
				return beta;
			} else if (alpha > tmp) {
				a = tmp;
			}
			return alpha;
		}
		return node.state.state[3][0][0] == node.state.currentPlayer ? 1000 : -1000;
	}
}
//		
// int tmp = 0;
// int alpha = -150;
// int beta = 150;
// int which = -1;
// List<GameState> betterMove = new LinkedList<GameState>();
//
// if (depth != 0) { // if depth = 0 play random
// gs.generatePosibleMoves();
// Iterator<GameState> iterator = gs.iterator();
// while (iterator.hasNext()) {
// GameState tryMove = iterator.next();
// tmp = calcMinMax(tryMove, depth - 1, alpha, beta, which);
// if (alpha == tmp) {
// betterMove.add(tryMove);
// } else if (alpha < tmp) {
// alpha = tmp;
// betterMove = new LinkedList<GameState>();
// betterMove.add(tryMove);
// }
// }
// choose(betterMove).move.makeMove();
// }
//
// }
//
// private static GameState choose(List<GameState> betterMove) {
// return betterMove.get(0);
//
// }
//
// private static int calcMinMax(GameState gs, int depth, int alpha, int beta, int which) {
//
// int tmp;
//
// if (depth == 0 || gs.state.gameFinished()) {
// // gs.state.printState();
// // System.out.println("score : " + gs.score + ", ball 1 = " + gs.state.ballOnSide[0] + ", ball 2 = " +
// // gs.state.ballOnSide[1]);
// return gs.score;
// }
//
// gs.generatePosibleMoves();
// Iterator<GameState> iterator = gs.iterator();
// while (iterator.hasNext()) {
// GameState tryMove = iterator.next();
// tmp = calcMinMax(tryMove, depth - 1, alpha, beta, which * -1);
// if (which > 0 && alpha < tmp) {
// return tmp;
// }
// if (which < 0 && beta > tmp) {
// return tmp;
// }
// }
//
// return 0;
// }
// }
