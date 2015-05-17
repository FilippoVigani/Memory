package fvsl.memory.client.pages.lobby;

import java.util.ArrayList;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.entities.Request.StatusChangeResult;
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

	public StatusChangeResult setStatusReady(Lobby lobby) {
		return Global.getServerManager().requestSetStatusReady(Global.player, lobby);
	}
	
	
	public void backToMainPage(Lobby lobby){
		
		leave(lobby);
		fireGoToMainPageEvent();
	}
	
	public void leave(Lobby lobby){
		try {
			Global.getServerManager().requestLeaveLobby(Global.player, lobby);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void GoToGamePage(Lobby lobby){
		fireGoToGamePageEvent();
	}
}
