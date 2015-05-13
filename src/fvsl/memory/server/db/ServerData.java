package fvsl.memory.server.db;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.util.MockFactory;
import fvsl.memory.server.sockets.ClientRunnable;

public class ServerData {

	public ServerData(){
		lobbies = new ArrayList<Lobby>();
		clientsOutputStreams = new ArrayList<ObjectOutputStream>();
		lobbies = MockFactory.getMockLobbiesList();
		lobbies.add(new Lobby("popopop", "HUH", 1, 1, 1, "huh"));
	}
	
	private ArrayList<Lobby> lobbies;
	private ArrayList<ObjectOutputStream> clientsOutputStreams;
	
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
	 * @return the clientsOutputStreams
	 */
	public ArrayList<ObjectOutputStream> getClientsOutputStreams() {
		return clientsOutputStreams;
	}

	/**
	 * @param clientsOutputStreams the clientsOutputStreams to set
	 */
	public void setClientsOutputStreams(ArrayList<ObjectOutputStream> clientsOutputStreams) {
		this.clientsOutputStreams = clientsOutputStreams;
	}
	
}
