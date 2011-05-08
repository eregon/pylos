package pylos.ai;

import java.util.LinkedList;
import java.util.List;

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
	
	public boolean isRemovableByCurrentPlayer(int axeX, int axeY, int axeZ) {
		return isRemovable(axeX, axeY, axeZ) && state[axeZ][axeY][axeX] == currentPlayer;
	}
	
	public boolean isRemovable(int axeX, int axeY, int axeZ) {
		for(int x = valid(axeX, axeZ)[0]; x <= valid(axeX, axeZ)[1]; x ++) {
			for (int y = valid(axeY, axeZ)[0]; y <= valid(axeY, axeZ)[1]; y++) {
				if(state[axeZ + 1][y][x] != 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * to avoid out of bound exception
	 */
	public int[] valid(int x, int z) {
		int[] tab = new int[2];
		tab[0] = x - 1 < 0 ? x : x - 1;
		tab[1] = x < Model.LEVELS - (z + 1) ? x : x - 1; 
		return tab;
	}

	public boolean isMountable(int x, int y, int z) {
		return isRemovable(x, y, z) && !addPositionToMount(x, y, z).isEmpty();
	}

	/**
	 * on sait que la boule est montable, on veut toutes les positions 
	 * ou elle peut aller (faire attention aux removables)
	 */
	public List<Move> addPositionToMount(int x, int y, int z) {
		List<Move> list = new LinkedList<Move>();
		for (int iz = z + 1; iz < Model.LEVELS; iz++) {
			for (int ix = 0; ix < Model.LEVELS - iz; ix++) {
				for (int iy = 0; iy < Model.LEVELS - iz; iy++) {
					if(accessibleIgnoring(ix, iy, iz, x, y, z)) {
						list.add(new Move(x, y, z, ix, iy, iz));
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
			for (int iy = y; iy < y + 1; iy++) {
				if(state[z - 1][iy][ix] == 0)
					return false;
			}
		}
		return true;
	}
	
	public boolean accessibleIgnoring(int x, int y, int z, int x2, int y2, int z2) {
		if(state[x][y][z] != 0)
			return x == x2 && y == y2 && z == z2;
		if(z == 0)
			return true;
		for (int ix = x; ix <= x + 1; ix++) {
			for (int iy = y; iy <= y + 1; iy++) {
				boolean equals = x == x2 && y == y2 && z == z2;
				if(state[x][y][z] == 0 || equals)
					return false;
			}
		}
		return true;
	}

	public List<List<int[]>> lines(int[] pos) {
		List<List<int[]>> lines = new LinkedList<List<int[]>>();
		List<int[]> line;
		
		line = new LinkedList<int[]>();
		for (int x = 0; x < Model.LEVELS - pos[2]; x++) {
			int [] p = {x, pos[1], pos[2]};
			line.add(p);
		}
		lines.add(line);
		
		line = new LinkedList<int[]>();
		for (int y = 0; y < Model.LEVELS - pos[2]; y++) {
			int[] p = {pos[0], y, pos[2]};
			line.add(p);
		}
		lines.add(line);
		
		if (onFirstDiagonal(pos)) {
			line = new LinkedList<int[]>();
			for (int xy = 0; xy < Model.LEVELS - pos[2]; xy++) {
				int[] p = {xy, xy, pos[2]};
				line.add(p);
			}
			lines.add(line);
		}

		if (onSecondDiagonal(pos)) {
			line = new LinkedList<int[]>();
			for (int xy = 0; xy < Model.LEVELS - pos[2]; xy++) {
				int[] p = {xy, Model.LEVELS_1 - pos[2] - xy, pos[2]};
				line.add(p);
			}
			lines.add(line);
		}
		return lines;
	}
	
	public List<List<int[]>> linesNoDiagonales(int[] pos) {
		List<List<int[]>> lines = new LinkedList<List<int[]>>();
		List<int[]> line;
		
		line = new LinkedList<int[]>();
		for (int x = 0; x < Model.LEVELS - pos[2]; x++) {
			int [] p = {x, pos[1], pos[2]};
			line.add(p);
		}
		lines.add(line);
		
		line = new LinkedList<int[]>();
		for (int y = 0; y < Model.LEVELS - pos[2]; y++) {
			int[] p = {pos[0], y, pos[2]};
			line.add(p);
		}
		lines.add(line);
		return lines;
	}
	
	private boolean onSecondDiagonal(int[] pos) {
		return pos[0] + pos [1] == Model.LEVELS_1 - pos[2];
	}

	private boolean onFirstDiagonal(int[] pos) {
		return pos[0] == pos[2];
	}

	public List<List<int[]>> fourSquare(int[] pos) {
		List<List<int[]>> fourSquare = new LinkedList<List<int[]>>();
		List<int[]> square;
		for (int x = valid(pos[0], pos[2])[0]; x <= pos[0]; x++) {
			for (int y = valid(pos[1], pos[2])[1]; y <= pos[1]; y++) {
				square = square(x, y, pos[2]);
				if (square != null)
					fourSquare.add(square);
			}
		}
		return fourSquare;
	}

	public List<int[]> square(int x, int y, int z) {
		List<int[]> square = new LinkedList<int[]>();

		for (int ix = x; ix <= valid(x, z)[1] + 1; ix++) {
			for (int iy = y; iy <= valid(y, z)[1] + 1; iy++) {
				int[] pos = {ix, iy, z};
				square.add(pos);
			}
		}
			if(square.size() != 4)
				return null;
		return square;
	}

	public List<List<int[]>> getDiagonales(int z) {
		List<List<int[]>> diagonals = new LinkedList<List<int[]>>();
		List<int[]> line = new LinkedList<int[]>();
		for (int xy = 0; xy < Model.LEVELS - z; xy++) {
			int[] p = {xy, xy, z};
			line.add(p);
		}
		for (int xy = 0; xy < Model.LEVELS - z; xy++) {
			int[] p = {xy, Model.LEVELS_1 - z, z};
			line.add(p);
		}
		return diagonals;
	}
}
