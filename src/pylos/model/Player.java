package pylos.model;


public class Player {

	public Ball[] balls = new Ball[Model.nbBalls/2];

	public Player() {
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(this);
		}
	}
}
