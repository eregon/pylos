package pylos.model;

import pylos.Pylos;
import pylos.view.ball.PlayerBallGraphics;

public class Ball {
	public Position position;
	public final Player owner;
	public boolean onBoard = false;
	public PlayerBallGraphics graphics;

	public Ball(Player owner) {
		this.owner = owner;
		Model.balls.add(this);
		graphics = new PlayerBallGraphics(this);
	}

	@Override
	public String toString() {
		return "Ball (" + owner.side + ")";
	}

	public boolean isRemovable() {
		if (!onBoard)
			return false;
		int[] isExternal = checkIfExternal(position);
		for (int x = isExternal[0]; x < position.x; x++) {
			for (int y = isExternal[1]; y < position.y; y++) {
				if (Model.ballAt(Position.at(x, y, position.z + 1)))
					return false;
			}
		}
		return true;
	}

	/**
	 * @return whether this ball can be mounted (so there is at least one place to mount it)
	 */
	public boolean isMountable() {
		return !Pylos.model.getPositionsToRise(this).isEmpty();
	}

	/**
	 * This method check if a ball is in 1st or last line/column (which is important to know for checkIfRemovable)
	 */
	public int[] checkIfExternal(Position position) {
		int[] pos = { position.x - 1, position.y - 1 };
		if (position.x == 0 || position.x == 4 - position.z) {
			pos[0] = position.x;
		}
		if (position.y == 0 || position.y == 4 - position.z) {
			pos[1] = position.y;
		}
		return pos;
	}
}
