package pylos.ai;

import java.util.Enumeration;

public class AlphabetaAi {
	static final int depth = 2;
	final static boolean MAX_PLAYER = true;
	final static boolean MIN_PLAYER = false;

	public static void AI() {

		State s = new State();
		s.printState();
		StateNode node = new StateNode(null, s);
		alphaBeta(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, MAX_PLAYER);
		node.getBestMove().makeMove();// attention NPE
	}

	public static int alphaBeta(StateNode node, int depth, int a, int b, boolean player) {
		if (depth == 0 || node.state.endGame()) {
			return node.state.evaluate();
		}

		Enumeration<StateNode> sne = node.successors();
		StateNode current = null;
		int alpha = a, beta = b;

		if (player == MAX_PLAYER) {
			while (sne.hasMoreElements()) {
				current = sne.nextElement();
				current.state.printState();
				alpha = max(alpha, alphaBeta(current, depth - 1, alpha, beta, !player), node, current);
				if (beta <= alpha)
					break; // beta cut-off
			}
			return alpha;
		} else {
			while (sne.hasMoreElements()) {
				current = sne.nextElement();
				current.state.printState();
				beta = min(beta, alphaBeta(current, depth - 1, alpha, beta, !player), node, current);
				if (beta <= alpha)
					break; // alpha cut-off
			}
			return beta;
		}
	}

	private static int min(int beta, int alphaBeta, StateNode node, StateNode currentSon) {
		boolean isMoveBetter = beta > alphaBeta;
		if (isMoveBetter)
			node.bestMove = currentSon;
		return isMoveBetter ? alphaBeta : beta;
	}

	private static int max(int alpha, int alphaBeta, StateNode node, StateNode currentSon) {
		boolean isMoveBetter = alpha < alphaBeta;
		if (isMoveBetter)
			node.bestMove = currentSon;
		return isMoveBetter ? alphaBeta : alpha;
	}
}