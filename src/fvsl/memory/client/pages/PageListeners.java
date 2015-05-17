package fvsl.memory.client.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import fvsl.memory.client.entities.Lobby;

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
        /**
		 * 
		 */
		private static final long serialVersionUID = 6545671459425688856L;

		public GoToCreateLobbyEvent(Object source) {
            super(source, 0, null);
        }
	}
	public class GotoMainPageEvent extends ActionEvent{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public GotoMainPageEvent(Object source){
			super(source,0,null);
		}
	}
	
	public class GoToLobbyEvent extends ActionEvent {
        /**
		 * 
		 */
		private static final long serialVersionUID = -3856940450599430419L;

		public GoToLobbyEvent(Object source) {
            super(source, 0, null);
        }
	}
	public class GoToGamePageEvent extends ActionEvent{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public GoToGamePageEvent(Object source) {
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
	
	//back to mainpage
	public interface GoToMainPageEventListener extends ActionListener{
	}
	protected synchronized void fireGoToMainPageEvent() {
		GotoMainPageEvent event= new GotoMainPageEvent(this);
		for (ActionListener listener : listeners) {
	    	if (listener instanceof GoToMainPageEventListener){
	    		listener.actionPerformed(event);
	    	}
		}
	}
	//go to gamePage
	public interface GoToGamePageEventListener extends ActionListener{
	}
	protected synchronized void fireGoToGamePageEvent() {
		GoToGamePageEvent event= new GoToGamePageEvent(this);
		for (ActionListener listener : listeners) {
			if (listener instanceof GoToGamePageEventListener){
	    		listener.actionPerformed(event);
	    	}
		}
	}
	
}
