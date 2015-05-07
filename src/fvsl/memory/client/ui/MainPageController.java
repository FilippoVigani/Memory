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

public class MainPageController {
	private static final Logger log = Logger.getLogger( MainPageController.class.getName() );
	
	public ArrayList<Lobby> getLobbiesFromServer(){
		return Global.getServerManager().requestLobbies();
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
	
	protected void loadCreateLobbyPage() {
		fireGoToCreateLobbyEvent();
	}
	
	protected void loadLobbyPage(Lobby lobby) {
		fireGoToLobbyEvent(lobby);
	}
	
	//Event to go to a different page
	
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	public synchronized void addEventListener(ActionListener listener)  {
		  listeners.add(listener);
	}
	public synchronized void removeEventListener(ActionListener listener)   {
		  listeners.remove(listener);
	}
	
	public class GoToCreateLobbyEvent extends ActionEvent {
        public GoToCreateLobbyEvent(Object source) {
            super(source, 0, null);
        }
	}
	
	public class GoToLobbyEvent extends ActionEvent {
        public GoToLobbyEvent(Object source) {
            super(source, 0, null);
        }
	}
	
	//Create lobby
	public interface GoToCreateLobbyEventListener extends ActionListener{
	}
	 
	private synchronized void fireGoToCreateLobbyEvent() {
		GoToCreateLobbyEvent event = new GoToCreateLobbyEvent(this);
	    for (ActionListener listener : listeners) {
	    	if (listener instanceof GoToCreateLobbyEventListener){
	    		listener.actionPerformed(event);
	    	}
		}
	}
	
	//Join lobby
	public interface GoToLobbyEventListener extends ActionListener{
	}
	 
	private synchronized void fireGoToLobbyEvent(Lobby lobby) {
		GoToLobbyEvent event = new GoToLobbyEvent(lobby);
	    for (ActionListener listener : listeners) {
	    	if (listener instanceof GoToLobbyEventListener){
	    		listener.actionPerformed(event);
	    	}
		}
	}
}
