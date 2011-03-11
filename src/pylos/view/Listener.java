package pylos.view;

import pylos.Pylos;

import com.jme3.input.controls.ActionListener;

public class Listener implements ActionListener {
	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("PickBall") && !isPressed) {
			Collisions collisions = new Collisions(Pylos.view);
			collisions.show();
		}
	}
}
