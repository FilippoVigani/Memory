package fvsl.memory.client.ui;

import fvsl.memory.client.ui.Request.LobbyCreationResult;
import fvsl.memory.client.ui.Request.LobbyJoiningResult;

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
