package pylos.test;

import pylos.ai.State;
import pylos.ai.StateIterator;
import pylos.ai.StateNode;

public class It_test extends PylosTestCase {

	public void testIterator() {
		State s = new State();
		s.printState();
		StateIterator iterator = new StateIterator(s);
		StateNode sn = iterator.nextElement();
		sn.state.printState();
	}
}