package pylos.view.ball;

import pylos.view.View;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public class HighlightBallGraphics extends BallGraphics {
	public HighlightBallGraphics() {
		super("Highlight Ball");
	}

	public void create(View view) {
		Material mat = new Material(view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		setMaterial(mat);
	}
}
