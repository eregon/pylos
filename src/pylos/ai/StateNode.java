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

	private Ply getRandomMove() {
		return successors().nextElement().ply;
	}

	public Enumeration<StateNode> successors() {
		return new StateIterator(state);
	}
}
