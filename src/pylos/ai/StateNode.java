package pylos.ai;

import java.util.Enumeration;

import pylos.ai.move.Ply;

public class StateNode {
	public Ply ply;
	public State state;
	public int alpha;
	public int beta;

	public StateNode(Ply ply, State state) {
		this.ply = ply;
		this.state = state;
		alpha = state.maxVal;
		beta = state.minVal;
	}

	public Ply getBestMove(int depth) {
		if (depth == 0) {
			return getRandomMove();
		}

		Enumeration<StateNode> sne = successors();
		StateNode current = null;
		StateNode best = null;
		int which = -1;

		while (sne.hasMoreElements()) {
			current = sne.nextElement();
			int tmp = AlphabetaAi.alphaBeta(depth - 1, beta, alpha, current, which);
			if (beta < tmp) {
				beta = tmp;
				best = current;
			}
		}
		best.state.printState();
		return best != null ? best.ply : null;
	}

	private Ply getRandomMove() {
		return successors().nextElement().ply;
	}

	public Enumeration<StateNode> successors() {
		return new StateIterator(state);
	}
}
