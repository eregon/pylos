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
		State state = new State(s);
		state.swichPlayers();
		return apply(s);
	}

	public State apply(State s) { // at and removes ply can be null
		State state = new State(s);
		if (at != null) {
			state.state[at.z][at.y][at.x] = state.currentPlayer;
			state.ballOnSide[s.currentPlayer - 1]--;
		}
		if (removes != null) {
			for (Position remove : removes) {
				Position p = remove;
				state.state[p.z][p.y][p.x] = 0;
				state.ballOnSide[state.currentPlayer - 1]++;
			}
		}
		return state;
	}

	public void makeMove() { // pourquoi ia autorisée à metter boule en 2, 2, 0 et retirer sans faire ligne ?
		if (removes != null) {
			Controller.placeAiBall(at, removes, mount);
		} else {
			Controller.placeAiBall(at, new Position[0], false);
		}
	}
}
