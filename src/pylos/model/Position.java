package pylos.model;

import java.util.List;

/**
 * Represent an (immutable) position on the Board.
 * x and y are horizontal coordinates, z is the level in the pyramid.
 */
public class Position {
	public static final Position[][][] positions = new Position[Model.LEVELS][][];
	public static Position top;
	public final int x, y, z;

	private Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public static void createPositions() {
		for (int level = 0; level < Model.LEVELS; level++) {
			positions[level] = new Position[Model.LEVELS - level][Model.LEVELS - level];
			for (int y = 0; y < Model.LEVELS - level; y++) {
				for (int x = 0; x < Model.LEVELS - level; x++) {
					positions[level][y][x] = new Position(x, y, level);
				}
			}
		}
		top = Position.at(0, 0, Model.LEVELS - 1);
	}

	public static Position at(int x, int y, int z) {
		return positions[z][y][x];
	}

	public boolean isContained(List<Position> pos) {
		for (Position position : pos) {
			if(this.x == position.x && this.y == position.y && this.z == position.z)
				return true;
		}
		return false;
	}

	public boolean equals(Position position) {
		if(this.x == position.x && this.y == position.y && this.z == position.z)
			return true;
		return false;
	}
}
