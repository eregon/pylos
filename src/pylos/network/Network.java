package pylos.network;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.LinkedList;
import java.util.List;

import pylos.model.Position;

public class Network {
	static final String localhost = "rmi://localhost";
	static final String remoteObjectBaseName = "/RemotePylos";
	static String remoteObjectName;
	static final int port = 1099; // default rmiregistry port

	public void createConnections() {

		String uID = ManagementFactory.getRuntimeMXBean().getName();
		remoteObjectName = remoteObjectBaseName + uID;

		launchServer();
		launchClient();
	}

	private void launchServer() {
		try {
			LocateRegistry.getRegistry(port);
			Naming.list(localhost);
		} catch (RemoteException e1) {
			try {
				LocateRegistry.createRegistry(port);
			} catch (RemoteException e) {
				System.err.println("Could not launch rmiregistry");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			RemoteGame remoteGame = new RemoteGame();
			Naming.rebind(remoteObjectName, remoteGame);
			System.out.println("Server is ready : " + localhost + remoteObjectName);
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}

	private void launchClient() {
		String host = "localhost";
		String uri = "rmi://" + host;

		RemoteGameInterface remoteGame = null;
		Remote remote = null;
		List<String> otherRemoteNames = new LinkedList<String>();

		try {
			String[] remoteNames = Naming.list(host);

			for (String remoteName : remoteNames) {
				URI remoteURI = new URI(remoteName);
				String path = remoteURI.getPath();
				if (!path.equals(remoteObjectName)) {
					otherRemoteNames.add(path);
				}
			}

			for (String remoteName : otherRemoteNames) {
				System.out.println(uri + remoteName);

				remote = Naming.lookup(uri + remoteName);
				remoteGame = (RemoteGameInterface) remote;

				System.out.println(remoteGame);
				remoteGame.placePlayerBall(Position.at(0, 0, 0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
