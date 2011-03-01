package pylos.view;

import pylos.Pylos;
import pylos.model.Ball;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication {
	BoardGraphics board;
	CameraTarget cameraTarget;

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

		cameraTarget = new CameraTarget(this);
		rootNode.attachChild(cameraTarget.geometry);

//		Geometry b = makeBoard();
//		rootNode.attachChild(b);

		// Load model

//		Material mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
//		mat.setColor("Color", ColorRGBA.Gray);
//		board.setMaterial(mat);
		board = new BoardGraphics(this);
		rootNode.attachChild(board.getSpatial());

		// You must add a light to make the model visible
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection((new Vector3f(-0.1f, -0.7f, -1.0f)).normalize());
		rootNode.addLight(sun);

		flyCam.setEnabled(false);
		ChaseCamera chaseCam = new ChaseCamera(cam, cameraTarget.geometry, inputManager);
		chaseCam.setInvertVerticalAxis(true);

		//Pylos.board.balls
		for (Ball ball : Pylos.model.balls) {
			if (ball.onBoard) {
				//TODO
			} else {
				Geometry geom = ball.graphics.draw();
				geom.move(ball.x, ball.y, ball.z);
				//geom.move(Math.random() < 0.5 ? 5.5f : -5.5f, 0.8f, 0);
				rootNode.attachChild(geom);
			}
		}
	}

	public Geometry	makeBoard() {
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
