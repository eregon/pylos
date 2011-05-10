package pylos.ai.move;

import pylos.ai.State;
import pylos.model.Position;

public class Remove extends Move{

	public Position second;
	boolean hasSecond;
	
	public Remove(int x, int y, int z) {
		super(x, y, z);
		hasSecond = false;
	}
	
	public Remove(Position pos) {
		super(pos);
		hasSecond = false;
	}
	
	public Remove(int x, int y, int z, int ix, int iy, int iz) {
		super(x, y, z);
		second = Position.at(ix, iy, iz);
		hasSecond = true;
	}
	
	public Remove(Position position, int x, int y, int z) {
		super(position);
		second = Position.at(x, y, z);
		hasSecond = true;
	}

	public State doMove(State s) {
		State state = new State(s);
		state.state[position.z][position.y][position.x] = 0;
		state.ballOnSide[state.currentPlayer - 1] ++;
		if(hasSecond){
			state.state[second.z][second.y][second.z] = 0;
			state.ballOnSide[state.currentPlayer - 1] ++;
		}
		return state;
	}
	
	public String toString() {
		String s = "( REMOVE ) { " + position.x+ ", " + position.y + ", " + position.z + " }";
		if(hasSecond)
			s += "( AND ) { " + second.x + ", " + second.y + ", " + second.z + " }";	
		return s;
	}
	public boolean equals(Remove r, int x, int y, int z) {
		if(position.x == r.position.x && position.y == r.position.y && position.z == r.position.z)
			return second.x == x && second.y == y && second.z == z;
		else if(position.x == x && position.y == y && position.z == z)
			return second.x == r.position.x && second.y == r.position.y && second.z == r.position.z;
		else return false;
	}
}
