package pylos.view;

import com.jme3.scene.Spatial;

public class BoardGraphics {

	Spatial board;

	public BoardGraphics(View view) {
		board = view.getAssetManager().loadModel("Models/Board/Board.mesh.xml");
		board.scale(5, 0.5f, 5);
	}

	public Spatial getSpatial() {
		return board;
	}

}
