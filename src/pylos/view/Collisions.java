package pylos.view;

import java.util.ConcurrentModificationException;

import pylos.exception.PylosError;
import pylos.model.Ball;
import pylos.model.Position;
import pylos.view.ball.PlayerBallGraphics;
import pylos.view.ball.PositionBallGraphics;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class Collisions {
	public Geometry closest;
	CollisionResults results = new CollisionResults();
	int n;

	public Collisions(View view, Node targets) {
		if (targets == null)
			throw new PylosError("targets must not be null when creating Collisions");

		Vector3f origin = view.getCamera().getWorldCoordinates(view.getInputManager().getCursorPosition(), 0);
		Vector3f direction = view.getCamera().getWorldCoordinates(view.getInputManager().getCursorPosition(), 1);
		direction.subtractLocal(origin).normalizeLocal();

		Ray ray = new Ray(origin, direction);

		try {
			synchronized (view) {
				n = targets.collideWith(ray, results);
			}
			if (n > 0)
				closest = results.getClosestCollision().getGeometry();
		} catch (ConcurrentModificationException e) {
			// Network methods can cause this exception
			System.out.println(e);
			n = 0;
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
			if (closest instanceof PositionBallGraphics)
				return ((PositionBallGraphics) closest).position;
			else if (closest instanceof PlayerBallGraphics)
				return ((PlayerBallGraphics) closest).model.position;
			else
				throw new PylosError("Can not cast collision to Position : " + closest.getClass());
		} else
			throw new PylosError("No collisions");
	}

	public Ball getBall() {
		if (n > 0) {
			if (closest instanceof PlayerBallGraphics)
				return ((PlayerBallGraphics) closest).model;
			else
				throw new PylosError("Can not cast collision to Ball : " + closest.getClass());
		} else
			throw new PylosError("No collisions");
	}

	public boolean any() {
		return n > 0;
	}
}
