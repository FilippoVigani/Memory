package fvsl.memory.client.pages.lobby;
/**
 * @author Stefano Leggio
 *
 */
import java.util.Vector;

import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request.LobbyLeavingResult;
import fvsl.memory.common.entities.Request.StatusChangeResult;

// TODO: Auto-generated Javadoc
/**
 * The Class LobbyPageController.
 */
public class LobbyPageController extends PageListeners {

	/**
	 * Gets the players of lobby from server.
	 * 
	 * @param lobby
	 *            the lobby
	 * @return the players of lobby from server
	 */
	public Vector<Player> getPlayersOfLobbyFromServer(Lobby lobby) {
		try {
			return Application.getServerManager().requestConnectedPlayers(Application.player, lobby);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sets the status ready.
	 * 
	 * @param lobby
	 *            the lobby
	 * @return the status change result
	 */
	public StatusChangeResult setStatusReady(Lobby lobby) {
		return Application.getServerManager().requestSetStatusReady(Application.player, lobby);
	}

	/**
	 * Back to main page.
	 * 
	 * @param lobby
	 *            the lobby
	 */
	public void backToMainPage(Lobby lobby) {
		if (leaveLobby(lobby) == LobbyLeavingResult.Accepted) {
			fireGoToMainPageEvent();
		}
	}

	/**
	 * Go to game page.
	 * 
	 * @param lobby
	 *            the lobby
	 */
	public void goToGamePage(Lobby lobby) {
		fireGoToGamePageEvent();
	}

	/**
	 * Leave lobby.
	 * 
	 * @param lobby
	 *            the lobby
	 * @return the lobby leaving result
	 */
	public LobbyLeavingResult leaveLobby(Lobby lobby) {

		try {
			return Application.getServerManager().requestLeaveLobby(Application.player, lobby);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
