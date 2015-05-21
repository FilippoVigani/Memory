package fvsl.memory.server.db;

import java.util.Vector;

import fvsl.memory.common.entities.Lobby;
import fvsl.memory.server.sockets.ClientUpdaterRunnable;

// TODO: Auto-generated Javadoc
/**
 * Holds all the data of the server, and provides it in a synchronized manner
 */
/**
 * @author Filippo Vigani
 *
 */
public class ServerData {

	/**
	 * Instantiates a new server data.
	 */
	public ServerData() {
		lobbies = new Vector<Lobby>();
		games = new Vector<GameState>();
		clientUpdaters = new Vector<ClientUpdaterRunnable>();
	}

	private volatile Vector<Lobby> lobbies;
	private volatile Vector<ClientUpdaterRunnable> clientUpdaters;
	private volatile Vector<GameState> games;

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
	public synchronized void setLobbies(Vector<Lobby> lobbies) {
		this.lobbies = lobbies;
	}

	/**
	 * Gets the lobby by id.
	 * 
	 * @param id
	 *            the id
	 * @return the lobby by id
	 */
	public Lobby getLobbyById(String id) {
		for (Lobby lobby : getLobbies()) {
			if (lobby.getId().equals(id)) {
				return lobby;
			}
		}
		return null;
	}

	/**
	 * Gets the game by id.
	 * 
	 * @param id
	 *            the id
	 * @return the game by id
	 */
	public GameState getGameById(String id) {
		for (GameState game : getGames()) {
			if (game.getId().equals(id)) {
				return game;
			}
		}
		return null;
	}

	/**
	 * Gets the client updaters.
	 * 
	 * @return the client updaters
	 */
	public Vector<ClientUpdaterRunnable> getClientUpdaters() {
		return clientUpdaters;
	}

	/**
	 * Sets the client updaters.
	 * 
	 * @param clientUpdaters
	 *            the new client updaters
	 */
	public synchronized void setClientUpdaters(Vector<ClientUpdaterRunnable> clientUpdaters) {
		this.clientUpdaters = clientUpdaters;
	}

	/**
	 * Gets the games.
	 * 
	 * @return the games
	 */
	public Vector<GameState> getGames() {
		return games;
	}

	/**
	 * Sets the games.
	 * 
	 * @param games
	 *            the new games
	 */
	public void setGames(Vector<GameState> games) {
		this.games = games;
	}

}
