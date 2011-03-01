package pylos.controller;

import com.jme3.scene.Geometry;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.view.View;

public class Controller {
	protected Model model;
	protected View  view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void updateBallsPosition() {
		for (int i = 0; i < model.nbBalls/2; i++) {
			model.player1.balls[i].graphics.setPosition(5.5f, 0.75f, i-model.nbBalls/4);
		}
		for (int i = 0; i < model.nbBalls/2; i++) {
			model.player2.balls[i].graphics.setPosition(-5.5f, 0.75f, i-model.nbBalls/4);
		}
	}

}
