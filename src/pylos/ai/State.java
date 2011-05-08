package pylos.ai;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class State {
	public byte[][][] state = new byte[Model.LEVELS][][];
	public int ballOnSide = 0;
	public int currentPlayer;
	
	public State() {
		updateFromModel();
	}
	
	public void updateFromModel() {
		currentPlayer = Model.currentPlayer.toByte();
		Ball ball;
		for (int z = 0; z < Model.LEVELS; z++) {
			state[z] = new byte[Model.LEVELS - z][Model.LEVELS - z];
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					ball = Model.board.ballAt(Position.at(x, y, z));
					if(!ball.onBoard)
						ballOnSide ++;
					state[z][y][x] = ball == null ? 0 : ball.owner.toByte();
				}
			}
		}
	}
	
	public boolean isRemovable(int x, int y, int z) {
		return false;
	}

	public boolean isMountable(int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * on sait que la boule est montable, on veut toutes les positions ou elle peut aller (faire attention aux removables)
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Move addPositionToMount(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean accessible() {
		// TODO Auto-generated method stub
		return false;
	}

}
