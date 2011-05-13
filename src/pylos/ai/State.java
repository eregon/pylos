package pylos.ai;

import java.util.LinkedList;
import java.util.List;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class State {
	public final int minVal = -150, maxVal = 150;

	public byte[][][] state = new byte[Model.LEVELS][][];
	public int[] ballOnSide = new int[2];
	public byte currentPlayer;
	public byte opponnent;

	public State() {
		currentPlayer = Model.currentPlayer.toByte();
		opponnent = Model.currentPlayer.other().toByte();
		ballOnSide[currentPlayer - 1] = 15;
		ballOnSide[opponnent - 1] = 15;
		Ball ball;
		for (int z = 0; z < Model.LEVELS; z++) {
			state[z] = new byte[Model.LEVELS - z][Model.LEVELS - z];
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					ball = Model.board.ballAt(Position.at(x, y, z));
					if (ball != null) {
						ballOnSide[ball.owner.toByte() - 1]--;
					}
					state[z][y][x] = ball == null ? 0 : ball.owner.toByte();
				}
			}
		}

	}

	public State(State s) {
		clone(s);
	}

	public void clone(State s) {
		for (int z = 0; z < Model.LEVELS; z++) {
			state[z] = new byte[Model.LEVELS - z][Model.LEVELS - z];
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					state[z][y][x] = s.state[z][y][x];
				}
			}
		}
		ballOnSide[0] = s.ballOnSide[0];
		ballOnSide[1] = s.ballOnSide[1];
		currentPlayer = s.currentPlayer;
		opponnent = (byte) (currentPlayer == 1 ? 2 : 1);
	}

	public int evaluate() {
		return ballOnSide[0] * 10 - ballOnSide[1] * 10;
	}

	public boolean accessible(Position p) {
		if (state[p.z][p.y][p.x] != 0)
			return false;
		if (p.z == 0)
			return true;
		for (int ix = p.x; ix <= p.x + 1; ix++) {
			for (int iy = p.y; iy <= p.y + 1; iy++) {
				if (state[p.z - 1][iy][ix] == 0)
					return false;
			}
		}
		return true;
	}

	public void printState() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		String s = new String();
		for (int z = 0; z < Model.LEVELS; z++) { // print state
			s += "\n";
			for (int x = 0; x < Model.LEVELS - z; x++) {
				for (int y = 0; y < Model.LEVELS - z; y++) {
					s += state[z][x][y];
				}
				s += "\n";
			}
		}
		return s;
	}

	public boolean createsLineOrSquare(Position p) {
		return anyLines(p) || anySquares(p);
	}

	private boolean anyLines(Position position) {
		if (position.z >= 2)
			return false;
		for (List<Position> line : position.lines()) {
			boolean validLine = true;
			for (Position p : line) {
				int ball = state[p.z][p.y][p.x];
				if (p != position && ball == 0 || ball != currentPlayer && ball != 0) {
					validLine = false;
					break;
				}
			}
			if (validLine)
				return true;
		}
		return false;
	}

	private boolean anySquares(Position position) {
		if (position.z >= 2)
			return false;
		for (List<Position> square : position.fourSquare()) {
			boolean validSquare = true;
			for (Position p : square) {
				int ball = state[p.z][p.y][p.z];
				if (position != p && ball == 0 || ball != currentPlayer && ball != 0) {
					validSquare = false;
					break;
				}
			}
			if (validSquare)
				return true;
		}
		return false;
	}

	public boolean isAlreadyRemoved(Position check, Position removed) {
		return check == removed;
	}

	public boolean isRemovableByCurrentPlayer(Position position) {
		return isRemovable(position) && state[position.z][position.y][position.x] == currentPlayer;
	}

	public boolean isRemovable(Position position) {
		if (state[position.z][position.y][position.x] == 0)
			return false;
		for (int x = position.x - 1; x <= position.x; x++) {
			for (int y = position.y - 1; y <= position.y; y++) {
				if (Position.isValid(x, y, position.z + 1) && state[position.z + 1][y][x] != 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * to avoid out of bound exception
	 */

	public boolean isMountableByCurrentPlayer(Position position) {
		return isRemovableByCurrentPlayer(position) && !addPositionToMount(position).isEmpty();
	}

	/**
	 * on sait que la boule est montable, on veut toutes les positions
	 * ou elle peut aller (faire attention aux removables)
	 */
	public List<Position> addPositionToMount(Position position) {
		List<Position> list = new LinkedList<Position>();
		for (int z = position.z + 1; z < Model.LEVELS; z++) {
			for (int x = 0; x < Model.LEVELS - z; x++) {
				for (int y = 0; y < Model.LEVELS - z; y++) {
					if (accessibleIgnoring(Position.at(x, y, z), position)) {
						list.add(Position.at(x, y, z));
					}
				}

			}

		}
		return list;
	}

	public boolean accessibleIgnoring(Position accessible, Position ignore) {
		if (state[accessible.z][accessible.y][accessible.x] != 0)
			return accessible == ignore;
		if (accessible.z == 0)
			return true;
		for (int x = accessible.x; x <= accessible.x + 1; x++) {
			for (int y = accessible.y; y <= accessible.y + 1; y++) {
				if (state[accessible.z - 1][y][x] == 0 || Position.at(x, y, accessible.z - 1) == ignore)
					return false;
			}
		}
		return true;
	}
}
