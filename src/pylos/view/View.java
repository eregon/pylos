package pylos.view;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Model;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication {
	BoardGraphics board;
	ChaseCamera chaseCam;
	CameraTarget cameraTarget;
	Node targets = new Node("Targets");
	InputListener listener;

	public View() {
		super();
		showSettings = false;
		settings = new AppSettings(true);
		settings.setResolution(800, 600);
		settings.setTitle("Pylos");
	}

	@Override
	public void simpleInitApp() {
		assetManager.registerLocator(Pylos.rootPath + "/assets", FileLocator.class);

		rootNode.attachChild(targets);

		cameraTarget = new CameraTarget(this);
		rootNode.attachChild(cameraTarget.geometry);

		board = new BoardGraphics(this);
		rootNode.attachChild(board.getSpatial());

		// You must add a light to make the model visible
		rootNode.addLight((new Sun()).light);

		initFlyCam();

		initBalls();

		initKeys();

		targets.attachChild(board.getSpatial());
	}

	private void initKeys() {
		inputManager.addMapping("PickBall", new MouseButtonTrigger(0)); // left-button click

		listener = new Listener(this);

		inputManager.addListener(listener, "PickBall");
	}

	public void initBalls() {
		for (Ball ball : Model.balls) {
			ball.graphics.create();
			rootNode.attachChild(ball.graphics.geometry);
			targets.attachChild(ball.graphics.geometry);
		}
		board.drawBalls();
		for (Ball ball : Model.balls) {
			ball.graphics.move();
		}
	}

	public void initFlyCam() {
		flyCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, cameraTarget.geometry, inputManager);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
	}

	public Geometry makeBoard() {
		Box board = new Box(new Vector3f(), 2, 0.1f, 2);
		Geometry geom = new Geometry("board", board);
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		geom.setMaterial(mat);
		return geom;
	}

	public void show() {
		start();
	}
}
