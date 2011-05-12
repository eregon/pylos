package pylos.network;

import java.lang.management.ManagementFactory;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import pylos.Config;
import pylos.model.Model;
import pylos.model.Player;

public class Network {
	static final String localhost = "130.104.102.155";
	static final String remoteHost = "130.104.102.112";
	static final String remoteObjectBaseName = "/RemotePylos";
	static String remoteObjectName;

	RemoteGameInterface localGame;
	public List<RemoteGameInterface> remoteGames = new LinkedList<RemoteGameInterface>();
	List<String> remoteObjectsNames = new LinkedList<String>();

	public boolean isPaired() {
		return !remoteGames.isEmpty();
	}

	private String rmiURL(String host) {
		return "//" + host + ":" + Config.RMI_PORT;
	}

	public void createConnections() {
		setRMIhostname(localhost);

		String uID = ManagementFactory.getRuntimeMXBean().getName();
		remoteObjectName = remoteObjectBaseName + uID; // + getIP() ?
		System.out.println(localhost);
		launchServer();
		scanForRemote(remoteHost); // TODO: ask the host
		scanForRemote(localhost);
	}

	/**
	 * equivalent of command line arg -Djava.rmi.server.hostname=myhostname
	 */
	private void setRMIhostname(String hostname) {
		Properties systemProperties = new Properties(System.getProperties());
		systemProperties.put("java.rmi.server.hostname", hostname);
		System.setProperties(systemProperties);
	}

	private void launchServer() {
		try {
			FutureTask<?> startRegistry = new FutureTask<Void>(new Callable<Void>() {
				public Void call() throws Exception {
					System.out.println("Searching or creating the rmiregistry ...");
					LocateRegistry.createRegistry(Config.RMI_PORT);
					System.out.println("Created the registry");
					return null;
				}
			});
			new Thread(startRegistry).start();
			startRegistry.get(Config.CREATE_RMI_REGISTRY_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			// Already Running
		}

		try {
			String url = rmiURL(localhost) + remoteObjectName;
			RemoteGameInterface localGame = new RemoteGame();
			Naming.rebind(url, localGame);
			this.localGame = localGame;
			System.out.println("Server is ready: " + url);
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}

	public void scanForRemote(String host) {
		try {
			String base = rmiURL(host);
			String[] list = Naming.list(base);

			System.out.println(base);
			System.out.println(list.length);
			for (String remoteName : list) {
				System.out.println(remoteName);
				String path = (new URI(remoteName)).getPath();
				try {
					if (!path.equals(remoteObjectName) && !remoteObjectsNames.contains(path)) {
						String url = base + path;
						RemoteGameInterface remoteGame = (RemoteGameInterface) Naming.lookup(url);
						remoteGames.add(remoteGame);
						remoteObjectsNames.add(path);
						System.out.println("Paired with " + url);

						if (Model.player1.isUndefined() && Model.player2.isUndefined()) {
							byte player = remoteGame.askForPlayer();
							if (player != 0) {
								Player me = Player.fromByte(player);
								me.isLocal();
								me.other().isRemote();
							}
						}

						remoteGame.scanForRemote(localhost); // TODO: be able to specify own's ip
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
