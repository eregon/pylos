package pylos.view;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class CameraTarget {
	Geometry geometry;

	public CameraTarget(View view) {
		geometry = new Geometry("Chasing camera target");
		geometry.setMesh(new Mesh());
		geometry.setMaterial(new Material(view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md"));
	}

	public Geometry getGeometry() {
		return geometry;
	}
}
