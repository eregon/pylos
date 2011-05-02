package pylos.model;

import pylos.Pylos;
import pylos.view.ball.PlayerBallGraphics;

/**
 * a player ball.
 * position can be null if onBoard is false
 */
public class Ball {
	public Position position;
	public final Player owner;
	public boolean onBoard = false;
	public PlayerBallGraphics graphics;

	public Ball(Player owner) {
		this.owner = owner;
		Model.board.balls.add(this);
		graphics = new PlayerBallGraphics(this);
	}

	@Override
	public String toString() {
		return "Ball (" + owner.side + ", " + position + ")";
	}

	public void placeAt(Position position) {
		onBoard = true;
		this.position = position;
		Pylos.logger.info(owner + " place a ball at " + position);
	}

	public void removeFromBoard() {
		Pylos.logger.info(owner + " remove a ball at " + position);
		onBoard = false;
		// position = null; // Keep the position as it might be useful (e.g.: to mount it)
	}

	public boolean isRemovable() {
		if (!onBoard)
			return false;

		for (int x = position.x - 1; x <= position.x; x++) {
			for (int y = position.y - 1; y <= position.y; y++) {
				if (Position.isValid(x, y, position.z + 1) && Model.board.anyBallAt(Position.at(x, y, position.z + 1)))
					return false;
			}
		}
		return true;
	}

	/**
	 * @return whether this ball can be mounted (so there is at least one place
	 *         to mount it)
	 */
	public boolean isMountable() {
		return isRemovable() && !Model.getPositionsToMount(this).isEmpty();
	}

	public boolean isMountableByCurrentPlayer() {
		return owner == Model.currentPlayer && isMountable();
	}

	public boolean isRemovableByCurrentPlayer() {
		return owner == Model.currentPlayer && isRemovable();
	}
}
