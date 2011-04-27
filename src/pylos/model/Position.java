package pylos.model;

/**
 * Represent an (immutable) position on the Board.
 * x and y are horizontal coordinates, z is the level in the pyramid.
 */
public class Position {
	public static final Position[][][] positions = new Position[Model.LEVELS][][];
	public static final Position[] all = new Position[Model.BALLS];
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
		int all_index = 0;
		Position pos;
		for (int level = 0; level < Model.LEVELS; level++) {
			positions[level] = new Position[Model.LEVELS - level][Model.LEVELS - level];
			for (int y = 0; y < Model.LEVELS - level; y++) {
				for (int x = 0; x < Model.LEVELS - level; x++) {
					pos = new Position(x, y, level);
					positions[level][y][x] = pos;
					all[all_index++] = pos;
				}
			}
		}
		top = Position.at(0, 0, Model.LEVELS - 1);
	}

	public static Position at(int x, int y, int z) {
		return positions[z][y][x];
	}

	public static boolean isValid(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < Model.LEVELS - z && y < Model.LEVELS - z && z < Model.LEVELS;
	}

	// No equals(), Positions are unique and can be compared by identity
}
