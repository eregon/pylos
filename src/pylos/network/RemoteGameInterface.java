package pylos.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import pylos.model.Position;

public interface RemoteGameInterface extends Remote {
	// Methods to simulate real clicks
	void placePlayerBall(Position position) throws RemoteException;

	void mountPlayerBall(Position position) throws RemoteException;

	void removePlayerBall(Position position, boolean lastRemoved) throws RemoteException;

	// Network
	void scanForRemote(String host) throws RemoteException;
}
