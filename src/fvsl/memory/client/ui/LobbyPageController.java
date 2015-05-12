package fvsl.memory.client.ui;

import java.util.ArrayList;

public class LobbyPageController extends PageListeners{
	
	public ArrayList<Player> getPlayersOfLobbyFromServer(Lobby lobby){
		try {
			return Global.getServerManager().requestConnectedPlayers(Global.player, lobby);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
