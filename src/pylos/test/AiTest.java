package pylos.test;

import pylos.ai.State;

public class AiTest extends PylosTestCase{
	public void testRemovable() {
		State s = new State();
		s.state[1][1][2] = 1;
		System.out.println(s.state[0][1][3]);
		assertFalse(s.isRemovable(3, 1, 0));
	}
}
