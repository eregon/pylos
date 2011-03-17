package pylos.view;

import java.util.LinkedList;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Player;
import pylos.model.Position;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class BoardGraphics {
	public static final int BALL_SAMPLES = 32;

	public static final float BOARD_HEIGHT = 1f;
	public static final float BALL_DIAMETER = 1.38f;
	static final float HORIZONTAL_SCALE = 5;
	static final float VERTICAL_SCALE = 0.5f;
	static final int BALLS_BY_SIDE = 9;
	static final int BALLS_BY_SMALL_SIDE = 3;
	Spatial board;

	public BoardGraphics(View view) {
		board = view.getAssetManager().loadModel("Models/Board/Board.mesh.xml");
		board.scale(HORIZONTAL_SCALE, VERTICAL_SCALE, HORIZONTAL_SCALE);
	}

	public Spatial getSpatial() {
		return board;
	}

	/*
	 * The balls on the board must be placed this way (to look like natural moves)
	 * 13                      14
	 * 11                      12
	 *  9                      10
	 *  7  5  3  1  0  2  4  6  8
	 */
	public void drawBalls() {
		float distanceCenterSide = BALL_DIAMETER * (BALLS_BY_SIDE / 2);

		for (Player player : Model.players) {
			LinkedList<Ball> ballsOnBoard = new LinkedList<Ball>(), ballsInSide = player.partitionBalls(ballsOnBoard);
			int n = ballsInSide.size();
			int limitFront = Math.min(BALLS_BY_SIDE, n);
			int limitZNeg = (n - limitFront) / 2;
			int limitZPos = n - limitFront - limitZNeg;

			// 0-8 front line
			for (int i = 0; i < limitFront; i++) {
				ballsInSide.pop().graphics.center().move(
						distanceCenterSide * player.side,
						BOARD_HEIGHT,
						((i + 1) / 2) * BALL_DIAMETER * (i % 2 == 1 ? 1 : -1)
						);
			}

			// 9-14 sides
			final int offset = BALLS_BY_SIDE / 2 - 1;
			for (int i = 0; i < limitZPos; i++) {
				ballsInSide.pop().graphics.center().move(
						(offset - i) * BALL_DIAMETER * player.side,
						BOARD_HEIGHT,
						distanceCenterSide
						);
				if (i < limitZNeg) {
					ballsInSide.pop().graphics.center().move(
							(offset - i) * BALL_DIAMETER * player.side,
							BOARD_HEIGHT,
							-distanceCenterSide
							);
				}
			}

			for (Ball ball : ballsOnBoard) {
				place(ball.graphics, ball.position);
			}
		}
	}

	public void place(Geometry ball, Position pos) {
		float offset = (Model.ballsBySideAtLevel(pos.z) - 1) * BALL_DIAMETER / 2;
		ball.center().move(
				pos.x * BALL_DIAMETER - offset,
				BoardGraphics.BOARD_HEIGHT + pos.z * BALL_DIAMETER,
				pos.y * BALL_DIAMETER - offset
				);
	}
}
