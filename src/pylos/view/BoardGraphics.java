package pylos.view;

import java.util.LinkedList;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Player;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class BoardGraphics {
	static final float HORIZONTAL_SCALE = 5;
	static final float VERTICAL_SCALE = 0.5f;
	public static final float BOARD_HEIGHT = 1f;
	static final int BALLS_BY_SIDE = 9;
	static final int BALLS_BY_SMALL_SIDE = 3;
	public static final float BALL_DIAMETER = 1.38f;
	Spatial board;

	public BoardGraphics(View view) {
		board = view.getAssetManager().loadModel("Models/Board/Board.mesh.xml");
		board.scale(HORIZONTAL_SCALE, VERTICAL_SCALE, HORIZONTAL_SCALE);
	}

	public Spatial getSpatial() {
		return board;
	}

	public void drawBalls() {
		float distanceCenterSide = BALL_DIAMETER * (BALLS_BY_SIDE / 2);

		for (Player player : Model.players) {
			LinkedList<Ball> ballsInSide = player.ballsInSide();
			int n = ballsInSide.size();
			int limitFront = Math.min(BALLS_BY_SIDE, n);
			int limitZNeg = (n - limitFront) / 2;
			int limitZPos = n - limitFront - limitZNeg;

			for (int i = 0; i < limitZPos; i++) {
				ballsInSide.pop().graphics.center().move(
						(i + 1) * BALL_DIAMETER * player.side,
						BOARD_HEIGHT,
						distanceCenterSide
						);
			}

			for (int i = 0; i < limitFront; i++) {
				ballsInSide.pop().graphics.center().move(
						distanceCenterSide * player.side,
						BOARD_HEIGHT,
						(BALLS_BY_SIDE / 2 - i) * BALL_DIAMETER
						);
			}

			for (int i = 0; i < limitZNeg; i++) {
				ballsInSide.pop().graphics.center().move(
						(BALLS_BY_SIDE / 2 - i - 1) * BALL_DIAMETER * player.side,
						BOARD_HEIGHT,
						-distanceCenterSide
						);
			}
		}
	}

	public void place(Geometry ball, int x, int y, int level) {
		float offset = (Model.ballsBySideAtLevel(level) - 1) * BALL_DIAMETER / 2;
		ball.center().move(
				x * BALL_DIAMETER - offset,
				BoardGraphics.BOARD_HEIGHT + level * BALL_DIAMETER,
				y * BALL_DIAMETER - offset
				);
	}
}
