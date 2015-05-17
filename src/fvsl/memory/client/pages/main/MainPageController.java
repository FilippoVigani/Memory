package fvsl.memory.client.pages.main;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;

public class MainPageController extends PageListeners {
	private static final Logger log = Logger.getLogger( MainPageController.class.getName() );
	
	public Vector<Lobby> getLobbiesFromServer(Player player){
		try {
			return Application.getServerManager().requestLobbies(player);
		} catch (Exception e) {
			e.printStackTrace();
			return new Vector<Lobby>();
		}
	}
	
	public LobbyJoiningResult requestLobbyJoining(Player player, Lobby selectedLobby, String password) {
		log.log(Level.INFO, player.getName() + " tries to join lobby " + selectedLobby.getName(), selectedLobby);
		try {
			return Application.getServerManager().requestJoinLobby(player, selectedLobby, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected void loadCreateLobbyPage() {
		fireGoToCreateLobbyEvent();
	}
	
	protected void loadLobbyPage(Lobby lobby) {
		fireGoToLobbyEvent(lobby);
	}
	

}
