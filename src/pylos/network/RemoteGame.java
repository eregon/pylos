package pylos.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Ball;
import pylos.model.Position;

public class RemoteGame extends UnicastRemoteObject implements RemoteGameInterface {
	protected RemoteGame() throws RemoteException {
		super();
	}

	/* call local implementation */

	public void placePlayerBall(Position position) {
		Controller.placePlayerBall(position);
	}

	public void mountPlayerBall(Ball ball) throws RemoteException {
		Controller.mountPlayerBall(ball);
	}

	public void removePlayerBall(Ball ball, boolean lastRemoved) throws RemoteException {
		Controller.removePlayerBall(ball, lastRemoved);
	}

	public void scanForRemote(String host) throws RemoteException {
		Pylos.network.scanForRemote(host);
	}
}
