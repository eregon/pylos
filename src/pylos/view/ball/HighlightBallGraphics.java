package pylos.view.ball;

import pylos.view.BoardGraphics;
import pylos.view.View;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class HighlightBallGraphics extends Geometry {
	public HighlightBallGraphics() {
		super("Highlight Ball", new Sphere(50, 50, BoardGraphics.BALL_DIAMETER / 2));
	}

	public void create(View view) {
		Material mat = new Material(view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		setMaterial(mat);
	}
}
