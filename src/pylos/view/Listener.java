package pylos.view;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class Listener implements ActionListener {
	Camera cam;
	Node targets;
	InputManager inputManager;

	public Listener(View view) {
		cam = view.getCamera();
		inputManager = view.getInputManager();
		targets = view.targets;
	}

	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("PickBall") && !isPressed) {
			CollisionResults results = new CollisionResults();

			Vector3f origin = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0);
			Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 1);
			direction.subtractLocal(origin).normalizeLocal();
			Ray ray = new Ray(origin, direction);

			int collisions = targets.collideWith(ray, results);

			System.out.println(collisions + " collisions");
			if (collisions > 0) {
				CollisionResult closest = results.getClosestCollision();
				System.out.println(closest.getGeometry().getName() + " " + closest.getContactPoint());
			}
		}
	}
}
