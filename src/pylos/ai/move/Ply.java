package pylos.ai.move;

import pylos.ai.State;
import pylos.controller.Controller;
import pylos.model.Position;

public class Ply {

	public Position at;
	public Position[] removes;
	public boolean mount = false;

	public Ply(Position at, Position[] removes) {
		this.at = at;
		this.removes = removes;
	}

	public State applyAndSwitchPlayer(State s) {
		State state = apply(s);
		state.swichPlayers();
		return state;
	}

	public State apply(State s) { // at and removes ply can be null
		State state = new State(s);
		if (at != null) {
			state.state[at.z][at.y][at.x] = state.currentPlayer;
			state.ballOnSide[s.currentPlayer]--;
		}
		if (removes != null) {
			for (Position remove : removes) {
				Position p = remove;
				state.state[p.z][p.y][p.x] = 0;
				state.ballOnSide[state.currentPlayer]++;
			}
		}
		return state;
	}

	public void makeMove() {
		Controller.placeAIBall(at, removes, mount);
	}
}
