package pylos.ai;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pylos.ai.move.Mount;
import pylos.ai.move.Move;
import pylos.ai.move.Remove;
import pylos.model.Model;

public class GameState implements Iterable<GameState> {

	public State state;
	GameState parent;	// etat avant le move
	Move move;			// move effectuer depuis parent
	public List<Move> possibleMoves;
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
					int ball = state.state[z][y][x];
					if(ball == 0 && state.accessible(x, y, z)) {
						Move move = new Move(x, y, z);
						move.hasRemoveStep(state);
						possibleMoves.add(move);
					}
//					if(ball == state.currentPlayer && state.isMountable(x, y, z)) {
//						for (Mount toMount : state.addPositionToMount(x, y, z)) {
//							toMount.hasRemoveStep(state);
//							possibleMoves.add(move);
//						}
//					}
				}
			}

		}
	}
	
	public Iterator<GameState> iterator() {
		List<GameState> list = new LinkedList<GameState>();
//		State s;
//		while (!possibleMoves.isEmpty()) {
//			Move move = possibleMoves.get(0);
//			possibleMoves.remove(move);
//			if (move.removeStep) {
//				while (!move.removables.isEmpty()) {
//					Remove remove = move.removables.get(0);	// si je place dans un move un mount et fait move.do est ce que ce sera le do du move ou du mount?
//					move.removables.remove(remove);
//					move.remove = remove;
//					s = move.doMove(state);
//					s.switchPlayer();
//					list.add(new GameState(this, s, move));
//				}
//			} else {
//				s = move.doMove(state);
//				s.switchPlayer();
//				list.add(new GameState(this, s, move));
//			}
//		}
		return list.iterator();
	}
}