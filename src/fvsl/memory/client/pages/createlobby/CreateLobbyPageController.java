package fvsl.memory.client.pages.createlobby;

import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Request.LobbyCreationResult;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;

public class CreateLobbyPageController extends PageListeners {

	public LobbyCreationResult attemptToCreateLobby(Lobby lobby, String password) {
		LobbyCreationResult result = LobbyCreationResult.Failed;
		try {
			result = Application.getServerManager().requestCreateLobby(Application.player, lobby, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(result.toString() + ", Lobby creata con id: " + lobby.getId());

		if (result == LobbyCreationResult.Accepted){
			LobbyJoiningResult joiningResult = LobbyJoiningResult.Failed;
			try {
				joiningResult = Application.getServerManager().requestJoinLobby(Application.player, lobby, password);
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

	public void backToMainPage(){
		fireGoToMainPageEvent();
	}

}
