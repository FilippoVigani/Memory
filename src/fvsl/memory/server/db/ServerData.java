package fvsl.memory.server.db;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.util.MockFactory;
import fvsl.memory.server.sockets.ClientRunnable;
import fvsl.memory.server.sockets.ClientUpdaterRunnable;

public class ServerData {

	public ServerData(){
		lobbies = new ArrayList<Lobby>();
		clientUpdaters = new ArrayList<ClientUpdaterRunnable>();
		lobbies = MockFactory.getMockLobbiesList();
		lobbies.add(new Lobby("popopop", "HUH", 1, 1, 1, "huh"));
	}
	
	private ArrayList<Lobby> lobbies;
	private ArrayList<ClientUpdaterRunnable> clientUpdaters;
	
	/**
	 * @return the lobbies
	 */
	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}

	/**
	 * @param lobbies the lobbies to set
	 */
	public synchronized void setLobbies(ArrayList<Lobby> lobbies) {
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
	public ArrayList<ClientUpdaterRunnable> getClientUpdaters() {
		return clientUpdaters;
	}

	/**
	 * @param clientUpdaters the clientUpdaters to set
	 */
	public void setClientUpdaters(ArrayList<ClientUpdaterRunnable> clientUpdaters) {
		this.clientUpdaters = clientUpdaters;
	}

	
}
