package pylos.ai;

import static pylos.model.Model.LEVELS;

import java.util.LinkedList;
import java.util.List;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class State {
	static final int HAND_POINT = 140;
	static final int RAWS_POINT = 100;
	static final int REMOVABLE_POINT = 20;
	public static final byte ai = 2, human = 1; // ai = max, human = min

	public byte[][][] state = new byte[LEVELS][][];
	public int[] ballOnSide = new int[3];
	public byte currentPlayer;
	byte opponent;

	public State() {
		currentPlayer = Model.currentPlayer.toByte();
		opponent = (byte) (3 - currentPlayer);
		ballOnSide[human] = 15;
		ballOnSide[ai] = 15;
		Ball ball;
		for (int z = 0; z < LEVELS; z++) {
			state[z] = new byte[LEVELS - z][LEVELS - z];
			for (int y = 0; y < LEVELS - z; y++) {
				for (int x = 0; x < LEVELS - z; x++) {
					ball = Model.board.ballAt(Position.at(x, y, z));
					if (ball != null) {
						ballOnSide[ball.owner.toByte()]--;
					}
					state[z][y][x] = ball == null ? 0 : ball.owner.toByte();
				}
			}
		}

	}

	public State(State s) {
		for (int z = 0; z < LEVELS; z++) {
			state[z] = new byte[LEVELS - z][];
			for (int y = 0; y < LEVELS - z; y++)
				state[z][y] = s.state[z][y].clone();
		}
		ballOnSide = s.ballOnSide.clone();
		currentPlayer = s.currentPlayer;
		opponent = s.opponent;
	}

	public int evaluate() {
		int score = ballOnSide[ai] * HAND_POINT - ballOnSide[human] * HAND_POINT;
		// score += Math.random() * 9;
		for (int z = 0; z < LEVELS; z++) {
			for (int y = 0; y < LEVELS - z; y++) {
				for (int x = 0; x < LEVELS - z; x++) {
					byte ball = state[z][y][x];
					if (ball == 0) {
						if (anyLines(Position.at(x, y, z), ai) || anySquares(Position.at(x, y, z), ai)) {
							score += RAWS_POINT;
						}
						if (anyLines(Position.at(x, y, z), human) || anySquares(Position.at(x, y, z), human)) {
							score -= RAWS_POINT;
						}
					} else {
						if (isRemovable(Position.at(x, y, z))) {
							if (ball == ai)
								score += REMOVABLE_POINT;
							else
								score -= REMOVABLE_POINT;
						}
					}
				}
			}
		}
		if (state[3][0][0] != 0)
			return state[3][0][0] == ai ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		return score;
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
		Pylos.AIlogger.info(toString());
	}

	public void printBallOnSide() {
		Pylos.AIlogger.info("ballOnSide[human] = " + ballOnSide[human] + "ballOnSide[ai] = " + ballOnSide[ai]);
	}

	@Override
	public String toString() {
		String s = new String();
		for (int z = 0; z < LEVELS; z++) { // print state
			s += "\n";
			for (int x = 0; x < LEVELS - z; x++) {
				for (int y = 0; y < LEVELS - z; y++) {
					s += state[z][x][y];
				}
				s += "\n";
			}
		}
		return s;
	}

	public boolean createsLineOrSquare(Position p) {
		return anyLines(p, currentPlayer) || anySquares(p, currentPlayer);
	}

	private boolean anyLines(Position position, byte currentPlayer) {
		if (position.z >= 2)
			return false;
		for (List<Position> line : Position.lines.get(position)) {
			boolean validLine = true;
			for (Position p : line) {
				byte ball = state[p.z][p.y][p.x];
				if ((ball == 0 && p != position) || (ball != 0 && ball != currentPlayer)) {
					validLine = false;
					break;
				}
			}
			if (validLine) {
				return true;
			}
		}
		return false;
	}

	public boolean isAlreadyRemoved(Position check, Position removed) {
		return check == removed;
	}

	public boolean anySquares(Position position, byte currentPlayer) {
		if (position.z >= 2)
			return false;
		for (List<Position> square : Position.fourSquare.get(position)) {
			boolean validSquare = true;
			for (Position p : square) {
				byte ball = state[p.z][p.y][p.x];
				if ((ball == 0 && p != position) || (ball != 0 && ball != currentPlayer)) {
					validSquare = false;
					break;
				}
			}
			if (validSquare) {
				return true;
			}
		}
		return false;
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
		return !addPositionToMount(position).isEmpty() && isRemovableByCurrentPlayer(position);
	}

	/**
	 * on sait que la boule est montable, on veut toutes les positions
	 * ou elle peut aller (faire attention aux removables)
	 */
	public List<Position> addPositionToMount(Position position) {
		List<Position> list = new LinkedList<Position>();
		for (int z = position.z + 1; z < LEVELS; z++) {
			for (int x = 0; x < LEVELS - z; x++) {
				for (int y = 0; y < LEVELS - z; y++) {
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

	public void swichPlayers() {
		currentPlayer = opponent;
		opponent = (byte) (3 - currentPlayer);
	}

	public boolean endGame() {
		return ballOnSide[currentPlayer] == 0 || state[3][0][0] != 0;
	}
}
