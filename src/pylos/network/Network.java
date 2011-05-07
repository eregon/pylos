package pylos.network;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import pylos.model.Position;

public class Network {
	static final String rmiScheme = "rmi://";
	static final String localhost = "localhost";
	static final String remoteObjectBaseName = "/RemotePylos";
	static String remoteObjectName;
	static final int port = 1099; // default rmiregistry port

	RemoteGameInterface remoteGame = null;

	public void createConnections() {

		String uID = ManagementFactory.getRuntimeMXBean().getName();
		remoteObjectName = remoteObjectBaseName + uID;

		launchServer();
		scanForRemote(localhost);
	}

	private void launchServer() {
		try {
			LocateRegistry.getRegistry(port);
			Naming.list(rmiScheme + localhost);
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
			System.out.println("Server is ready: " + localhost + remoteObjectName);
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}

	public void scanForRemote(String host) {
		if (remoteGame != null)
			return;

		String base = rmiScheme + host;
		try {
			for (String remoteName : Naming.list(host)) {
				URI remoteURI = new URI(remoteName);
				String path = remoteURI.getPath();
				if (!path.equals(remoteObjectName)) {
					Remote remote = Naming.lookup(base + path);
					remoteGame = (RemoteGameInterface) remote;
					System.out.println("Paired with " + base + path);

					remoteGame.scanForRemote(getIP());

					remoteGame.placePlayerBall(Position.at(0, 0, 0));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Could not get ip: " + e);
			return null;
		}
	}
}
