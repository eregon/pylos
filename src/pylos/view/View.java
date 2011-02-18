package pylos.view;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication {
	public View() {
		super();
		showSettings = false;
		settings = new AppSettings(true);
		settings.setResolution(800, 600);
		settings.setTitle("Pylos");
	}

	@Override
	public void simpleInitApp() {
		Geometry board = makeBoard();
		rootNode.attachChild(board);

		flyCam.setEnabled(false);
		ChaseCamera chaseCam = new ChaseCamera(cam, board, inputManager);
		chaseCam.setInvertVerticalAxis(true);

		/*
		 * DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0, -1, 0));
		sun.setColor(ColorRGBA.White);
		rootNode.addLight(sun);
		*/
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
