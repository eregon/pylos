package pylos.model;

import pylos.exception.PylosError;

public class Model {
	public final int levels;
	public final Ball[] balls;
	public final Player player1;
	public final Player player2;
	public final Player[] players;

	public Model() { this(4); }
	public Model(int levels) {
		int nb_balls = ballsForLevels(levels);
		if (nb_balls % 2 != 0) {
			throw new PylosError("There must be an even number of balls, please choose a correct number of levels (4, 6, ...)");
		}
		this.levels = levels;

		player1 = new Player();
		player2 = new Player();
		players = new Player[] { player1, player2 };

		balls = new Ball[nb_balls];
		for (int i = 0; i < balls.length/2; i++) {
			balls[i] = new Ball(player1, 5.5f, 0.8f, i-7);
		}
		for (int i = balls.length/2; i < balls.length; i++) {
			balls[i] = new Ball(player2, -5.5f, 0.8f, i-7-balls.length/2);
		}
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
