package pylos.view;

import java.io.File;
import java.util.LinkedList;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Player;
import pylos.model.Position;

import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class BoardGraphics {
	public static final int BALL_SAMPLES = 32;

	public static final float BOARD_SIDE_HEIGHT = 1.22f;
	public static final float BOARD_HEIGHT = 1.185f;
	public static final float BALL_DIAMETER = 1.38f;
	public static final float HALF_SQRT_2 = FastMath.sqrt(2) / 2;
	static final float HORIZONTAL_SCALE = 0.8f;
	static final float VERTICAL_SCALE = 0.85f;
	static final int BALLS_BY_SIDE = 9;
	Spatial board;
	View view;

	public BoardGraphics(View view) {
		this.view = view;
		if (new File(Pylos.assetsPath, "Models/Board/Board.j3o").exists()) {
			board = view.getAssetManager().loadModel("Models/Board/Board.j3o");
		} else {
			board = view.getAssetManager().loadModel("Models/Board/Board.mesh.xml");
		}
		board.scale(HORIZONTAL_SCALE, VERTICAL_SCALE, HORIZONTAL_SCALE);
	}

	public Spatial getSpatial() {
		return board;
	}

	/*
	 * The balls on the board must be placed this way (to look like natural moves)
	 * 
	 * @formatter:off
	 * 13              14
	 * 11              12
	 * 9               10
	 * 7 5 3 1 0 2 4 6 8
	 */
	// @formatter:on
	public void drawBalls() {
		float distanceCenterSide = BALL_DIAMETER * (BALLS_BY_SIDE / 2);

		for (Player player : Model.players) {
			LinkedList<Ball> ballsOnBoard = new LinkedList<Ball>(), ballsOnSide = player.partitionBalls(ballsOnBoard);
			int n = ballsOnSide.size();
			int limitFront = Math.min(BALLS_BY_SIDE, n);
			int limitZNeg = (n - limitFront) / 2;
			int limitZPos = n - limitFront - limitZNeg;

			// 0-8 front line
			for (int i = 0; i < limitFront; i++) {
				view.move(ballsOnSide.pop().graphics,
						distanceCenterSide * player.side,
						BOARD_SIDE_HEIGHT,
						((i + 1) / 2) * BALL_DIAMETER * (i % 2 == 1 ? 1 : -1));
			}

			// 9-14 sides
			final int offset = BALLS_BY_SIDE / 2 - 1;
			for (int i = 0; i < limitZPos; i++) {
				view.move(ballsOnSide.pop().graphics,
						(offset - i) * BALL_DIAMETER * player.side,
						BOARD_SIDE_HEIGHT,
						distanceCenterSide);
				if (i < limitZNeg) {
					view.move(ballsOnSide.pop().graphics,
							(offset - i) * BALL_DIAMETER * player.side,
							BOARD_SIDE_HEIGHT,
							-distanceCenterSide);
				}
			}

			for (Ball ball : ballsOnBoard) {
				place(ball.graphics, ball.position);
			}
		}
	}

	public void place(Geometry ball, Position pos) {
		float offset = (Model.ballsBySideAtLevel(pos.z) - 1) * BALL_DIAMETER / 2;
		view.move(ball,
				pos.x * BALL_DIAMETER - offset,
				BoardGraphics.BOARD_HEIGHT + pos.z * BALL_DIAMETER * HALF_SQRT_2,
				pos.y * BALL_DIAMETER - offset);
	}
}
