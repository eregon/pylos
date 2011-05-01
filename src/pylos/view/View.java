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
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.system.Timer;

import de.lessvoid.nifty.Nifty;

public class View extends SimpleApplication {
	public BoardGraphics board;
	public Lights lights;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;

	public Node balls = new Node("Balls");
	public Node positionBalls = new Node("Position Balls");
	public Node positionsToRiseBall = new Node("Positions to rise Ball");

	MainScreenController screenController;

	public View() {
		super();
		showSettings = false;
		settings = new AppSettings(true);
		settings.setResolution(800, 600);
		settings.setTitle("Pylos");
	}

	@Override
	public void simpleInitApp() {
		assetManager.registerLocator(Pylos.assetsPath, FileLocator.class);

		rootNode.attachChild(balls);

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

		startNifty();

		setStatus("Welcome to Pylos !");

		Controller.updateView();
	}

	// simpleUpdate() is empty, everything is in AppState

	public void initBalls() {
		for (Ball ball : Model.board.balls) {
			ball.graphics.create(this);
			balls.attachChild(ball.graphics);
		}
		board.drawBalls();
	}

	public void initFlyCam() {
		flyCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, cameraTarget.geometry, inputManager);
		chaseCam.setInvertVerticalAxis(true);
	}

	private void startNifty() {
		guiNode.detachAllChildren();
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		Nifty nifty = niftyDisplay.getNifty();
		try {
			nifty.fromXml(Pylos.assetsPath + "/Interface/MainScreen.xml", "main_screen");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		guiViewPort.addProcessor(niftyDisplay);
	}

	public void show() {
		start();
	}

	public void updatePositionBalls() {
		updateNodeFromPositions(positionBalls, Model.getPositionBalls());
	}

	public void updatePositionsToRise(Ball ball) {
		updateNodeFromPositions(positionsToRiseBall, Model.getPositionsToRise(ball));
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

	public void registerScreenController(MainScreenController screenController) {
		this.screenController = screenController;
	}

	public void setStatus(String status) {
		screenController.setStatus(status);
	}
}
