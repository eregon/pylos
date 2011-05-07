package pylos.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Position;

public class RemoteGame extends UnicastRemoteObject implements RemoteGameInterface {
	protected RemoteGame() throws RemoteException {
		super();
	}

	/* call local implementation */

	public void placePlayerBall(Position position) {
		Controller.placePlayerBall(position);
	}

	public void scanForRemote(String host) throws RemoteException {
		Pylos.network.scanForRemote(host);
	}
}
