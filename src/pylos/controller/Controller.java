package pylos.controller;

import pylos.model.Model;
import pylos.view.BallGraphics;
import pylos.view.View;

public class Controller {
	protected Model model;
	protected View  view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void updateBallsPosition() {
		for (int i = 0; i < Model.nbBalls/2; i++) {
			Model.player1.balls[i].graphics.setPosition(5.5f, 0.75f, (i-Model.nbBalls/4)*BallGraphics.DIAMETER);
		}
		for (int i = 0; i < Model.nbBalls/2; i++) {
			Model.player2.balls[i].graphics.setPosition(-5.5f, 0.75f, (i-Model.nbBalls/4)*BallGraphics.DIAMETER);
		}
	}

}
