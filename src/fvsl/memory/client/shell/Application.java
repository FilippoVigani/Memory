package fvsl.memory.client.shell;

import fvsl.memory.client.sockets.GUIUpdaterRunnable;
import fvsl.memory.client.sockets.ServerManager;
import fvsl.memory.common.entities.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public final class Application {
	
	/** The Constant WINDOW_WIDTH. */
	public static final int WINDOW_WIDTH = 1100;
	
	/** The Constant WINDOW_HEIGHT. */
	public static final int WINDOW_HEIGHT = 600;

	private static ServerManager serverManager;
	
	/** The player. */
	public static Player player;

	protected volatile static GUIUpdaterRunnable updater;
	protected volatile static Thread updaterThread;

	/**
	 * Gets the server manager.
	 *
	 * @return the server manager
	 */
	public static ServerManager getServerManager() {
		if (serverManager == null) {
			serverManager = new ServerManager();
		}
		return serverManager;
	}

	/**
	 * Gets the updater.
	 *
	 * @return the updater
	 */
	public static GUIUpdaterRunnable getUpdater() {
		return updater;
	}

	/**
	 * Sets the updater.
	 *
	 * @param updater the new updater
	 */
	public static void setUpdater(GUIUpdaterRunnable updater) {
		Application.updater = updater;
	}

	/**
	 * Gets the updater thread.
	 *
	 * @return the updater thread
	 */
	public static Thread getUpdaterThread() {
		return updaterThread;
	}

	/**
	 * Sets the updater thread.
	 *
	 * @param updaterThread the new updater thread
	 */
	public static void setUpdaterThread(Thread updaterThread) {
		Application.updaterThread = updaterThread;
	}
}
