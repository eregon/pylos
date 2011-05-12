package pylos.controller;

import java.rmi.RemoteException;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Position;
import pylos.network.RemoteGameInterface;

/**
 * The Game Controller.
 * Dispatch players' actions.
 */
public class GameController {
	public static void placePlayerBall(Position position) {
		Controller.placePlayerBall(position);
		if (Pylos.network.isPaired()) {
			try {
				for (RemoteGameInterface remoteGame : Pylos.network.remoteGames) {
					remoteGame.placePlayerBall(position);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public static void mountPlayerBall(Ball ball) {
		Controller.mountPlayerBall(ball);
		if (Pylos.network.isPaired()) {
			try {
				for (RemoteGameInterface remoteGame : Pylos.network.remoteGames) {
					remoteGame.mountPlayerBall(ball.position);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public static void removePlayerBall(Ball ball, boolean lastRemoved) {
		Controller.removePlayerBall(ball, lastRemoved);
		if (Pylos.network.isPaired()) {
			try {
				for (RemoteGameInterface remoteGame : Pylos.network.remoteGames) {
					remoteGame.removePlayerBall(ball.position, lastRemoved);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
