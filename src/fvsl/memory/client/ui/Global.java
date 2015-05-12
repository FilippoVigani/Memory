package fvsl.memory.client.ui;

public final class Global {
	static final int WINDOW_WIDTH = 800;
	static final int WINDOW_HEIGHT = 600;
	
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
