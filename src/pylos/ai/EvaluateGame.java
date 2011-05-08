package pylos.ai;

import java.util.LinkedList;
import java.util.List;

import pylos.model.Model;



public class EvaluateGame {

	public static int evaluate(State s) {
		return countBallInHandPoint(s) + countRemovableBallsPoint(s) + countRawsPoint(s);
		
	}

	public static int countRawsPoint(State s) {
		int score = 0;
		int scoreTmp = 0;
		List<List<int[]>> lines = new LinkedList<List<int[]>>();
		for (int z = 0; z < Model.LEVELS - 2; z++) {
			for (int xy = 0; xy < Model.LEVELS - z; xy++) {
				int[] pos = {xy, xy, z};
				for (List<int[]> line : s.linesNoDiagonales(pos)) {
					lines.add(line);
				}
				for(List<int[]> line : s.getDiagonales(z)) {
					lines.add(line);
				}
			}
			for (int x = 0; x < Model.LEVELS_1 - z; x++) {
				for (int y = 0; y < Model.LEVELS_1 - z; y++) {
					lines.add(s.square(x, y, z));
				}
			}
		}
		for (List<int[]> list : lines) {
			scoreTmp = 0;
			for (int[] p : list) {
				int ball = s.state[p[2]][p[1]][p[0]];
				if(ball != 0) {
					if(ball == s.currentPlayer) {
						scoreTmp ++;
					} else {
						scoreTmp = 0;
						break;
					}
				}
			}
			switch (scoreTmp) {
			case 2:
				score += 4;
				break;
				
			case 3:
				score += 8;
				break;
				
			default:
				break;
			}
			
		}
		return score;
	}

	public static int countRemovableBallsPoint(State s) {
		int score = 0;
		for (int z = 0; z < Model.LEVELS; z++) {
			for (int y = 0; y < Model.LEVELS - z; y++) {
				for (int x = 0; x < Model.LEVELS - z; x++) {
					if(s.state[z][y][x] == s.currentPlayer && s.isRemovable(x, y, z))
						score ++;
				}
				
			}
		}
		return score;
	}

	public static int countBallInHandPoint(State s) {
		return s.ballOnSide[s.currentPlayer - 1] * 10;
	}
}
