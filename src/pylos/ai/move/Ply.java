package pylos.ai.move;

import pylos.ai.State;
import pylos.controller.Controller;
import pylos.model.Position;

public class Ply {

	public Position placeAt = null;
	public Position[] removes = null;
	public boolean mount = false;

	public Ply(Position at, Position[] removes) {
		placeAt = at;
		this.removes = removes;
	}

	public State applyAndSwitchPlayer(State s) {
		State state = apply(s);
		state.swichPlayers();
		return state;
	}

	/**
	 * Make a copy of the State, and apply this ply
	 */
	public State apply(State s) { // at and removes ply can be null
		State state = new State(s);
		if (placeAt != null) {
			state.state[placeAt.z][placeAt.y][placeAt.x] = state.currentPlayer;
			state.ballOnSide[s.currentPlayer]--;
		}
		if (removes != null) {
			for (Position remove : removes) {
				state.state[remove.z][remove.y][remove.x] = 0;
				state.ballOnSide[state.currentPlayer]++;
			}
		}
		return state;
	}

	public void makeMove() {
		Controller.placeAIBall(placeAt, removes, mount);
	}
}
