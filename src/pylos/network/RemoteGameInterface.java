package pylos.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import pylos.model.Position;

public interface RemoteGameInterface extends Remote {
	// All methods to simulate real clicks

	void placePlayerBall(Position position) throws RemoteException;

	void scanForRemote(String host) throws RemoteException;
}
