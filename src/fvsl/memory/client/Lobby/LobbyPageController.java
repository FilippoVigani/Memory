package fvsl.memory.client.Lobby;

import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.shell.Global;
import fvsl.memory.client.util.PageListeners;

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
