package fvsl.memory.client.pages.main;
/**
 * @author Stefano Leggio
 *
 */
import java.util.Vector;

import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;

// TODO: Auto-generated Javadoc
//In realtà sarebbe MainPageModel, (vedi MVC)
/**
 * The Class MainPageModel.
 */
public class MainPageModel {
	private Player player;
	private Vector<Lobby> lobbies;
	private Lobby selectedLobby;
	private String password;

	/**
	 * Gets the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player.
	 * 
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the lobbies.
	 * 
	 * @return the lobbies
	 */
	public Vector<Lobby> getLobbies() {
		return lobbies;
	}

	/**
	 * Sets the lobbies.
	 * 
	 * @param lobbies
	 *            the new lobbies
	 */
	public void setLobbies(Vector<Lobby> lobbies) {
		this.lobbies = lobbies;
	}

	/**
	 * Gets the selected lobby.
	 * 
	 * @return the selected lobby
	 */
	public Lobby getSelectedLobby() {
		return selectedLobby;
	}

	/**
	 * Sets the selected lobby.
	 * 
	 * @param selectedLobby
	 *            the new selected lobby
	 */
	public void setSelectedLobby(Lobby selectedLobby) {
		this.selectedLobby = selectedLobby;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
