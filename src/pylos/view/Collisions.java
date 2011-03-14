package pylos.view;

import pylos.exception.PylosError;
import pylos.model.PositionBall;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

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

	public void show() {
		System.out.println(n + " collisions");
		if (n > 0) {
			CollisionResult closest = results.getClosestCollision();
			System.out.println(closest.getGeometry().getName() + " " + closest.getContactPoint());
		}
	}

	public PositionBall getPositionBall() {
		if (n > 0) {
			Geometry closest = results.getClosestCollision().getGeometry();
			if (closest instanceof PositionBallGraphics) {
				return ((PositionBallGraphics) closest).model;
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
