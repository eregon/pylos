package pylos.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
	public static final int levels = 4;
	public static final int nbBalls = 30;
	public static final List<Ball> balls = new LinkedList<Ball>();
	public static final Player player1 = new Player(+1);
	public static final Player player2 = new Player(-1);
	public static final Player[] players =  { player1, player2 };
}
