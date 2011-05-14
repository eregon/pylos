package pylos.ai;

import java.util.Enumeration;

import pylos.ai.move.Ply;

public class StateNode {
	public Ply ply;
	public State state;
	public StateNode bestMove;

	public StateNode(Ply ply, State state) {
		this.ply = ply;
		this.state = state;
	}

	public Ply getBestMove() {
		return bestMove != null ? bestMove.ply : null;
	}

	public Enumeration<StateNode> children() {
		return new StateIterator(state);
	}
}
