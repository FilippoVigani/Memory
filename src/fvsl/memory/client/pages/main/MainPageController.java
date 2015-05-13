package fvsl.memory.client.pages.main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.entities.Request.LobbyJoiningResult;
import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Global;

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
