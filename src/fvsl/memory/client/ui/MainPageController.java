package fvsl.memory.client.ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import fvsl.memory.client.ui.Request.LobbyJoiningResult;

public class MainPageController extends PageListeners {
	private static final Logger log = Logger.getLogger( MainPageController.class.getName() );
	
	public ArrayList<Lobby> getLobbiesFromServer(Player player){
		try {
			return Global.getServerManager().requestLobbies(player);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Lobby>();
		}
	}
	
	public LobbyJoiningResult requestLobbyJoining(Player player, Lobby selectedLobby, String password) {
		log.log(Level.INFO, player.getName() + " tries to join lobby " + selectedLobby.getName(), selectedLobby);
		try {
			return Global.getServerManager().requestJoinLobby(player, selectedLobby, password);
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
