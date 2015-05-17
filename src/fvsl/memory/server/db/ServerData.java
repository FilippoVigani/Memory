package fvsl.memory.server.db;

import java.util.Vector;

import fvsl.memory.common.entities.Lobby;
import fvsl.memory.server.sockets.ClientUpdaterRunnable;

public class ServerData {

	public ServerData(){
		lobbies = new Vector<Lobby>();
		lobbies.add(new Lobby("dsfdsafereres", "HUH", 2, 1, 1, ""));
		lobbies.add(new Lobby("asfsadfadsfas", "HUH2", 3, 1, 1, ""));
		clientUpdaters = new Vector<ClientUpdaterRunnable>();
	}
	
	private volatile Vector<Lobby> lobbies;
	private volatile Vector<ClientUpdaterRunnable> clientUpdaters;
	
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
		Lobby lobby = null;
		boolean found = false;
		for (int i = 0; !found && i < getLobbies().size(); i++){
			lobby = getLobbies().get(i);
			found = lobby.getId().equals(id);
		}
		
		return found?lobby:null;
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

	
}
