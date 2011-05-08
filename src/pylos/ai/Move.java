package pylos.ai;

import java.util.List;

import pylos.model.Model;

public class Move {
	int[][] position = new int [2][3];
	String action;
	boolean removeStep = false;
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
	public State doMove(State gs) {  // not forget to switch player
		return gs; // param pour décrémenté obBoard au cas ou
		// TODO Auto-generated method stub
		
	}
	
	/**
	 *  do the move
	 */
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

	public void hasRemoveStep(State state) {
		if(anyLine(state) || anySquare(state)) {
			removeStep = true;
			for (int z = 0; z < Model.LEVELS; z++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					for (int y = 0; y < Model.LEVELS - z; y++) {
						if(state.isRemovable(x, y, z))
							removables.add(new Move(x, y, z));
						for (int iz = 0; iz < Model.LEVELS; iz++) {
							for (int ix = 0; ix < Model.LEVELS - iz; ix++) {
								for (int iy = 0; iy < Model.LEVELS - iz; iy++) {
									if(state.isRemovable(ix, iy, iz) && x != ix && y != iy && z != iz)
										removables.add(new Move(x, y, z, ix, iy, iz));
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean anySquare(State state) {
		if (position[0][2] >= 2)
			return false;
		for (List<int[]> square : state.fourSquare(position[0])) {
			boolean validSquare = true;
			for (int[] p : square) {
				int ball = state.state[p[2]][p[1]][p[0]];
				boolean equals = p[0] == position[0][0] && p[1] == position[0][1] && p[2] == position[0][2];
				if (ball == 0 || !equals || ball != state.currentPlayer) {
					validSquare = false;
					break;
				}
			}
			if (validSquare)
				return true;
		}
		return false;
	}

	private boolean anyLine(State state) {
		if(position[0][2] >= 2)
			return false;
		for(List<int[]> line : state.lines(position[0])) {
			boolean validLine = true;
			for (int[] p : line) {
				int ball = state.state[p[2]][p[1]][p[0]];
				boolean equals = p[0] == position[0][0] && p[1] == position[0][1] && p[2] == position[0][2];
				if (ball == 0 || !equals || ball != state.currentPlayer) {
					validLine = false;
					break;
				}
			}
			if (validLine)
				return true;
		}
		return false;
	}
}