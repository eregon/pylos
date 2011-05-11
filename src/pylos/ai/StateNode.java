package pylos.ai;

import java.util.Enumeration;

import pylos.ai.move.Ply;

public class StateNode {
	public Ply ply;
	public State state;
	public int Alpha;
	public int Beta;

	public StateNode(Ply ply, State state) {
		this.ply = ply;
		this.state = state;
	}

	public Ply getBestMove(int depth) {
		Enumeration<StateNode> sne = successors();
		StateNode current = null;
		StateNode best = null;

		while (sne.hasMoreElements()) {
			current = sne.nextElement();
		}

		return best != null ? best.ply : null;
	}

	public Enumeration<StateNode> successors() {
		return new StateIterator(state);
	}
}
