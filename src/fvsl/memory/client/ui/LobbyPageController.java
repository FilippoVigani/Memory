package fvsl.memory.client.ui;

import java.util.ArrayList;

public class LobbyPageController extends PageListeners{
	
	public ArrayList<Player> getPlayersOfLobbyFromServer(Lobby lobby){
		return Global.getServerManager().requestConnectedPlayers(Global.playerName, lobby);
	}
	
}
