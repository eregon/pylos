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