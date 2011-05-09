package pylos.network;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class Network {
	static final String rmiScheme = "rmi://";
	static final String localhost = "localhost";
	static final String remoteObjectBaseName = "/RemotePylos";
	static String remoteObjectName;

	RemoteGameInterface localGame;
	public List<RemoteGameInterface> remoteGames = new LinkedList<RemoteGameInterface>();
	List<String> remoteObjectsNames = new LinkedList<String>();

	public boolean isPaired() {
		return !remoteGames.isEmpty();
	}

	public void createConnections() {

		String uID = ManagementFactory.getRuntimeMXBean().getName();
		remoteObjectName = remoteObjectBaseName + uID;

		launchServer();
		scanForRemote(localhost); // TODO: ask the host
	}

	private void launchServer() {
		try {
			LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			Naming.list(rmiScheme + localhost);
		} catch (RemoteException e1) {
			try {
				LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			} catch (RemoteException e) {
				System.err.println("Could not launch rmiregistry");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			RemoteGameInterface localGame = new RemoteGame();
			Naming.rebind(remoteObjectName, localGame);
			this.localGame = localGame;
			System.out.println("Server is ready: " + localhost + remoteObjectName);
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}

	public void scanForRemote(String host) {
		String base = rmiScheme + host;
		try {
			for (String remoteName : Naming.list(host)) {
				String path = (new URI(remoteName)).getPath();
				try {
					if (!path.equals(remoteObjectName) && !remoteObjectsNames.contains(path)) {
						RemoteGameInterface remoteGame = (RemoteGameInterface) Naming.lookup(base + path);
						remoteGames.add(remoteGame);
						remoteObjectsNames.add(path);
						System.out.println("Paired with " + base + path);

						remoteGame.scanForRemote(getIP());
					}
				} catch (Exception e) {
					e.printStackTrace();
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

	public void stop() {
		if (localGame != null) {
			try {
				Naming.unbind(remoteObjectName);
				UnicastRemoteObject.unexportObject(localGame, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
