package pylos.view;

import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class Listener implements ActionListener {
	Camera cam;
	Node targets;

	public Listener(Camera cam, Node targets) {
		this.cam = cam;
		this.targets = targets;
	}

	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("PickBall") && !isPressed) {
			System.out.println("Event: Ball picked");
			CollisionResults results = new CollisionResults();
			Ray ray = new Ray(cam.getLocation(), cam.getDirection());
			targets.collideWith(ray, results);
		}
	}
}
