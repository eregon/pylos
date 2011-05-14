package pylos.ai;

import java.util.Enumeration;

public class AlphaBeta {
	static int depth = 1;
	final static boolean MAX_PLAYER = true;
	final static boolean MIN_PLAYER = false;

	public static void AI() {

		State s = new State();
		StateNode node = new StateNode(null, s);
		if (s.ballOnSide[0] + s.ballOnSide[1] <= 20)
			depth = 4;
		else
			depth = 2;
		alphaBeta(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, MAX_PLAYER);
		node.getBestMove().makeMove();// attention NPE
	}

	public static int alphaBeta(StateNode node, int depth, int a, int b, boolean player) {
		if (depth == 0 || node.state.endGame()) {
			return node.state.evaluate();
		}

		Enumeration<StateNode> children = node.children();
		StateNode child;
		int alpha = a, beta = b;

		if (player == MAX_PLAYER) {
			while (children.hasMoreElements()) {
				child = children.nextElement();
				alpha = max(alpha, alphaBeta(child, depth - 1, alpha, beta, !player), node, child);
				if (beta <= alpha)
					break; // beta cut-off
			}
			return alpha;
		} else {
			while (children.hasMoreElements()) {
				child = children.nextElement();
				beta = min(beta, alphaBeta(child, depth - 1, alpha, beta, !player), node, child);
				if (beta <= alpha)
					break; // alpha cut-off
			}
			return beta;
		}
	}

	private static int min(int beta, int alphaBeta, StateNode node, StateNode currentChild) {
		boolean isMoveBetter = beta > alphaBeta;
		if (isMoveBetter)
			node.bestMove = currentChild;
		return isMoveBetter ? alphaBeta : beta;
	}

	private static int max(int alpha, int alphaBeta, StateNode node, StateNode currentChild) {
		boolean isMoveBetter = alpha < alphaBeta;
		if (isMoveBetter)
			node.bestMove = currentChild;
		return isMoveBetter ? alphaBeta : alpha;
	}
}