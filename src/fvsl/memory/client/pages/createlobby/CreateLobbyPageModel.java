/*
 * 
 */
package fvsl.memory.client.pages.createlobby;

import fvsl.memory.common.entities.Lobby;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateLobbyPageModel.
 */
public class CreateLobbyPageModel {
	
	/** The lobby. */
	private Lobby lobby;
	
	/** The password. */
	private String password;
	
	/** The possible players. */
	private Integer[] possiblePlayers = { 2, 3, 4 };
	
	/** The possible pairs. */
	private Integer[] possiblePairs = { 2, 10, 15, 20 };
	
	/** The possible timers. */
	private Integer[] possibleTimers = { 5, 10, 15 };

	/**
	 * Gets the lobby.
	 *
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * Sets the lobby.
	 *
	 * @param lobby the new lobby
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
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
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the possible players.
	 *
	 * @return the possible players
	 */
	public Integer[] getPossiblePlayers() {
		return possiblePlayers;
	}

	/**
	 * Sets the possible players.
	 *
	 * @param possiblePlayers the new possible players
	 */
	public void setPossiblePlayers(Integer[] possiblePlayers) {
		this.possiblePlayers = possiblePlayers;
	}

	/**
	 * Gets the possible pairs.
	 *
	 * @return the possible pairs
	 */
	public Integer[] getPossiblePairs() {
		return possiblePairs;
	}

	/**
	 * Sets the possible pairs.
	 *
	 * @param possiblePairs the new possible pairs
	 */
	public void setPossiblePairs(Integer[] possiblePairs) {
		this.possiblePairs = possiblePairs;
	}

	/**
	 * Gets the possible timers.
	 *
	 * @return the possible timers
	 */
	public Integer[] getPossibleTimers() {
		return possibleTimers;
	}

	/**
	 * Sets the possible timers.
	 *
	 * @param possibleTimers the new possible timers
	 */
	public void setPossibleTimers(Integer[] possibleTimers) {
		this.possibleTimers = possibleTimers;
	}
}
