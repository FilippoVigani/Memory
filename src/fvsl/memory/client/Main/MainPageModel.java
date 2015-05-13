package fvsl.memory.client.Main;

import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;

//In realtà sarebbe MainPageModel, (vedi MVC)
public class MainPageModel {
	private Player player;
	private ArrayList<Lobby> lobbies;
	private Lobby selectedLobby;
	private String password;
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * @return the lobbies
	 */
	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}
	/**
	 * @param lobbies the lobbies to set
	 */
	public void setLobbies(ArrayList<Lobby> lobbies) {
		this.lobbies = lobbies;
	}
	/**
	 * @return the selectedLobby
	 */
	public Lobby getSelectedLobby() {
		return selectedLobby;
	}
	/**
	 * @param selectedLobby the selectedLobby to set
	 */
	public void setSelectedLobby(Lobby selectedLobby) {
		this.selectedLobby = selectedLobby;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
