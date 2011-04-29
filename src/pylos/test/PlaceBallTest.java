package pylos.test;

import java.util.LinkedList;
import java.util.List;

import pylos.model.Model;
import pylos.model.Position;

public class PlaceBallTest extends PylosTestCase {

	/**
	 * Test
	 */
	public void testCanPlaceBallAt() {
		assertTrue(Model.canPlaceBallAt(Position.at(0, 0, 0)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1))); // plateau vide donc pas de sens placer boule z = 1

		List<Position> balls = MainTest.gameSample();
		for (Position pos : balls) {
			assertFalse(Model.canPlaceBallAt(pos));
		}

		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 1)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 2)));
		assertFalse(Model.canPlaceBallAt(Position.at(0, 0, 3)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 2, 0)));
		assertTrue(Model.canPlaceBallAt(Position.at(1, 0, 1)));
	}

	public void complexTestCanPlaceBallAt() {
		List<Position> balls = MainTest.complexGameSample();

		/**
		 * o.oo
		 * oooo ...
		 * ooo. oo. ..
		 * ooo. oo. .. .
		 */

		for (Position position : balls) {
			assertFalse(Model.canPlaceBallAt(position));
		}

		List<Position> accessible = new LinkedList<Position>();
		accessible.add(Position.at(1, 0, 0));
		accessible.add(Position.at(3, 2, 0));
		accessible.add(Position.at(3, 3, 0));
		accessible.add(Position.at(2, 0, 1));
		accessible.add(Position.at(0, 0, 2));

		for (Position position : accessible) {
			assertTrue(Model.canPlaceBallAt(position));
		}
	}
}
