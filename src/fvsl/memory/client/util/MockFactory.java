package fvsl.memory.client.util;

import java.util.ArrayList;

import fvsl.memory.common.entities.Lobby;

public class MockFactory {
	public static ArrayList<Lobby> getMockLobbiesList() {
		ArrayList<Lobby> list = new ArrayList<Lobby>();
		list.add(new Lobby("sadasd", "MockLobby1", 2, 30, 19, null));
		list.add(new Lobby("ghdf", "MockLobby2", 2, 10, 9, null));
		list.add(new Lobby("ghad", "MockLobby3", 4, 20, 15, "123"));
		list.add(new Lobby("dsfha", "MockLobby4", 4, 16, 14, "123"));
		list.add(new Lobby("dshhhs", "MockLobby5", 3, 14, 13, "123"));
		return list;
	}
}
