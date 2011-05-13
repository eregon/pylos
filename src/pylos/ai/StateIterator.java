package pylos.ai;

import java.util.Enumeration;
import java.util.List;

import pylos.ai.move.MountBall;
import pylos.ai.move.Ply;
import pylos.model.Model;
import pylos.model.Position;

public class StateIterator implements Enumeration<StateNode> {

	State state;
	StateNode next;
	int x, y, z;

	private Enumeration<StateNode> moves;

	public StateIterator(State s) {
		state = s;
		x = y = z = 0;
	}

	public boolean hasMoreElements() {
		if (next == null)
			next = generateNext();
		return next != null;
	}

	public StateNode nextElement() {
		StateNode r = null;
		if (hasMoreElements()) {
			r = next;
			next = null;
		}
		return r;
	}

	private StateNode generateNext() {
		if (moves != null && moves.hasMoreElements())
			return moves.nextElement();

		if (z == Model.LEVELS)
			return null;

		Position p = Position.at(x, y, z);

		if (state.accessible(p)) {
			if (state.createsLineOrSquare(p)) {
				moves = new RemovableEnumerator(state, p);
			} else {
				// Aucune generateNextboule ne peut être enlevée.
				incrementVariables();
				Ply ply = new Ply(p, new Position[0]);
				return new StateNode(ply, ply.apply(state));
			}
		} else if (state.isMountableByCurrentPlayer(p)) {
			moves = new MountableEnumerator(p);
		} else {
			moves = null;
		}

		incrementVariables();
		return generateNext();
	}

	private void incrementVariables() {
		x++;
		if (x == Model.LEVELS - z) {
			x = 0;
			y++;
		}
		if (y == Model.LEVELS - z) {
			y = 0;
			z++;
		}
	}

	private class RemovableEnumerator implements Enumeration<StateNode> {

		State state, ns;
		StateNode next;

		int x1, y1, z1, x2, y2, z2;
		Position to;

		public RemovableEnumerator(State state, Position p) {
			Ply ply = new Ply(p, null);
			to = p;
			this.state = state;
			ns = ply.apply(state);
			ns.currentPlayer = state.currentPlayer; // ply.apply change the currentplayer so we need to reinitialize
			next = null;
			x1 = y1 = z1 = 0;
			x2 = y2 = z2 = 0;
		}

		public boolean hasMoreElements() {
			if (next == null)
				next = generateNext();
			return next != null;
		}

		public StateNode nextElement() {
			StateNode r = null;
			if (hasMoreElements()) {
				r = next;
				next = null;
			}
			return r;
		}

		private StateNode generateNext() {
			if (z1 == Model.LEVELS)
				return null;

			Position p1 = Position.at(x1, y1, z1), p2 = Position.at(x2, y2, z2);
			StateNode returnValue = null;

			if (p1 == p2) {
				if (ns.isRemovableByCurrentPlayer(p1)) {
					Ply p = new Ply(to, new Position[] { p1 });
					returnValue = new StateNode(p, p.apply(state));
				}
			} else {
				if (ns.isRemovableByCurrentPlayer(p1) && ns.isRemovableByCurrentPlayer(p2)) {
					Ply p = new Ply(to, new Position[] { p1, p2 });
					returnValue = new StateNode(p, p.apply(state));
				}
			}

			incrementVariables();
			return returnValue == null ? generateNext() : returnValue;
		}

		private void incrementVariables() {
			x2++;
			if (x2 == Model.LEVELS - z2) {
				y2++;
				if (y2 == Model.LEVELS - z2) {
					z2++;
					if (z2 == Model.LEVELS) {
						x1++;
						if (x1 == Model.LEVELS - z1) {
							y1++;
							if (y1 == Model.LEVELS - z1) {
								z1++;
								if (z1 == Model.LEVELS) {

								} else {
									x1 = 0;
									y1 = 0;
								}
							} else {
								x1 = 0;
							}
						}

						x2 = x1;
						y2 = y1;
						z2 = z1;

					} else {
						x2 = 0;
						y2 = 0;
					}
				} else {
					x2 = 0;
				}
			}
		}
	}

	private class MountableEnumerator implements Enumeration<StateNode> {

		List<Position> accessibles;
		RemovableEnumerator current;
		State new_state;
		StateNode currentState;
		Position from;

		public MountableEnumerator(Position p) {
			from = p;
			accessibles = state.addPositionToMount(p);
			Ply ply = new Ply(null, new Position[] { p });
			new_state = ply.apply(state);
			generateNext();
		}

		public boolean hasMoreElements() {
			if (current == null) {
				return currentState != null;
			} else {
				if (current.hasMoreElements())
					return true;
				else {
					generateNext();
					return hasMoreElements();
				}
			}
		}

		public StateNode nextElement() {
			StateNode next;
			if (currentState != null) {
				next = currentState;
				generateNext();
			} else if (current != null) {
				if (current.hasMoreElements()) {
					next = current.nextElement();
					next = new StateNode(new MountBall(from, next.ply.at, next.ply.removes), state);
				} else {
					generateNext();
					return nextElement();
				}
			} else {
				return null;
			}

			return next;
		}

		private void generateNext() {
			if (accessibles.isEmpty()) {
				current = null;
				currentState = null;
			} else {
				Position new_pos = accessibles.get(0);
				accessibles.remove(new_pos);

				if (new_state.createsLineOrSquare(new_pos)) {
					current = new RemovableEnumerator(new_state, new_pos);
					currentState = null;
				} else {
					Ply ply = new MountBall(from, new_pos, new Position[0]);
					currentState = new StateNode(ply, ply.apply(state));
					current = null;
				}
			}
		}
	}
}
