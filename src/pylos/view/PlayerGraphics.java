package pylos.view;

import pylos.model.Model;
import pylos.model.Player;

import com.jme3.material.Material;
import com.jme3.texture.Texture;

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
				Texture tex_ml = view.getAssetManager().loadTexture("Models/Board/Texture/marbrenoir.jpg");
				ballMaterial.setTexture("DiffuseMap", tex_ml);
				ballMaterial.setFloat("Shininess", 55f);
			} else {
				ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
				Texture tex_ml = view.getAssetManager().loadTexture("Models/Board/Texture/verre4.jpg");
				ballMaterial.setTexture("DiffuseMap", tex_ml);
				ballMaterial.setFloat("Shininess", 55f);
			}
		}
	}
}
