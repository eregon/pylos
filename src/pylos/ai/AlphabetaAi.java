package pylos.ai;

public class AlphabetaAi {
	/**
	 * if depth == 0 then AI makes random moves
	 * @param depth
	 */
	public void AI(int depth) {
		/**
		 * get player	commence tjrs par max
		 * if(depht != 0 || !endGame)
		 * 		Parcourir les coups possibles
		 * 			joue le coup >> new GameState gs
		 * 			calcule MAX (gs, depth - 1)
		 */
		GameState gs = new GameState();	// crée un gameState à partir de la situation actuelle et en calcule le score
		if(depth == 0) {
//			gs.generatePosibleMoves();
//			gs.possibleMoves.iterator().next().makeMove();
			return;
		}
		
	}
}
