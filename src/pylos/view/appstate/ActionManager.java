package pylos.view.appstate;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.view.Collisions;
import pylos.view.View;
import pylos.view.ball.HighlightBallGraphics;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;

public class ActionManager extends AbstractAppState implements ActionListener {
	static final int MaxClickTime = 250; // ms
	static final String LeftClick = "LeftClick";
	static final String RightClick = "RightClick";

	View view;

	HighlightBallGraphics highlightBall = new HighlightBallGraphics();
	Node highlightBallNode = new Node("Highlight Ball");

	private long lastLeftClick, lastRightClick;

	Collisions collisions;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		view = Pylos.view;
		view.getRootNode().attachChild(highlightBallNode);

		initListener();
	}

	// Action Listener part: listen to clicks
	private void initListener() {
		InputManager input = view.getInputManager();
		input.addMapping(LeftClick, new MouseButtonTrigger(0)); // left-button click
		input.addMapping(RightClick, new MouseButtonTrigger(1)); // right-button click
		input.addListener(this, LeftClick, RightClick);
	}

	public boolean getCollisions(Node target) {
		collisions = new Collisions(view, target);
		return collisions.any();
	}

	public boolean getCollisions() {
		Node target;
		switch (Model.currentPlayer.action) {
		case PLACE:
			target = view.positionBalls;
			break;
		case RISE:
			target = view.positionsToRiseBall;
			break;
		case REMOVE:
			target = view.balls;
			break;
		default:
			return false;
		}
		return getCollisions(target);
	}

	@Override
	public void update(float tpf) {
		if (Model.currentPlayer.isPlacing() && getCollisions()) {
			highlightBall.setMaterial(Model.currentPlayer.graphics.ballMaterial);
			view.board.place(highlightBall, collisions.getPosition());
			highlightBallNode.attachChild(highlightBall);
		} else {
			highlightBallNode.detachChild(highlightBall);
		}
	}

	public void onAction(String action, boolean pressed, float tpf) {
		long time = System.currentTimeMillis();
		if (action == LeftClick) {
			if (pressed) {
				lastLeftClick = time;
			} else if (time - lastLeftClick < MaxClickTime) {
				if (getCollisions()) {
					if (Model.currentPlayer.isPlacing()) {
						Controller.placePlayerBall(collisions.getPosition());
					} else if (Model.currentPlayer.isRemoving()) {
						Ball ball = Model.board.ballAt(collisions.getPosition());
						if (ball.isRemovableByCurrentPlayer())
							Controller.removePlayerBall(ball);
					}
				}
			}
		} else if (action == RightClick) {
			if (pressed) {
				lastRightClick = time;
			} else if (time - lastRightClick < MaxClickTime && Model.currentPlayer.canRise()) {
				if (getCollisions(view.balls)) {
					Ball ball = Model.board.ballAt(collisions.getPosition());
					if (ball.isMountableByCurrentPlayer())
						Controller.risePlayerBall(ball);
				}
			}
		}
	}
}
