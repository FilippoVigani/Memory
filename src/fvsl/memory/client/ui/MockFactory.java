package fvsl.memory.client.ui;

import java.util.ArrayList;

public class MockFactory {
	public static ArrayList<Lobby> getMockLobbiesList(){
		ArrayList<Lobby> list = new ArrayList<Lobby>();
		list.add(new Lobby("MockLobby1", 2, 30, 19));
		list.add(new Lobby("MockLobby2", 2, 10, 9));
		list.add(new Lobby("MockLobby3", 4, 20, 15));
		list.add(new Lobby("MockLobby4", 4, 16, 14));
		list.add(new Lobby("MockLobby5", 3, 14, 13));
		return list;
	}
}
