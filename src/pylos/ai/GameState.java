package pylos.ai;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pylos.ai.move.Mount;
import pylos.ai.move.Move;
import pylos.ai.move.Remove;
import pylos.model.Model;
import pylos.model.Position;

public class GameState implements Iterable<GameState> {

	public State state;
	GameState parent;	// etat avant le move
	public Move move;			// move effectuer depuis parent
	public List<Move> possibleMoves;
	public int score;

	public GameState() {	
		state = new State();
		possibleMoves = new LinkedList<Move>();
		EvaluateGame.evaluate(this);
	}
	
	public GameState(GameState p, State s, Move m) {
		state = s;
		parent = p;
		move = m;
		possibleMoves = new LinkedList<Move>();
		EvaluateGame.evaluate(this);
	}

	public void generatePosibleMoves() {
		for (int z = 0; z < Model.LEVELS; z++) {
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					int ball = state.state[z][y][x];
					if(ball == 0 && state.accessible(x, y, z)) {
						Move move = new Move(x, y, z);
						move.hasRemoveStep(state);
						possibleMoves.add(move);
					} else if(ball == state.currentPlayer && state.isMountable(Position.at(x, y, z))) {
						for (Move toMount : state.addPositionToMount(Position.at(x, y, z))) {
							toMount.hasRemoveStep(state);
							possibleMoves.add(toMount);
						}
					}
				}
			}

		}
	}
	
	public Iterator<GameState> iterator() {
		List<GameState> list = new LinkedList<GameState>();
		State s;
		for (Move move : possibleMoves) {	
			if (move.removeStep) {
				for (Remove r : move.removables) {
					move.remove = r;
					s = move.doMove(state);
					s.switchPlayer();
					list.add(new GameState(this, s, move));
				}
			} else {
				s = move.doMove(state);
				s.switchPlayer();
				list.add(new GameState(this, s, move));
			}
		}
		return list.iterator();
	}
}