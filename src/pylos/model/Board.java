package pylos.model;

import pylos.exception.PylosError;

public class Board {
	private final int levels;

	public Board() { this(4); }
	public Board(int levels) {
		if (ballsForLevels(levels) % 2 != 0) {
			throw new PylosError("There must be an even number of balls, please choose a correct number of levels (4, 6, ...)");
		}
		this.levels = levels;
	}

	/** number of balls for a number of levels.
	 * @param n number of levels
	 *
	 * for n = 4: 1 + 4 + 9 + 16
	 * = sum(1,n) n*n
	 * = 1/3n^3 + 1/2n^2 + 1/6n
	 * (by polynomial interpolation)
	 */
	private static int ballsForLevels(int n) {
		return (2*n*n*n + 3*n*n + n)/6;
	}
}
