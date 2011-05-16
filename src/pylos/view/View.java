package pylos.view;

import java.util.List;

import pylos.Config;
import pylos.Pylos;
import pylos.controller.Controller;
import pylos.controller.screen.MainScreenController;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.appstate.ActionManager;
import pylos.view.appstate.FPSDisplayer;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.system.Timer;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import de.lessvoid.nifty.Nifty;

public class View extends SimpleApplication implements ActionListener {
	static final String Quit = "Quit";

	public BoardGraphics board;
	public Lights lights;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;

	public Node balls = new Node("Balls");
	public Node positionBalls = new Node("Position Balls");
	public Node positionsToMountBall = new Node("Positions to mount Ball");

	Nifty nifty;
	public MainScreenController screenController;

	public View() {
		super();
		showSettings = false;
		settings = new AppSettings(true);
		settings.setResolution(Config.RESOLUTION[0], Config.RESOLUTION[1]);
		settings.setTitle("Pylos");
	}

	@Override
	public void simpleInitApp() {
		assetManager.registerLocator(Pylos.assetsPath, FileLocator.class);

		initKeys();
		initCamera();
		startNifty();
	}

	// simpleUpdate() is empty, everything is in AppState

	void initKeys() {
		inputManager.clearMappings();
		inputManager.addMapping(Quit, new KeyTrigger(KeyInput.KEY_Q), new KeyTrigger(KeyInput.KEY_ESCAPE));
		inputManager.addListener(this, Quit);
	}

	void initCamera() {
		flyCam.setEnabled(false);

		cameraTarget = new CameraTarget(this);
		rootNode.attachChild(cameraTarget.geometry);

		chaseCam = new ChaseCamera(cam, cameraTarget.geometry, inputManager);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setDefaultHorizontalRotation(-FastMath.PI / 6);
		chaseCam.setDefaultVerticalRotation(FastMath.PI / 4);
		chaseCam.setMaxDistance(500);
	}

	void startNifty() {
		guiNode.detachAllChildren();
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();
		try {
			nifty.fromXml(Pylos.assetsPath + "/Interface/NiftyUI.xml", "start_screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
		guiViewPort.addProcessor(niftyDisplay);
	}

	void initBalls() {
		for (Ball ball : Model.board.balls) {
			ball.graphics.create(this);
			balls.attachChild(ball.graphics);
		}
		board.drawBalls();
	}

	public void initGame() {
		nifty.gotoScreen("main_screen");

		rootNode.attachChild(balls);

		board = new BoardGraphics(this);
		rootNode.attachChild(board.getSpatial());

		lights = new Lights(rootNode);

		initBalls();

		// AppState
		stateManager.attach(new ActionManager());
		stateManager.attach(new FPSDisplayer());

		setStatus("Welcome to Pylos !");

		Controller.initTurn();
	}

	// simpleUpdate() is empty, everything is in AppState

	public void show() {
		start();
	}

	public void updatePositionBalls() {
		updateNodeFromPositions(positionBalls, Model.getPositionBalls());
	}

	public void updatePositionsToMount(Ball ball) {
		updateNodeFromPositions(positionsToMountBall, Model.getPositionsToMount(ball));
	}

	public void updateNodeFromPositions(Node node, List<Position> positions) {
		node.detachAllChildren();
		for (Position position : positions) {
			PositionBallGraphics graphics = new PositionBallGraphics(position);
			board.place(graphics, position);
			node.attachChild(graphics);
		}
		rootNode.attachChild(SkyFactory.createSky(assetManager, assetManager.loadTexture("Models/Board/Texture/sky/Stellar_Layout_west.png"), 
				assetManager.loadTexture("Models/Board/Texture/sky/Stellar_Layout_east.png"), 
				assetManager.loadTexture("Models/Board/Texture/sky/Stellar_Layout_north.png"), 
				assetManager.loadTexture("Models/Board/Texture/sky/Stellar_Layout_south.png"),
				assetManager.loadTexture("Models/Board/Texture/sky/Stellar_Layout_up.png"), 
				assetManager.loadTexture("Models/Board/Texture/sky/Stellar_Layout_down.png"), new Vector3f(1, 1, 1)));
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

	public void onAction(String action, boolean isPressed, float tpf) {
		if (action == Quit)
			Pylos.stop();
	}
}
