package fvsl.memory.client.pages.lobby;

import java.util.Vector;

import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request.LobbyLeavingResult;
import fvsl.memory.common.entities.Request.StatusChangeResult;

public class LobbyPageController extends PageListeners{

	public Vector<Player> getPlayersOfLobbyFromServer(Lobby lobby){
		try {
			return Application.getServerManager().requestConnectedPlayers(Application.player, lobby);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public StatusChangeResult setStatusReady(Lobby lobby) {
		return Application.getServerManager().requestSetStatusReady(Application.player, lobby);
	}


	public void backToMainPage(Lobby lobby){
		if (leaveLobby(lobby) == LobbyLeavingResult.Accepted)
		{
			fireGoToMainPageEvent();
		}
	}

	public LobbyLeavingResult leaveLobby(Lobby lobby){

		try {
			return Application.getServerManager().requestLeaveLobby(Application.player, lobby);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
