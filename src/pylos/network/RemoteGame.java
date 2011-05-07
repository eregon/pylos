package pylos.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import pylos.Pylos;
import pylos.controller.Controller;
import pylos.model.Model;
import pylos.model.Position;

public class RemoteGame extends UnicastRemoteObject implements RemoteGameInterface {
	protected RemoteGame() throws RemoteException {
		super();
	}

	/* call local implementation */

	public void placePlayerBall(Position position) {
		Controller.placePlayerBall(position);
	}

	public void mountPlayerBall(Position position) throws RemoteException {
		Controller.mountPlayerBall(Model.board.ballAt(position));
	}

	public void removePlayerBall(Position position, boolean lastRemoved) throws RemoteException {
		Controller.removePlayerBall(Model.board.ballAt(position), lastRemoved);
	}

	public void scanForRemote(String host) throws RemoteException {
		Pylos.network.scanForRemote(host);
	}
}
