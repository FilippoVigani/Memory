package fvsl.memory.client.pages.createlobby;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Request.LobbyCreationResult;
import fvsl.memory.client.entities.Request.LobbyJoiningResult;
import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Global;

public class CreateLobbyPageController extends PageListeners {

	public LobbyCreationResult attemptToCreateLobby(Lobby lobby, String password) {
		LobbyCreationResult result = LobbyCreationResult.Failed;
		try {
			result = Global.getServerManager().requestCreateLobby(Global.player, lobby, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(result.toString() + ", Lobby creata con id: " + lobby.getId());
		
		if (result == LobbyCreationResult.Accepted){
			LobbyJoiningResult joiningResult = LobbyJoiningResult.Failed;
			try {
				joiningResult = Global.getServerManager().requestJoinLobby(Global.player, lobby, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!(joiningResult == LobbyJoiningResult.Accepted)){
				//Delete lobby
				result = LobbyCreationResult.Failed;
			} else {
				fireGoToLobbyEvent(lobby);
			}
		}
		return result;
	}
}
