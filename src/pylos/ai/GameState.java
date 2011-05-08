package pylos.ai;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pylos.model.Model;

public class GameState implements Iterable<GameState> {

	State state;
	GameState parent;	// etat avant le move
	Move move;			// move effectuer depuis parent
	List<Move> possibleMoves;
	private int score;

	public GameState() {	
		state = new State();
		possibleMoves = new LinkedList<Move>();
		score = EvaluateGame.evaluate(state);
	}
	
	public GameState(GameState p, State s, Move m) {
		state = s;
		parent = p;
		move = m;
		possibleMoves = new LinkedList<Move>();
		score = EvaluateGame.evaluate(state);
	}

	public void addScore(int a) {
		score += a;
	}

	public void generatePosibleMoves() {
		for (int z = 0; z < Model.LEVELS; z++) {
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					if(state.state[z][y][x] == state.currentPlayer && state.isMountable(x, y, z))
						possibleMoves.add(state.addPositionToMount(x, y, z));
					if(state.state[z][y][x] == 0 && state.accessible())
						possibleMoves.add(new Move(x, y, z));
				}
			}
		}
	}


	public Iterator<GameState> iterator() {
		List<GameState> list = new LinkedList<GameState>();
		State s;
		while (!possibleMoves.isEmpty()) {
			Move move = possibleMoves.get(0);
			possibleMoves.remove(move);
			if (move.removeStep) {
				while(!move.removables.isEmpty()) {
					Move remove = move.removables.get(0);
					move.removables.remove(remove);
					Move moveAndRemove = new Move(move.position, remove);
					s = moveAndRemove.doMove(state);
					list.add(new GameState(this, s, moveAndRemove));
				}
			} else {
				s = move.doMove(state);
				list.add(new GameState(this, s, move));
			}
		}
		return list.iterator();
	}
}