package fvsl.memory.client.pages.lobby;

import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Global;

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
