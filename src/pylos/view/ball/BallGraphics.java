package pylos.view.ball;

import pylos.view.BoardGraphics;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public abstract class BallGraphics extends Geometry {
	public BallGraphics(String description) {
		super(description, makeSphere());
	}

	/**
	 * Make balls texture look better
	 * 
	 * @return
	 */
	protected static Sphere makeSphere() {
		Sphere s = new Sphere(BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_DIAMETER / 2);
		s.setTextureMode(Sphere.TextureMode.Projected);
		TangentBinormalGenerator.generate(s);
		return s;
	}
}
