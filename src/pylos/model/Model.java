package pylos.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
	public final int levels = 4;
	public final int nbBalls = 30;
	public final List<Ball> balls = new LinkedList<Ball>();
	public final Player player1;
	public final Player player2;
	public final Player[] players;

	public Model() {
		player1 = new Player(this);
		player2 = new Player(this);
		players = new Player[] { player1, player2 };
	}
}
