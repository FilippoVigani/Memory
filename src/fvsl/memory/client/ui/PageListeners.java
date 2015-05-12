package fvsl.memory.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PageListeners {
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
	
	protected synchronized void fireGoToCreateLobbyEvent() {
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
	 
	protected synchronized void fireGoToLobbyEvent(Lobby lobby) {
		GoToLobbyEvent event = new GoToLobbyEvent(lobby);
	    for (ActionListener listener : listeners) {
	    	if (listener instanceof GoToLobbyEventListener){
	    		listener.actionPerformed(event);
	    	}
		}
	}
}
