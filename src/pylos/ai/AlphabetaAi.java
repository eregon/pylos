package pylos.ai;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AlphabetaAi {
	/**
	 * if depth == 0 then AI makes random moves
	 * 
	 * @param depth
	 * 
	 */

	public void AI(int depth) {
		/**
		 * get player commence tjrs par max
		 * if(depht != 0 || !endGame)
		 * Parcourir les coups possibles
		 * joue le coup >> new GameState gs
		 * calcule MAX (gs, depth - 1)
		 */
		int tmp;
		int alpha = -1000;
		int beta = 1000;
		int which = -1;
		List<GameState> betterMove = new LinkedList<GameState>();

		if (depth != 0) { // if depth = 0 play random
			GameState gs = new GameState();
			gs.generatePosibleMoves();
			Iterator<GameState> iterator = gs.iterator();
			while (iterator.hasNext()) {
				GameState tryMove = iterator.next();
				tmp = calcMinMax(tryMove, depth - 1, alpha, beta, which);
				if (alpha == tmp) {
					betterMove.add(tryMove);
				} else if (alpha < tmp) {
					alpha = tmp;
					betterMove = new LinkedList<GameState>();
					betterMove.add(tryMove);
				}
			}
			choose(betterMove).move.makeMove();
		}

	}

	private GameState choose(List<GameState> betterMove) {
		return betterMove.get(0);

	}

	private int calcMinMax(GameState gs, int depth, int alpha, int beta, int which) {

		int tmp;

		if (depth == 0 || gs.state.gameFinished()) {
			EvaluateGame.evaluate(gs);
			return gs.score;
		}

		gs.generatePosibleMoves();
		Iterator<GameState> iterator = gs.iterator();
		while (iterator.hasNext()) {
			GameState tryMove = iterator.next();
			tmp = calcMinMax(tryMove, depth - 1, alpha, beta, which * -1);
			if (which > 0 && alpha < tmp) {
				return tmp;
			}
			if (which < 0 && beta > tmp) {
				return tmp;
			}
		}

		return 0;
	}
}
