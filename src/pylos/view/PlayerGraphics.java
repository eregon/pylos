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
				ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
				Texture tex_ml = view.getAssetManager().loadTexture("Models/Board/Texture/marbrenoir.jpg");
				ballMaterial.setTexture("ColorMap", tex_ml);
				// ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
				// ballMaterial.setFloat("Shininess", 15);
				// ballMaterial.setBoolean("UseMaterialColors", true);
				// ballMaterial.setColor("Ambient", ColorRGBA.Black);
				// ballMaterial.setColor("Diffuse", ColorRGBA.Gray);
				// ballMaterial.setColor("Specular", new ColorRGBA(0, 0, 0.8f, 1));
			} else {
				ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
				Texture tex_ml = view.getAssetManager().loadTexture("Models/Board/Texture/verre4.jpg");
				ballMaterial.setTexture("ColorMap", tex_ml);
				// ballMaterial = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
				// ballMaterial.setFloat("Shininess", 15);
				// ballMaterial.setBoolean("UseMaterialColors", true);
				// ballMaterial.setColor("Ambient", ColorRGBA.Black);
				// ballMaterial.setColor("Diffuse", ColorRGBA.DarkGray);
				// ballMaterial.setColor("Specular", ColorRGBA.Orange);
			}
		}
	}
}
