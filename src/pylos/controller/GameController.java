package pylos.controller;

import java.rmi.RemoteException;

import pylos.Config;
import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Position;

/**
 * The Game Controller.
 * Dispatch players' actions.
 */
public class GameController {
	public static void placePlayerBall(Position position) {
		Controller.placePlayerBall(position);
		if (Config.NETWORK && Pylos.network.isPaired()) {
			try {
				Pylos.network.remoteGame.placePlayerBall(position);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public static void mountPlayerBall(Ball ball) {
		Controller.mountPlayerBall(ball);
		if (Config.NETWORK && Pylos.network.isPaired()) {
			try {
				Pylos.network.remoteGame.mountPlayerBall(ball);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public static void removePlayerBall(Ball ball, boolean lastRemoved) {
		Controller.removePlayerBall(ball, lastRemoved);
		if (Config.NETWORK && Pylos.network.isPaired()) {
			try {
				Pylos.network.remoteGame.removePlayerBall(ball, lastRemoved);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
