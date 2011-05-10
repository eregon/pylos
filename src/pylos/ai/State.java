package pylos.ai;

import java.util.LinkedList;
import java.util.List;

import pylos.ai.move.Mount;
import pylos.ai.move.Remove;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class State {
	public byte[][][] state = new byte[Model.LEVELS][][];
	public int[] ballOnSide = new int[2];
	public int currentPlayer;
	
	public State() {
		currentPlayer = Model.currentPlayer.toByte();
		ballOnSide[0] = 15;
		ballOnSide[1] = 15;
		updateFromModel();
	}
	
	public State(State s) {
		state = copy(s.state);
		ballOnSide[0] = s.ballOnSide[0];
		ballOnSide[1] = s.ballOnSide[1];
		currentPlayer = s.currentPlayer;
	}
	
	public int[] copy(int[] b) {
		int[] copy = new int[2];
		for (int i = 0; i < b.length; i++) {
			copy[i] = b[i];
		}
		return b;
	}
	
	public byte[][][] copy(byte[][][] s) {
		byte[][][] copy = new byte[Model.LEVELS][][];
		for (int z = 0; z < Model.LEVELS; z++) {
			copy[z] = new byte[Model.LEVELS - z][Model.LEVELS - z];
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					copy[z][y][x] = s[z][y][x];
				}
			}
		}
		return copy;
	}
	
	public void updateFromModel() {
		Ball ball;
		for (int z = 0; z < Model.LEVELS; z++) {
			state[z] = new byte[Model.LEVELS - z][Model.LEVELS - z];
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					ball = Model.board.ballAt(Position.at(x, y, z));
					if(ball != null) {
						ballOnSide[ball.owner.toByte() - 1] --;
					}
					state[z][y][x] = ball == null ? 0 : ball.owner.toByte();
				}
			}
		}
	}
	
	public boolean isAlreadyRemoved(Position check, Position removed) {
		return check == removed;
	}
	
	
	public boolean isRemovableByCurrentPlayerIgnoring(Position position, Position ignore) {
		if(state[position.z][position.y][position.x] == 0)
			return false;
		for(int x = position.x - 1; x <= position.x; x ++) {
			for (int y = position.y - 1; y <= position.y; y++) {
				if(Position.isValid(x, y, position.z + 1) && state[position.z + 1][y][x] != 0 && !isAlreadyRemoved(Position.at(x, y, position.z + 1), ignore))
					return false;
			}
		}
		return true;
	}
	
	public boolean isRemovableByCurrentPlayer(Position position) {
		return isRemovable(position) && state[position.z][position.y][position.x] == currentPlayer;
	}
	
	public boolean isRemovable(Position position) {
		if(state[position.z][position.y][position.x] == 0)
			return false;
		for(int x = position.x - 1; x <= position.x; x ++) {
			for (int y = position.y - 1; y <= position.y; y++) {
				if(Position.isValid(x, y, position.z + 1) && state[position.z + 1][y][x] != 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * to avoid out of bound exception
	 */

	public boolean isMountable(Position position) {
		return isRemovable(position) && !addPositionToMount(position).isEmpty();
	}

	/**
	 * on sait que la boule est montable, on veut toutes les positions 
	 * ou elle peut aller (faire attention aux removables)
	 */
	public List<Mount> addPositionToMount(Position position) {
		List<Mount> list = new LinkedList<Mount>();
		for (int z = position.z + 1; z < Model.LEVELS; z++) {
			for (int x = 0; x < Model.LEVELS - z; x++) {
				for (int y = 0; y < Model.LEVELS - z; y++) {
					if(accessibleIgnoring(Position.at(x, y, z), position)) {
						list.add(new Mount(Position.at(x, y, z), position));
					}
				}

			}

		}
		return list;
	}

	public boolean accessible(int x, int y, int z) {
		if(state[z][y][x] != 0)
			return false;
		if(z == 0)
			return true;
		for (int ix = x; ix <= x + 1; ix++) {
			for (int iy = y; iy <= y + 1; iy++) {
				if(state[z - 1][iy][ix] == 0)
					return false;
			}
		}
		return true;
	}
	
	public boolean accessibleIgnoring(Position accessible, Position ignore) {
		if(state[accessible.z][accessible.y][accessible.x] != 0)
			return accessible == ignore;
		if(accessible.z == 0)
			return true;
		for (int x = accessible.x; x <= accessible.x + 1; x++) {
			for (int y = accessible.y; y <= accessible.y + 1; y++) {
				if(state[accessible.z - 1][y][x] == 0 || Position.at(x, y, accessible.z - 1) == ignore)
					return false;
			}
		}
		return true;
	}

	public List<List<Position>> lines(Position pos) {
		List<List<Position>> lines = new LinkedList<List<Position>>();
		List<Position> line;
		
		line = new LinkedList<Position>();
		for (int x = 0; x < Model.LEVELS - pos.z; x++) {
			line.add(Position.at(x, pos.y, pos.z));
		}
		lines.add(line);
		
		line = new LinkedList<Position>();
		for (int y = 0; y < Model.LEVELS - pos.z; y++) {
			line.add(Position.at(pos.x, y, pos.z));
		}
		lines.add(line);
		
		if (onFirstDiagonal(pos)) {
			line = new LinkedList<Position>();
			for (int xy = 0; xy < Model.LEVELS - pos.z; xy++) {
				line.add(Position.at(xy, xy, pos.z));
			}
			lines.add(line);
		}

		if (onSecondDiagonal(pos)) {
			line = new LinkedList<Position>();
			for (int xy = 0; xy < Model.LEVELS - pos.z; xy++) {
				line.add(Position.at(xy, Model.LEVELS_1 - pos.z - xy, pos.z));
			}
			lines.add(line);
		}
		return lines;
	}
	
	public List<List<Position>> linesNoDiagonales(Position pos) {
		List<List<Position>> lines = new LinkedList<List<Position>>();
		List<Position> line;
		
		line = new LinkedList<Position>();
		for (int x = 0; x < Model.LEVELS - pos.z; x++) {
			line.add(Position.at(x, pos.y, pos.z));
		}
		lines.add(line);
		
		line = new LinkedList<Position>();
		for (int y = 0; y < Model.LEVELS - pos.z; y++) {
			line.add(Position.at(pos.x, y, pos.z));
		}
		lines.add(line);
		return lines;
	}
	
	private boolean onSecondDiagonal(Position pos) {
		return pos.x + pos.y == Model.LEVELS_1 - pos.z;
	}

	private boolean onFirstDiagonal(Position pos) {
		return pos.x == pos.y;
	}

	public List<List<Position>> fourSquare(Position pos) {
		List<List<Position>> fourSquare = new LinkedList<List<Position>>();
		List<Position> square;
		for (int x = pos.x - 1; x <= pos.x; x++) {
			for (int y = pos.y - 1; y <= pos.y; y++) {
				if(Position.isValid(x, y, pos.z)) {
					square = square(Position.at(x, y, pos.z));
					if (square != null)
						fourSquare.add(square);
				}
			}
		}
		return fourSquare;
	}

	public List<Position> square(Position position) {
		List<Position> square = new LinkedList<Position>();

		for (int x = position.x; x <= position.x + 1; x++) {
			for (int y = position.y; y <= position.y + 1; y++) {
				if(Position.isValid(x, y, position.z))
					square.add(Position.at(x, y, position.z));
				else 
					return null;
			}
		}
		return square;
	}

	public List<List<Position>> getDiagonales(int z) {
		List<List<Position>> diagonals = new LinkedList<List<Position>>();
		List<Position> line = new LinkedList<Position>();
		for (int xy = 0; xy < Model.LEVELS - z; xy++) {
			line.add(Position.at(xy, xy, z));
		}
		for (int xy = 0; xy < Model.LEVELS - z; xy++) {
			line.add(Position.at(xy, Model.LEVELS_1 - z - xy, z));
		}
		return diagonals;
	}
	
	public void switchPlayer() {
		currentPlayer = currentPlayer == 1 ? 2 : 1;
	}

	public List<Remove> copyList(List<Remove> removables) {
		List<Remove> copy = new LinkedList<Remove>();
		for (Remove remove : removables) {
			copy.add(remove);
		}
		return copy;
	}
	
	public boolean isConteined(List<Remove> ignore, int x, int y, int z) {
		for (Remove remove : ignore) {
			if(remove.position.x == x && remove.position.y == y && remove.position.z == z)
				return true;
		}
		return false;
	}
	
	public boolean hasAlreadyBeenRemoved(Remove r, int x, int y, int z) {
		return r.position.x == x && r.position.y == y && r.position.z == z;
	}

	public void printState() {
		for (int z = 0; z < Model.LEVELS; z++) {		// print state
		System.out.println();
		for (int x = 0; x < Model.LEVELS - z; x++) {
			for (int y = 0; y < Model.LEVELS - z; y++) {
				System.out.print(state[z][x][y]);
			}
			System.out.println();
		}
		
	}
	}
}
