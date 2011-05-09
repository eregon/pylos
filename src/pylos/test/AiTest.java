package pylos.test;

import java.util.List;

import pylos.ai.EvaluateGame;
import pylos.ai.GameState;
import pylos.ai.State;
import pylos.ai.move.Mount;
import pylos.ai.move.Move;
import pylos.ai.move.Remove;
import pylos.model.Model;
import pylos.model.Position;

public class AiTest extends PylosTestCase{
	public void testRemovable() {
		State s = new State();
		s.state[1][1][2] = 1;
		assertFalse(s.isRemovable(3, 1, 0));
	}
	public void testEvaluate() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		State s = new State();
		assertEquals(52, EvaluateGame.countRawsPoint(s));
		assertEquals(4, EvaluateGame.countRemovableBallsPoint(s));
		assertEquals(70, EvaluateGame.countBallInHandPoint(s));
	}
	public void testAccessible() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		State s = new State();
		assertTrue(s.accessible(3, 0, 0));
	}
	
	public void testMount() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		State s = new State();
		Move m = new Mount(1, 0, 1, 2, 2, 0);
		s = m.doMove(s);
//		for (int z = 0; z < Model.LEVELS; z++) {		// print state
//			System.out.println();
//			for (int x = 0; x < Model.LEVELS - z; x++) {
//				System.out.println();
//				for (int y = 0; y < Model.LEVELS - z; y++) {
//					System.out.print(s.state[z][x][y]);
//				}
//				
//			}
//			
//		}
	}
	
	public void testLines() {
		MainTest.complexGameSample();
		/**
		 * o..o
		 * ooo. ...
		 * ooo. oo. ..
		 * ooo. oo. .. .
		 */
		// 7 + 7 * 6 = 49
		// + si j'enleve 0.1.1; 0.1.0 devient removable... >> + 4
		State s = new State();
//		for (int z = 0; z < Model.LEVELS; z++) {		// print state
//			System.out.println();
//			for (int x = 0; x < Model.LEVELS - z; x++) {
//				System.out.println();
//				for (int y = 0; y < Model.LEVELS - z; y++) {
//					System.out.print(s.state[z][x][y]);
//				}
//				
//			}	
//		}
		Move m = new Move(3, 3, 0);
		m.hasRemoveStep(s);
		assertTrue(m.removeStep);
		int[] pos = {3, 3, 0};
		assertEquals(3, s.lines(pos).size());
//		for (List<int[]> i : s.lines(pos)) {		// print lines
//			System.out.println();
//			for (int[] js : i) {
//				System.out.print("{ ");
//				for (int j : js) {
//					System.out.print(j + ", ");
//				}
//				System.out.print("}");
//			}
//			
//		}
		assertEquals(32, m.removables.size());
	}
	
	public void testGenerateMove() {
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		/**
		 * o...
		 * .... ...
		 * .... ...
		 * .... ...
		 */
		GameState gs = new GameState();
		gs.generatePosibleMoves();
		assertEquals(15, gs.possibleMoves.size());
//		for (Move m : gs.possibleMoves) {
//			System.out.println(m.toString());
//		}
	}
	
	public void testGenerateMove2() {
		MainTest.gameSample();
		/**					7 moves normal - 1 mount
		 * ooo.				3.0.0 > 5 + 10 + 4 
		 * ooo. o..			3.1.0 > 19
		 * ..o. ...			2.3.0 > 19
		 * .... ...
		 */
		GameState gs = new GameState();
		
		gs.generatePosibleMoves();
		for (int z = 0; z < Model.LEVELS; z++) {		// print state
		System.out.println();
		for (int x = 0; x < Model.LEVELS - z; x++) {
			System.out.println();
			for (int y = 0; y < Model.LEVELS - z; y++) {
				System.out.print(gs.state.state[z][x][y]);
			}
			
		}	
	}
//		assertEquals(65, gs.possibleMoves.size());
		for (Move m : gs.possibleMoves) {
			System.out.println(m.toString());
		}
	}
//	public void testIterator() {
//		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
//		/**
//		 * o...
//		 * .... ...
//		 * .... ...
//		 * .... ...
//		 */
//		GameState gs = new GameState();
//		gs.generatePosibleMoves();
//		GameState gsI = gs.iterator().next();
//		gsI = gs.iterator().next();
//		for (int z = 0; z < Model.LEVELS; z++) {		// print state
//			System.out.println();
//			for (int x = 0; x < Model.LEVELS - z; x++) {
//				System.out.println();
//				for (int y = 0; y < Model.LEVELS - z; y++) {
//					System.out.print(gs.state.state[z][y][x]);
//				}
//
//			}	
//		}
//	}
}
