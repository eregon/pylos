package pylos.model;

import java.util.LinkedList;

public class Player {
	public static final int nbBalls = Model.nbBalls / 2;
	public final Ball[] balls = new Ball[nbBalls];
	public final int side;

	public Player(int side) {
		this.side = side;
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(this);
		}
	}

	public LinkedList<Ball> ballsInSide() {
		LinkedList<Ball> list = new LinkedList<Ball>();
		for (Ball ball : balls) {
			if (!ball.onBoard) {
				list.add(ball);
			}
		}
		return list;
	}
}
