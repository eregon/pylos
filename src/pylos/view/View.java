package pylos.view;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.ball.HighlightBallGraphics;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication implements ActionListener {
	static final int CheckTargetsEveryFrames = 30;

	public Model model;
	public Controller controller;

	public BoardGraphics board;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;

	Node targets;

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
		new Lights(this);

		initFlyCam();

		initBalls();

		initKeys();

		controller.updateView();
	}

	@Override
	public void simpleUpdate(float tpf) {
		if (!model.isWinner() && (int) (tpf) % CheckTargetsEveryFrames == 0) {
			Collisions collisions = new Collisions(this);
			if (collisions.any()) {
				Position position = collisions.getPosition();
				highlightBall.setMaterial(Model.currentPlayer.graphics.ballMaterial);
				board.place(highlightBall, position);
				visible.attachChild(highlightBall);
			} else {
				visible.detachChild(highlightBall);
			}
		}
	}

	private void initKeys() {
		inputManager.addMapping("PickBall", new MouseButtonTrigger(0)); // left-button click
		inputManager.addListener(this, "PickBall");
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
		positionBalls.detachAllChildren();

		for (Position position : model.getPositionsToPlaceBallOnBoard()) {
			PositionBallGraphics graphics = new PositionBallGraphics(position);
			board.place(graphics, position);
			positionBalls.attachChild(graphics);
		}

		targets = positionBalls;
	}

	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("PickBall") && !isPressed && !model.isWinner()) {
			Collisions collisions = new Collisions(Pylos.view);
			if (collisions.any()) {
				controller.placePlayerBall(collisions.getPosition());
			}
		}
	}
}
