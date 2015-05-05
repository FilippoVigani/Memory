package fvsl.memory.client.ui;

import java.awt.List;
import java.util.ArrayList;

//In realtà sarebbe MainPageModel, (vedi MVC)
public class MainPageViewModel {
	private String playerName;
	private ArrayList<Lobby> lobbies;
	private Lobby selectedLobby;
	private String password;
	
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
