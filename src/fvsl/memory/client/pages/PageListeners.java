package fvsl.memory.client.pages;
/**
 * @author Stefano Leggio
 *
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import fvsl.memory.common.entities.Lobby;

// TODO: Auto-generated Javadoc
/**
 * The Class PageListeners.here there are all events.
 * 
 * @author Filippo Vigani
 *
 */
public class PageListeners {
	// Event to go to a different page

	private List<ActionListener> listeners = new ArrayList<ActionListener>();

	/**
	 * Adds the event listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public synchronized void addEventListener(ActionListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the event listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public synchronized void removeEventListener(ActionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * The Class GoToCreateLobbyEvent.
	 */
	public class GoToCreateLobbyEvent extends ActionEvent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6545671459425688856L;

		/**
		 * Instantiates a new go to create lobby event.
		 * 
		 * @param source
		 *            the source
		 */
		public GoToCreateLobbyEvent(Object source) {
			super(source, 0, null);
		}
	}

	/**
	 * The Class GotoMainPageEvent.
	 */
	public class GotoMainPageEvent extends ActionEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = -41013621096958698L;

		/**
		 * Instantiates a new goto main page event.
		 * 
		 * @param source
		 *            the source
		 */
		public GotoMainPageEvent(Object source) {
			super(source, 0, null);
		}
	}

	/**
	 * The Class GoToGamePageEvent.
	 */
	public class GoToGamePageEvent extends ActionEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1753984169035400846L;

		/**
		 * Instantiates a new go to game page event.
		 * 
		 * @param source
		 *            the source
		 */
		public GoToGamePageEvent(Object source) {
			super(source, 0, null);
		}
	}

	/**
	 * The Class GoToLobbyEvent.
	 */
	public class GoToLobbyEvent extends ActionEvent {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3856940450599430419L;

		/**
		 * Instantiates a new go to lobby event.
		 * 
		 * @param source
		 *            the source
		 */
		public GoToLobbyEvent(Object source) {
			super(source, 0, null);
		}
	}

	/**
	 * The Class GoToScoreboardEvent.
	 */
	public class GoToScoreboardEvent extends ActionEvent {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3856940450599430419L;

		/**
		 * Instantiates a new go to scoreboard event.
		 * 
		 * @param source
		 *            the source
		 */
		public GoToScoreboardEvent(Object source) {
			super(source, 0, null);
		}
	}

	// Create lobby
	/**
	 * The listener interface for receiving goToCreateLobbyEvent events. The
	 * class that is interested in processing a goToCreateLobbyEvent event
	 * implements this interface, and the object created with that class is
	 * registered with a component using the component's
	 * <code>addGoToCreateLobbyEventListener<code> method. When
	 * the goToCreateLobbyEvent event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GoToCreateLobbyEventEvent
	 */
	public interface GoToCreateLobbyEventListener extends ActionListener {
	}

	protected synchronized void fireGoToCreateLobbyEvent() {
		GoToCreateLobbyEvent event = new GoToCreateLobbyEvent(this);
		for (ActionListener listener : listeners) {
			if (listener instanceof GoToCreateLobbyEventListener) {
				listener.actionPerformed(event);
			}
		}
	}

	// Join lobby
	/**
	 * The listener interface for receiving goToLobbyEvent events. The class
	 * that is interested in processing a goToLobbyEvent event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addGoToLobbyEventListener<code> method. When
	 * the goToLobbyEvent event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GoToLobbyEventEvent
	 */
	public interface GoToLobbyEventListener extends ActionListener {
	}

	protected synchronized void fireGoToLobbyEvent(Lobby lobby) {
		GoToLobbyEvent event = new GoToLobbyEvent(lobby);
		for (ActionListener listener : listeners) {
			if (listener instanceof GoToLobbyEventListener) {
				listener.actionPerformed(event);
			}
		}
	}

	// Scoreboard
	/**
	 * The listener interface for receiving goToScoreboardEvent events. The
	 * class that is interested in processing a goToScoreboardEvent event
	 * implements this interface, and the object created with that class is
	 * registered with a component using the component's
	 * <code>addGoToScoreboardEventListener<code> method. When
	 * the goToScoreboardEvent event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GoToScoreboardEventEvent
	 */
	public interface GoToScoreboardEventListener extends ActionListener {
	}

	protected synchronized void fireGoToScoreboardEvent(Lobby lobby) {
		GoToScoreboardEvent event = new GoToScoreboardEvent(lobby);
		for (ActionListener listener : listeners) {
			if (listener instanceof GoToScoreboardEventListener) {
				listener.actionPerformed(event);
			}
		}
	}

	// back to mainpage
	/**
	 * The listener interface for receiving goToMainPageEvent events. The class
	 * that is interested in processing a goToMainPageEvent event implements
	 * this interface, and the object created with that class is registered with
	 * a component using the component's
	 * <code>addGoToMainPageEventListener<code> method. When
	 * the goToMainPageEvent event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GoToMainPageEventEvent
	 */
	public interface GoToMainPageEventListener extends ActionListener {
	}

	protected synchronized void fireGoToMainPageEvent() {
		GotoMainPageEvent event = new GotoMainPageEvent(this);
		for (ActionListener listener : listeners) {
			if (listener instanceof GoToMainPageEventListener) {
				listener.actionPerformed(event);
			}
		}
	}

	// Go to game page
	/**
	 * The listener interface for receiving goToGamePageEvent events. The class
	 * that is interested in processing a goToGamePageEvent event implements
	 * this interface, and the object created with that class is registered with
	 * a component using the component's
	 * <code>addGoToGamePageEventListener<code> method. When
	 * the goToGamePageEvent event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see GoToGamePageEventEvent
	 */
	public interface GoToGamePageEventListener extends ActionListener {
	}

	protected synchronized void fireGoToGamePageEvent() {
		GoToGamePageEvent event = new GoToGamePageEvent(this);
		for (ActionListener listener : listeners) {
			if (listener instanceof GoToGamePageEventListener) {
				listener.actionPerformed(event);
			}
		}
	}
}
