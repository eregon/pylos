package pylos.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class Runner {
	public static Test suite() {
		TestSuite suite = new TestSuite(Runner.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(AiTest.class);
		suite.addTestSuite(AnyLineOrSquareTest.class);
		suite.addTestSuite(MainTest.class);
		suite.addTestSuite(MountBallTest.class);
		suite.addTestSuite(PlaceBallTest.class);
		// $JUnit-END$
		return suite;
	}
}
