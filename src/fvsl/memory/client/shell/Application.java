package fvsl.memory.client.shell;

import fvsl.memory.client.sockets.GUIUpdaterRunnable;
import fvsl.memory.client.sockets.ServerManager;
import fvsl.memory.common.entities.Player;

public final class Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private static ServerManager serverManager;
	public static Player player;
	
	protected volatile static GUIUpdaterRunnable updater;
	protected volatile static Thread updaterThread;
	
	/**
	 * @return the serverManager
	 */
	public static ServerManager getServerManager() {
		if (serverManager == null){
			serverManager = new ServerManager();
		}
		return serverManager;
	}

	/**
	 * @return the updater
	 */
	public static GUIUpdaterRunnable getUpdater() {
		return updater;
	}

	/**
	 * @param updater the updater to set
	 */
	public static void setUpdater(GUIUpdaterRunnable updater) {
		Application.updater = updater;
	}

	/**
	 * @return the updaterThread
	 */
	public static Thread getUpdaterThread() {
		return updaterThread;
	}

	/**
	 * @param updaterThread the updaterThread to set
	 */
	public static void setUpdaterThread(Thread updaterThread) {
		Application.updaterThread = updaterThread;
	}
}
