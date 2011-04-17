package pylos.view;

import java.util.List;

import pylos.Config;
import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.appstate.ActionManager;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication {
	static final int MinFPSForLowGraphics = 30;

	Model model;
	public Controller controller;

	public BoardGraphics board;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;
	Lights lights;

	Node ballsOnBoard = new Node("Balls on Board");
	public Node positionBalls = new Node("Position Balls");
	public Node mountableBalls = new Node("Mountable Balls");

	private int frame = 0;

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

		rootNode.attachChild(mountableBalls);

		cameraTarget = new CameraTarget(this);
		rootNode.attachChild(cameraTarget.geometry);

		board = new BoardGraphics(this);
		rootNode.attachChild(board.getSpatial());

		lights = new Lights(rootNode);

		initFlyCam();

		initBalls();

		ActionManager pickBallManager = new ActionManager();
		stateManager.attach(pickBallManager);

		controller.updateView();
	}

	@Override
	public void simpleUpdate(float tpf) {
		++frame;
		if (!Config.LOW_GRAPHICS && (1 / tpf) < MinFPSForLowGraphics && timer.getTimeInSeconds() >= 3) {
			Config.LOW_GRAPHICS = true;
			lights.switchLightMode(Config.LOW_GRAPHICS);
			Pylos.logger.info("Switching to low graphics because fps was " + (1 / tpf) + " at frame " + frame);
		}
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

	public void updatePositionBalls() {
		updateNodeFromPositions(positionBalls, model.getPositionBalls());
	}

	public void updateMountableBalls() {
		mountableBalls.detachAllChildren();

		for (Ball ball : Model.currentPlayer.getMountableBalls()) {
			mountableBalls.attachChild(ball.graphics);
		}
	}

	public void updateNodeFromPositions(Node node, List<Position> positions) {
		node.detachAllChildren();
		for (Position position : positions) {
			PositionBallGraphics graphics = new PositionBallGraphics(position);
			board.place(graphics, position);
			node.attachChild(graphics);
		}
	}
}
