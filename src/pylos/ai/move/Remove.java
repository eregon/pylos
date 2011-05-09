package pylos.ai.move;

import pylos.ai.State;

public class Remove extends Move{

	public int[] second = new int[3];
	boolean hasSecond;
	
	public Remove(int x, int y, int z) {
		super(x, y, z);
		hasSecond = false;
	}
	
	public Remove(int[] pos) {
		super(pos);
		hasSecond = false;
	}
	
	public Remove(int x, int y, int z, int ix, int iy, int iz) {
		super(x, y, z);
		second[0] = ix;
		second[1] = iy;
		second[2] = iz;
		hasSecond = true;
	}
	
	public Remove(int[] position, int x, int y, int z) {
		super(position);
		second[0] = x;
		second[1] = y;
		second[2] = z;
		hasSecond = true;
	}

	public State doMove(State s) {
		State state = new State(s);
		state.state[position[2]][position[1]][position[0]] = 0;
		state.ballOnSide[state.currentPlayer - 1] ++;
		if(hasSecond){
			state.state[second[2]][second[1]][second[0]] = 0;
			state.ballOnSide[state.currentPlayer - 1] ++;
		}
		return state;
	}
	
	public String toString() {
		String s = "hasSecond = " + hasSecond + " - ";
		s += "{ " + position[0] + ", " + position[1] + ", " + position[2] + " }";
		if(hasSecond)
			s += "{ " + second[0] + ", " + second[1] + ", " + second[2] + " }";	
		return s;
	}
	public boolean equals(Remove r, int x, int y, int z) {
		if(position[0] == r.position[0] && position[1] == r.position[1] && position[2] == r.position[2])
			return second[0] == x && second[1] == y && second[2] == z;
		else if(position[0] == x && position[1] == y && position[2] == z)
			return second[0] == r.position[0] && second[1] == r.position[1] && second[2] == r.position[2];
		else return false;
	}
}
