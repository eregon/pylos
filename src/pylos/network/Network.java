package pylos.network;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import pylos.Config;
import pylos.model.Model;
import pylos.model.Player;

public class Network {
	static final String rmiScheme = "rmi://";
	static final String localhost = "localhost";
	static final String remoteHost = "localhost";
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
		System.out.println(getIP());
		launchServer();
		scanForRemote(remoteHost); // TODO: ask the host
	}

	private void launchServer() {
		try {
			FutureTask<?> startRegistry = new FutureTask<Void>(new Callable<Void>() {
				public Void call() throws Exception {
					System.out.println("Searching the rmiregistry ...");
					LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
					System.out.println("Getting objects list ...");
					Naming.list(rmiScheme + localhost);
					return null;
				}
			});
			new Thread(startRegistry).start();
			startRegistry.get(Config.CREATE_RMI_REGISTRY_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			try {
				FutureTask<?> startRegistry = new FutureTask<Void>(new Callable<Void>() {
					public Void call() throws Exception {
						System.out.println("Creating the rmiregistry ...");
						LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
						return null;
					}
				});
				new Thread(startRegistry).start();
				startRegistry.get(Config.CREATE_RMI_REGISTRY_TIMEOUT, TimeUnit.MILLISECONDS);
			} catch (Exception e2) {
				System.err.println("Could not create the rmiregistry.");
			}
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

						if (Model.player1.isUndefined() && Model.player2.isUndefined()) {
							byte player = remoteGame.askForPlayer();
							if (player != 0) {
								Player me = Player.fromByte(player);
								me.isLocal();
								me.other().isRemote();
							}
						}

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
			} catch (Exception e) {
			}

			try {
				UnicastRemoteObject.unexportObject(localGame, true);
			} catch (NoSuchObjectException e) {
			}

		}
	}
}
