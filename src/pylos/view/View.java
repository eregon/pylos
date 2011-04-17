package pylos.view;

import java.util.List;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.appstate.ActionManager;
import pylos.view.appstate.LowGraphicsSwitcher;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.system.Timer;

public class View extends SimpleApplication {
	Model model;

	public BoardGraphics board;
	public Lights lights;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;

	public Node positionBalls = new Node("Position Balls");
	public Node mountableBalls = new Node("Mountable Balls");

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

		// AppState
		stateManager.attach(new ActionManager());
		stateManager.attach(new LowGraphicsSwitcher());

		Controller.updateView();
	}

	// simpleUpdate() is empty, everything is in AppState

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

	public Timer getTimer() {
		return timer;
	}
}
