package pylos.test;

import junit.framework.TestCase;

import org.junit.Before;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Model;

public class PylosTestCase extends TestCase {
	static Ball ball;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Pylos.initialize();
		beforeEach();
	}

	@Before
	public void beforeEach() {
		Model.initialize();
	}
}
