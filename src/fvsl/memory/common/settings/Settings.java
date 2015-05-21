package fvsl.memory.common.settings;

// TODO: Auto-generated Javadoc
/**
 * General settings to be shared by both the client and the server
 */
/**
 * @author Filippo Vigani
 *
 */
public abstract class Settings {

	/** Port where the main sockets are established. */
	public static final int PORT = 17829;

	/** Port where the sockets to update all the clients are established. */
	public static final int UPDATE_PORT = 17828;
}
