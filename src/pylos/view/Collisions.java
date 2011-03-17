package pylos.view;

import pylos.exception.PylosError;
import pylos.model.Position;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class Collisions {
	public Geometry closest;
	CollisionResults results = new CollisionResults();
	View view;
	int n;

	public Collisions(View view) {
		this.view = view;

		Vector3f origin = view.getCamera().getWorldCoordinates(view.getInputManager().getCursorPosition(), 0);
		Vector3f direction = view.getCamera().getWorldCoordinates(view.getInputManager().getCursorPosition(), 1);
		direction.subtractLocal(origin).normalizeLocal();

		Ray ray = new Ray(origin, direction);

		n = view.targets.collideWith(ray, results);
		if (n > 0) {
			closest = results.getClosestCollision().getGeometry();
		}
	}

	public void show() {
		System.out.println(n + " collisions");
		if (n > 0) {
			CollisionResult closest = results.getClosestCollision();
			System.out.println(closest.getGeometry().getName() + " " + closest.getContactPoint());
		}
	}

	public Position getPosition() {
		if (n > 0) {
			if (closest instanceof PositionBallGraphics) {
				return ((PositionBallGraphics) closest).position;
			} else {
				throw new PylosError("Can not cast collision to PositionBall : " + closest.getClass());
			}
		} else {
			throw new PylosError("No collisions");
		}
	}

	public boolean any() {
		return n > 0;
	}
}
