package fvsl.memory.server.db;

import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.util.MockFactory;

public class ServerData {

	public ServerData(){
		lobbies = new ArrayList<Lobby>();
		lobbies = MockFactory.getMockLobbiesList();
		lobbies.add(new Lobby("popopop", "HUH", 1, 1, 1, "huh"));
	}
	
	private ArrayList<Lobby> lobbies;

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
	
	public Lobby getLobbyById(String id){
		Lobby lobby = null;
		boolean found = false;
		for (int i = 0; !found && i < getLobbies().size(); i++){
			lobby = getLobbies().get(i);
			found = lobby.getId().equals(id);
		}
		
		return found?lobby:null;
	}
	
}
