package fvsl.memory.client.ui;

public class LobbyPageModel {
	
	private Lobby lobby;
	
	private boolean ready1;
	private boolean ready2;
	private boolean ready3;
	
	public boolean getReady1() {
		return ready1;
	}
	public void setReady1(boolean ready1) {
		this.ready1 = ready1;
	}
	public boolean getReady2() {
		return ready2;
	}
	public void setReady2(boolean ready2) {
		this.ready2 = ready2;
	}
	public boolean getReady3() {
		return ready3;
	}
	public void setReady3(boolean ready3) {
		this.ready3 = ready3;
	}
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
	
	
}