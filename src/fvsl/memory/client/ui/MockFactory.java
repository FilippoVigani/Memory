package fvsl.memory.client.ui;

import java.util.ArrayList;

public class MockFactory {
	public static ArrayList<Lobby> getMockLobbiesList(){
		ArrayList<Lobby> list = new ArrayList<Lobby>();
		list.add(new Lobby(1, "MockLobby1", 2, 30, 19, null));
		list.add(new Lobby(2, "MockLobby2", 2, 10, 9, null));
		list.add(new Lobby(3, "MockLobby3", 4, 20, 15, "123"));
		list.add(new Lobby(4, "MockLobby4", 4, 16, 14, "123"));
		list.add(new Lobby(5, "MockLobby5", 3, 14, 13, "123"));
		return list;
	}
}
