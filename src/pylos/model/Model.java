package pylos.model;

import java.util.LinkedList;
import java.util.List;

import pylos.exception.PylosError;

public class Model {
	public final int levels;
	public final int nbBalls;
	public final List<Ball> balls = new LinkedList<Ball>();
	public final Player player1;
	public final Player player2;
	public final Player[] players;

	public Model() { this(4); }
	public Model(int levels) {
		nbBalls = ballsForLevels(levels);
		if (nbBalls % 2 != 0) {
			throw new PylosError("There must be an even number of balls, please choose a correct number of levels (4, 6, ...)");
		}
		this.levels = levels;

		player1 = new Player(this);
		player2 = new Player(this);
		players = new Player[] { player1, player2 };
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
