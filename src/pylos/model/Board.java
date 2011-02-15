package pylos.model;

import pylos.exception.PylosError;

public class Board {
	private int levels;

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
	 * for 4: 1 + 4 + 9 + 16
	 * = sum(1,n) n*n
	 * = 1/3x^3 + 1/2x^2 + 1/6x
	 * (by polynomial interpolation)
	 */
	private static int ballsForLevels(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += i*i;
		}
		return sum;
	}
}
