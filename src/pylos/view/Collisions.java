package pylos.view;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Collisions {
	View view;
	CollisionResults results = new CollisionResults();
	int n;

	public Collisions(View view) {
		this.view = view;

		Vector3f origin = view.getCamera().getWorldCoordinates(view.getInputManager().getCursorPosition(), 0);
		Vector3f direction = view.getCamera().getWorldCoordinates(view.getInputManager().getCursorPosition(), 1);
		direction.subtractLocal(origin).normalizeLocal();

		Ray ray = new Ray(origin, direction);

		n = view.targets.collideWith(ray, results);
	}

	public Spatial getClosestSpatial() {
		if (n > 0) {
			return view.targets.getChild(results.getClosestCollision().getGeometry().getName());
		} else {
			return null;
		}
	}

	public void show() {
		System.out.println(n + " collisions");
		if (n > 0) {
			CollisionResult closest = results.getClosestCollision();
			System.out.println(closest.getGeometry().getName() + " " + closest.getContactPoint());
		}
	}

	public boolean any() {
		return n > 0;
	}
}
