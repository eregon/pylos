package pylos.ai.move;

import pylos.ai.State;
import pylos.model.Model;
import pylos.model.Position;

public class Ply {

	public Position at;
	public Position[] removes;

	public Ply(Position at, Position[] removes) {
		this.at = at;
		this.removes = removes;
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
		state.currentPlayer = (byte) (state.currentPlayer == 1 ? 2 : 1);
		return state;
	}

	public void makeMove() {
		System.out.println("ai place a ball at : " + at);
		Model.currentPlayer.putBallOnBoard(at);
		if (removes != null) {
			for (Position remove : removes) {
				System.out.println("ai removes a ball at : " + remove);
				Model.currentPlayer.removeBall(Model.board.ballAt(remove));
			}
		}
	}
}
