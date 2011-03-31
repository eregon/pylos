package pylos.model;

import java.util.List;

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

	public boolean checkIfRemovable() {
		int[] isExternal = checkIfExternal(this.position);
		for (int x = isExternal[0]; x < this.position.x; x++) {
			for (int y = isExternal[1]; y < this.position.y; y++) {
				if(Model.isBallAt(Position.at(x, y, this.position.z+1)))
					return false;
			}
		}
		return true;
	}

	public boolean checkIfCarryableUp() {
		List<Position> list = Pylos.model.getWhereToPlaceBallToCarryUp(this);
		if(list.isEmpty()){
			return false;
		}
		return true;
	}
	/*
	 * This method check if a ball is in 1st or last line/column (which is important to know for checkIfRemovable)
	 */
	public int[] checkIfExternal(Position position){
		int[] pos = {position.x-1, position.y-1};
		if(position.x == 0 || position.x == 4-position.z){
			pos[0] = position.x;
		}
		if(position.y == 0 || position.y == 4-position.z){
			pos[1] = position.y;
		}
		return pos;
	}
}
