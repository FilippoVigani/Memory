package fvsl.memory.client.ui;

import fvsl.memory.client.ui.Request.LobbyCreationResult;
import fvsl.memory.client.ui.Request.LobbyJoiningResult;

public class CreateLobbyPageController extends PageListeners {

	public LobbyCreationResult attemptToCreateLobby(Lobby lobby, String password) {
		LobbyCreationResult result = Global.getServerManager().requestCreateLobby(Global.playerName, lobby, password);

		System.out.println(result.toString() + ", Lobby creata con id: " + lobby.getId());
		
		if (result == LobbyCreationResult.Accepted){
			LobbyJoiningResult joiningResult = Global.getServerManager().requestJoinLobby(Global.playerName, lobby, password);
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
