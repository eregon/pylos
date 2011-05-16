package pylos.ai;

import java.util.Enumeration;

import pylos.Config;
import pylos.Pylos;
import pylos.controller.Controller;

public class AlphaBeta {
	final static boolean MAX_PLAYER = true;
	final static boolean MIN_PLAYER = false;

	public static void AI() {
		State s = new State();
		final StateNode node = new StateNode(null, s);
		final int depth = (s.ballOnSide[State.human] + s.ballOnSide[State.ai] > 20) ? Config.AI_DEPTH + 2 : Config.AI_DEPTH;

		// We do not want AI to block the main thread
		new Thread(new Runnable() {
			public void run() {
				long t = System.currentTimeMillis();
				alphaBeta(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, MAX_PLAYER);
				Pylos.AIlogger.info("alphaBeta took " + (System.currentTimeMillis() - t) + " ms");
				node.getBestMove().makeMove();
				Controller.finishTurn();
			}
		}).start();
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

	private static int min(int beta, int alphaBeta, StateNode node, StateNode child) {
		if (beta > alphaBeta) {
			node.bestMove = child;
			return alphaBeta;
		} else {
			return beta;
		}
	}

	private static int max(int alpha, int alphaBeta, StateNode node, StateNode child) {
		if (alpha < alphaBeta) {
			node.bestMove = child;
			return alphaBeta;
		} else {
			return alpha;
		}
	}
}