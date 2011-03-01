package pylos.view;

import pylos.model.Model;
import pylos.model.Player;

import com.jme3.scene.Spatial;

public class BoardGraphics {
	static final float HORIZONTAL_SCALE = 5;
	static final float VERTICAL_SCALE = 0.5f;
	static final float BOARD_HEIGHT = 0.75f;
	static final float BOARD_SIZE = 5.5f;
	Spatial board;

	public BoardGraphics(View view) {
		board = view.getAssetManager().loadModel("Models/Board/Board.mesh.xml");
		board.scale(HORIZONTAL_SCALE, VERTICAL_SCALE, HORIZONTAL_SCALE);
	}

	public Spatial getSpatial() {
		return board;
	}

	public void drawBalls() {
		for (Player player : Model.players) {
			for (int i = 0; i < Model.nbBalls/2; i++) {
				player.balls[i].graphics.setPosition(
						BOARD_SIZE*player.side,
						BOARD_HEIGHT,
						(i-Model.nbBalls/4)*BallGraphics.DIAMETER
				);
			}
		}
	}
}
