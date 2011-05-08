package pylos.ai;



public class EvaluateGame {

	public static int evaluate(State state) {
		// TODO Auto-generated method stub
		return 0;
	}
//	public static void evaluate(GameState gs) {
//		countBallInHandPoint(gs);
//		countRemovableBallsPoint(gs);
//		countRawsPoint(gs);
//	}
//
//	private void countRawsPoint(GameState gs) {
//		/**
//		 * recup list des boules du joueur sur le plateau
//		 * récup list des lignes/carré que forment chaque boules
//		 * test si carré ou lignes partiel ! pas compter 2 fois le mm carré, et pas compté si carré occupé par autre joueur
//		 */
////		List<Ball> balls = gs.onBoard;
////		List<List<Position>> lines;
////		for (Ball ball : balls) {
////			lines = ball.position.lines();
////		}
//	}
//
//	private void countRemovableBallsPoint(GameState gs) {
//		for (int z = 0; z < Model.LEVELS; z++) {
//			for (int y = 0; y < Model.LEVELS - z; y++) {
//				for (int x = 0; x < Model.LEVELS - z; x++) {
//					if(gs.state.state[z][y][x] == gs.state.currentPlayer && gs.state.isRemovable(x, y, z))
//						gs.addScore(1);
//				}
//				
//			}
//		}
//	}
//
//	private void countBallInHandPoint(GameState gs) {
//		gs.addScore(gs.state.ballOnSide * 5);
//	}
}
