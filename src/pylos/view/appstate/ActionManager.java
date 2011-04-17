package pylos.view.appstate;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.Collisions;
import pylos.view.View;
import pylos.view.ball.HighlightBallGraphics;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;

public class ActionManager extends AbstractAppState implements ActionListener {
	static final int MaxRightClickTime = 250; // ms
	static final String PickBall = "PickBall";
	static final String RiseBall = "RiseBall";

	Model model;
	Controller controller;
	View view;

	HighlightBallGraphics highlightBall = new HighlightBallGraphics();
	Node highlightBallNode = new Node("Highlight Ball");

	private long lastRightClick;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		model = Pylos.model;
		controller = Pylos.controller;
		view = Pylos.view;
		view.getRootNode().attachChild(highlightBallNode);

		initListener();
	}

	@Override
	public void update(float tpf) {
		Collisions collisions = new Collisions(view, view.positionBalls);
		if (collisions.any()) {
			Position position = collisions.getPosition();
			highlightBall.setMaterial(Model.currentPlayer.graphics.ballMaterial);
			view.board.place(highlightBall, position);
			highlightBallNode.attachChild(highlightBall);
		} else {
			highlightBallNode.detachChild(highlightBall);
		}
	}

	// Action Listener part: listen to clicks
	private void initListener() {
		view.getInputManager().addMapping(PickBall, new MouseButtonTrigger(0)); // left-button click
		view.getInputManager().addMapping(RiseBall, new MouseButtonTrigger(1)); // right-button click
		view.getInputManager().addListener(this, PickBall, RiseBall);
	}

	public void onAction(String action, boolean pressed, float tpf) {
		if (action == PickBall) {
			if (!pressed && !model.isWinner()) {
				Collisions collisions = new Collisions(view, view.positionBalls);
				if (collisions.any())
					controller.placePlayerBall(collisions.getPosition());
			}
		} else if (action == RiseBall) {
			long time = System.currentTimeMillis();
			if (pressed) {
				lastRightClick = time;
			} else {
				if (time - lastRightClick < MaxRightClickTime) {
					view.updateMountableBalls();
					Collisions collisions = new Collisions(view, view.mountableBalls);
					if (collisions.any())
						controller.risePlayerBall(Model.ballAt(collisions.getPosition()));
				}
			}
		}
	}
}
