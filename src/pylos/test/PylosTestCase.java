package pylos.test;

import org.junit.Before;

import pylos.model.Model;
import junit.framework.TestCase;

public class PylosTestCase extends TestCase {
	
	static Model model;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();	
		beforeEach();
	}
	
	@Before
	public void beforeEach() {
		model = new Model();
	}
}
