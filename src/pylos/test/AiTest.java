package pylos.test;

import java.util.Iterator;

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
		assertFalse(s.isRemovable(Position.at(3, 1, 0)));
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
	
	public void testEvaluate2() {
		GameState gs = new GameState();
		EvaluateGame.evaluate(gs);
		assertEquals(0, gs.score);
		
		MainTest.gameSample();
		gs = new GameState();
		EvaluateGame.evaluate(gs);
		assertEquals(-24, gs.score);
		
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
		assertTrue(s.accessible(1, 0, 1));
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
		Move m = new Mount(Position.at(1, 0, 1), Position.at(2, 2, 0));
		s = m.doMove(s);
//		System.out.println(m.toString());
//		s.printState();
	}
	
	public void testStateCopy() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		State s = new State();
		State s2 = new State(s);
		Move m = new Remove(Position.at(2, 1, 0));
		s2 = m.doMove(s);
//		s2.printState();
		s2.switchPlayer();
		assertEquals(2, s2.currentPlayer);
		assertEquals(1, s.currentPlayer);
		m = new Remove(Position.at(2, 0, 0));
		s2 = new State(s);
		s2 = m.doMove(s);
//		s2.printState();
		
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
//		s.printState();
		Move m = new Move(3, 3, 0);
		m.hasRemoveStep(s);
		assertTrue(m.removeStep);
		assertEquals(3, s.lines(Position.at(3, 3, 0)).size());
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
		/**					7 moves normal - 1 mount (8)
		 * ooo.				3.0.0 > 5 + 10 + 4 
		 * ooo. o..			3.1.0 > 19
		 * ..o. ...			2.3.0 > 19
		 * .... ...
		 */
		GameState gs = new GameState();
		gs.generatePosibleMoves();
		for (Move m : gs.possibleMoves) {
//			System.out.println(m);
			if(m.removeStep)
				assertEquals(19, m.removables.size());
		}
		assertEquals(11, gs.possibleMoves.size());
	}
	public void testIterator() {
		Model.currentPlayer.putBallOnBoard(Position.at(0, 0, 0));
		/**
		 * o...
		 * .... ...
		 * .... ...
		 * .... ...
		 */
		GameState gs = new GameState();
		gs.generatePosibleMoves();
		Iterator<GameState> i = gs.iterator();
		GameState gsI = i.next();
		GameState gsII = i.next();
		gsII.state.printState();
	}
	
	public void testIterator2() {
		MainTest.gameSample();
		/**
		 * ooo.
		 * ooo. o..
		 * ..o. ...
		 * .... ...
		 */
		GameState gs = new GameState();
		gs.generatePosibleMoves();
		Iterator<GameState> i = gs.iterator();
		GameState gsI = i.next();
		gsI = i.next();
		gsI = i.next();
		gsI.generatePosibleMoves();
		gsI = gsI.iterator().next();
		gsI.state.printState();
	}
}
