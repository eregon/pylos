package pylos.ai.move;

import pylos.ai.State;
import pylos.model.Position;

public class Mount extends Move{

	Remove mountedRemoved;
	
	public Mount(Position toPlace, Position toRemove) {
		super(toPlace);
		mountedRemoved = new Remove(toRemove);	
	}

	public Mount(int x, int y, int z, int ix, int iy, int iz) {
		super(x, y, z);
		mountedRemoved = new Remove(ix, iy, iz);
	}
	
	public State doMove(State s) {
		State state = mountedRemoved.doMove(s);
		state = super.doMove(state);
		return s;
	}
	
	/**
	 * changer les attributs de move
	 * Move >> place une boule qq part
	 * Mount >> enleve la boule a monter et la place avec l'attribut de move
	 * Remove >> Enleve 1 ou 2 boules (cmt identifier 1 ou 2 ? 2eme classe ?)
	 */
	public String toString() {
		return super.toString() + ", Mount";
	}

}
