package pylos.ai;

import java.util.List;

public class Move {
	int[][] position = new int [2][3];
	String action;
	boolean removeStep;
	Move toRemove;
	List<Move> removables;
	
	public Move(int x, int y, int z) {	// on place une boule ou on en enleve une
		position[0][0] = x;
		position[0][1] = y;
		position[0][2] = z;
	}

	public Move(int x, int y, int z, int x2, int y2, int z2) { // on monte une boule, ou on en enleve 2
		position[0][0] = x;
		position[0][1] = y;
		position[0][2] = z;
		position[1][0] = x2;
		position[1][1] = y2;
		position[1][2] = z2;
	}

	public Move(int[][] pos, Move remove) {	// on place une boule, puis on en enleve 1 ou 2
		position = pos;
		toRemove = remove;
	}

	/**
	 * modify the fake model for AI
	 * @param gs
	 * @return
	 */
	public State doMove(State gs) {
		return gs; // param pour décrémenté obBoard au cas ou
		// TODO Auto-generated method stub
		
	}
	
	/**
	 *  do the move
	 */
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

//	public boolean anyLineOrSquareForMove() {
//		ball.placeAt(position);
//		if(Model.currentPlayer.anyLineOrSquare(position)) {
//			ball.removeFromBoard();
//			removeStep = true;
//			return removeStep;
//		}
//		ball.removeFromBoard();
//		return removeStep;
//	}
}
