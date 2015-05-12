package fvsl.memory.client.ui;

public class CreateLobbyPageModel {
	private Lobby lobby;
	private String password;
	private Integer[] possiblePlayers = {2,3,4};
	private Integer[] possiblePairs = {10,16,20};
	private Integer[] possibleTimers = {5,10,15};
	
	/**
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}
	/**
	 * @param lobby the lobby to set
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
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
	/**
	 * @return the possiblePlayers
	 */
	public Integer[] getPossiblePlayers() {
		return possiblePlayers;
	}
	/**
	 * @param possiblePlayers the possiblePlayers to set
	 */
	public void setPossiblePlayers(Integer[] possiblePlayers) {
		this.possiblePlayers = possiblePlayers;
	}
	/**
	 * @return the possiblePairs
	 */
	public Integer[] getPossiblePairs() {
		return possiblePairs;
	}
	/**
	 * @param possiblePairs the possiblePairs to set
	 */
	public void setPossiblePairs(Integer[] possiblePairs) {
		this.possiblePairs = possiblePairs;
	}
	/**
	 * @return the possibleTimers
	 */
	public Integer[] getPossibleTimers() {
		return possibleTimers;
	}
	/**
	 * @param possibleTimers the possibleTimers to set
	 */
	public void setPossibleTimers(Integer[] possibleTimers) {
		this.possibleTimers = possibleTimers;
	}
}
