package fvsl.memory.client.shell;

import fvsl.memory.client.entities.Player;
import fvsl.memory.client.sockets.ServerManager;

public final class Global {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	public static final int PORT = 17829;
	public static final int UPDATE_PORT = 17828;
	
	private static ServerManager serverManager;
	public static Player player;
	
	/**
	 * @return the serverManager
	 */
	public static ServerManager getServerManager() {
		if (serverManager == null){
			serverManager = new ServerManager();
		}
		return serverManager;
	}
}
