package fvsl.memory.server.db;

import java.util.Vector;

import fvsl.memory.common.entities.Lobby;
import fvsl.memory.server.sockets.ClientUpdaterRunnable;

public class ServerData {

	public ServerData(){
		lobbies = new Vector<Lobby>();
		games = new Vector<Game>();
		clientUpdaters = new Vector<ClientUpdaterRunnable>();
	}
	
	private volatile Vector<Lobby> lobbies;
	private volatile Vector<ClientUpdaterRunnable> clientUpdaters;
	private volatile Vector<Game> games;
	
	/**
	 * @return the lobbies
	 */
	public Vector<Lobby> getLobbies() {
		return lobbies;
	}

	/**
	 * @param lobbies the lobbies to set
	 */
	public synchronized void setLobbies(Vector<Lobby> lobbies) {
		this.lobbies = lobbies;
	}
	
	public Lobby getLobbyById(String id){
		for (Lobby lobby : getLobbies()){
			if (lobby.getId().equals(id)){
				return lobby;
			}
		}
		return null;
	}
	
	public Game getGameById(String id){
		for (Game game : getGames()){
			if (game.getId().equals(id)){
				return game;
			}
		}
		return null;
	}

	/**
	 * @return the clientUpdaters
	 */
	public Vector<ClientUpdaterRunnable> getClientUpdaters() {
		return clientUpdaters;
	}

	/**
	 * @param clientUpdaters the clientUpdaters to set
	 */
	public synchronized void setClientUpdaters(Vector<ClientUpdaterRunnable> clientUpdaters) {
		this.clientUpdaters = clientUpdaters;
	}

	/**
	 * @return the games
	 */
	public Vector<Game> getGames() {
		return games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(Vector<Game> games) {
		this.games = games;
	}

	
}
