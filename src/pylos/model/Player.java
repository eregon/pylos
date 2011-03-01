package pylos.model;


public class Player {

	public Ball[] balls;

	public Player(Model model) {
		balls = new Ball[model.nbBalls/2];
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball(this, model);
		}
	}
}
