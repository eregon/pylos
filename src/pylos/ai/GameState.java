package pylos.ai;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class GameState {
	public static final byte[][][] state = new byte[Model.LEVELS][][];

	public void updateFromModel() {
		Ball ball;
		for (int z = 0; z < Model.LEVELS; z++) {
			state[z] = new byte[Model.LEVELS - z][Model.LEVELS - z];
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					ball = Model.board.ballAt(Position.at(x, y, z));
					state[z][y][x] = ball == null ? 0 : ball.owner.toByte();
				}
			}
		}
	}
}
