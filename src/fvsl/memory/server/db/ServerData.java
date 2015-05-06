package fvsl.memory.server.db;

import java.util.ArrayList;

import fvsl.memory.client.ui.Global;
import fvsl.memory.client.ui.Lobby;
import fvsl.memory.client.ui.MockFactory;

public class ServerData {

	public ServerData(){
		lobbies = new ArrayList<Lobby>();
		lobbies = MockFactory.getMockLobbiesList();
		lobbies.add(new Lobby("HUH", 1, 1, 1));
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
	
}
