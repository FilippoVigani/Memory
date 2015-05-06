package fvsl.memory.client.ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPageController {
	private static final Logger log = Logger.getLogger( MainPageController.class.getName() );
	
	public static ArrayList<Lobby> getLobbiesFromServer(){
		return MockFactory.getMockLobbiesList();
	}
	
	public LobbyJoiningResult requestLobbyJoining(String playerName, Lobby selectedLobby, String password) {
		log.log(Level.INFO, playerName + " tries to join lobby " + selectedLobby.getName(), selectedLobby);
		
		return LobbyJoiningResult.Accepted;
	}
	
	public enum LobbyJoiningResult{
		Accepted,
		WrongPassword,
		FullLobby,
		UnacceptedUsername		
	}
	
}
