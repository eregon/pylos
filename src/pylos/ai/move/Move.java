package pylos.ai.move;

import java.util.LinkedList;
import java.util.List;

import pylos.ai.State;
import pylos.model.Model;

public class Move {
	public int[] position = new int [3];
	public boolean removeStep = false;
	public Remove remove;
	public List<Remove> removables;
	
	public Move(int x, int y, int z) {	// on place une boule ou on en enleve une
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
	
	public Move(int[] pos) {
		position = pos;
	}

	/**
	 * modify the fake model for AI
	 * @param gs
	 * @return
	 */
	public State doMove(State s) {  
		State state = new State(s);
		state.state[position[2]][position[1]][position[0]] = (byte)state.currentPlayer;
		state.ballOnSide[state.currentPlayer -1] --;
		if(remove != null) {
			state = remove.doMove(state);
		}
		return state;
	}
	
	/**
	 *  do the move
	 */
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isCurrentMove(int x, int y, int z) {
		return x == position[0] && y == position[1] && z == position[2];
	}

	public void hasRemoveStep(State state) {
		if(anyLine(state) || (anySquare(state))) {
			removeStep = true;
			generateRemovables(state);
		} else {
			removeStep = false;
		}
	}
	
	private void generateRemovables(State state) {
		removables = new LinkedList<Remove>();
		for (int z = 0; z < Model.LEVELS; z++) {
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					if(isCurrentMove(x, y, z) || state.isRemovableByCurrentPlayer(x, y, z))
						removables.add(new Remove(x, y, z));
				}
				
			}
		}
		List<Remove> tmp = state.copyList(removables);
		List<Remove> ignore = new LinkedList<Remove>();
		for (Remove r : tmp) {
			for (int z = 0; z < Model.LEVELS; z++) {
				for (int y = 0; y < Model.LEVELS - z; y++) {
					for (int x = 0; x < Model.LEVELS - z; x++) {
						if(!state.hasAlreadyBeenRemoved(r, x, y, z) && !state.isConteined(ignore, x, y, z) /*&& state.isAlreadyContened(removables, r, x, y, z)*/) {
							if(isCurrentMove(x, y, z) || state.isRemovableByCurrentPlayerIgnoring(x, y, z, r.position))
								removables.add(new Remove(r.position, x, y, z));
						}
					}
					
				}
			}
			ignore.add(r);
		}
	}

	/**
	 * ne test pas si 4 boules sont en dessous, mais en théorie cette méthode n'est appliqué qu'à des positions 
	 * valide, donc si on vérifie si elle fait un carré ou une ligne avec une position non valide à coté 
	 * (sans les 4 boules sur lesquelles elle se tiens) il n'y aura simplement pas de boules à cette 
	 * endroit et donc on aura false comme résultat, pas de probleme
	 * @param state
	 * @return
	 */
	private boolean anySquare(State state) {
		if (position[2] >= 2)
			return false;
		for (List<int[]> square : state.fourSquare(position)) {
			boolean validSquare = true;
			for (int[] p : square) {
				int ball = state.state[p[2]][p[1]][p[0]];
				boolean equals = p[0] == position[0] && p[1] == position[1] && p[2] == position[2];
				if (!equals && ball == 0 || ball != state.currentPlayer && ball != 0) {
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
		if(position[2] >= 2)
			return false;
		for(List<int[]> line : state.lines(position)) {
			boolean validLine = true;
			for (int[] p : line) {
				int ball = state.state[p[2]][p[1]][p[0]];
				boolean equals = p[0] == position[0] && p[1] == position[1] && p[2] == position[2];
				if (!equals && ball == 0|| ball != state.currentPlayer && ball != 0) {
					validLine = false;
					break;
				}
			}
			if (validLine)
				return true;
		}
		return false;
	}
	
	public String toString() {
		return "{ " + position[0] + ", " + position[1] + ", " + position[2] + " }"; 
	}
}