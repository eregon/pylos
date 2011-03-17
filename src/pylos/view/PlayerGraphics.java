package pylos.view;

import pylos.model.Model;
import pylos.model.Player;

import com.jme3.material.Material;

public class PlayerGraphics {
	public Material ballMaterial;
	Player model;

	public PlayerGraphics(Player model) {
		this.model = model;
	}

	public void create(View view) {
		if (ballMaterial == null) {
			if (model == Model.player1) {
				ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
			} else {
				ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
				ballMaterial.setFloat("Shininess", 0.5f);
			}
		}
	}
}
