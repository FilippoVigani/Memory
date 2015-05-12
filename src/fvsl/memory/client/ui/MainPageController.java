package fvsl.memory.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fvsl.memory.client.ui.Request.LobbyJoiningResult;

public class MainPageController extends PageListeners {
	private static final Logger log = Logger.getLogger( MainPageController.class.getName() );
	
	public ArrayList<Lobby> getLobbiesFromServer(String playerName){
		return Global.getServerManager().requestLobbies(playerName);
	}
	
	public LobbyJoiningResult requestLobbyJoining(String playerName, Lobby selectedLobby, String password) {
		log.log(Level.INFO, playerName + " tries to join lobby " + selectedLobby.getName(), selectedLobby);
		
		return Global.getServerManager().requestJoinLobby(playerName, selectedLobby, password);
	}
	
	protected void loadCreateLobbyPage() {
		fireGoToCreateLobbyEvent();
	}
	
	protected void loadLobbyPage(Lobby lobby) {
		fireGoToLobbyEvent(lobby);
	}
	

}
