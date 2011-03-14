package pylos.view;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.PositionBall;
import pylos.view.ball.HighlightBallGraphics;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication {
	static final int CheckTargetsEveryFrames = 30;

	public Model model;
	public Controller controller;

	BoardGraphics board;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;
	InputListener listener = new Listener();

	Node targets = new Node("Targets");

	Node ballsOnBoard = new Node("Balls on Board");
	Node positionBalls = new Node("Position Balls");
	Node visible = new Node("Visible");
	HighlightBallGraphics highlightBall = new HighlightBallGraphics();

	public View(Model model) {
		super();
		this.model = model;
		showSettings = false;
		settings = new AppSettings(true);
		settings.setResolution(800, 600);
		settings.setTitle("Pylos");
	}

	@Override
	public void simpleInitApp() {
		assetManager.registerLocator(Pylos.rootPath + "/assets", FileLocator.class);

		rootNode.attachChild(visible);

		cameraTarget = new CameraTarget(this);
		rootNode.attachChild(cameraTarget.geometry);

		board = new BoardGraphics(this);
		rootNode.attachChild(board.getSpatial());

		// You must add a light to make the model visible
		rootNode.addLight((new Sun()).light);

		initFlyCam();

		initBalls();

		initKeys();

		controller.initTurn();

		highlightBall.create(this);
	}

	@Override
	public void simpleUpdate(float tpf) {
		if ((int) (tpf) % CheckTargetsEveryFrames == 0) {
			Collisions collisions = new Collisions(this);
			if (collisions.any()) {
				PositionBall ball = collisions.getPositionBall();
				board.place(highlightBall, ball.x, ball.y, ball.level);
				visible.attachChild(highlightBall);
			} else {
				visible.detachChild(highlightBall);
			}
		}
	}

	private void initKeys() {
		inputManager.addMapping("PickBall", new MouseButtonTrigger(0)); // left-button click
		inputManager.addListener(listener, "PickBall");
	}

	public void initBalls() {
		for (Ball ball : Model.balls) {
			ball.graphics.create(this);
			rootNode.attachChild(ball.graphics);
		}
		board.drawBalls();
	}

	public void initFlyCam() {
		flyCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, cameraTarget.geometry, inputManager);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
	}

	public void show() {
		start();
	}

	public void placePositionBalls() {
		for (PositionBall ball : model.getPositionsToPlaceBallOnBoard()) {
			board.place(ball.graphics, ball.x, ball.y, ball.level);
			positionBalls.attachChild(ball.graphics);
		}

		targets.attachChild(positionBalls);
	}
}
