package fvsl.memory.common.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Request representing a general two-way request, whose content can be various
 */
/**
 * @author Filippo Vigani
 *
 */
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8527944560427371477L;

	private Player player;
	private RequestAction requestAction;
	private RequestType requestType;
	private Object content;

	/**
	 * Instantiates a new request.
	 * 
	 * @param player
	 *            the player
	 * @param requestAction
	 *            the request action
	 * @param requestType
	 *            the request type
	 * @param content
	 *            the content
	 */
	public Request(Player player, RequestAction requestAction, RequestType requestType, Object content) {
		super();
		this.player = player;
		this.requestType = requestType;
		this.requestAction = requestAction;
		this.content = content;
	}

	/**
	 * Instantiates a new request.
	 * 
	 * @param requestAction
	 *            the request action
	 */
	public Request(RequestAction requestAction) {
		this.requestAction = requestAction;
	}

	/**
	 * Instantiates a new request.
	 */
	public Request() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * The Enum RequestType.
	 */
	public enum RequestType {

		/** Gets all the lobbies. */
		GetLobbies,
		/** Join a specific lobby. */
		JoinLobby,
		/** Create a new lobby. */
		CreateLobby,
		/** Delete an existing lobby. */
		DeleteLobby,
		/** Get possible players numbers. */
		GetPossiblePlayersNumbers,
		/** Get possible pairs numbers. */
		GetPossiblePairsNumbers,
		/** Get possible timer numbers. */
		GetPossibleTimerNumbers,
		/** Get the connected players to a lobby. */
		GetConnectedPlayers,
		/** Set player status to ready in a lobby. */
		SetPlayerStatusReady,
		/** Leave lobby. */
		LeaveLobby,

		/** The lobby list has been changed. */
		UpdateLobbyList, 
		/** The players list has been changed. */
		UpdatePlayersList, 
		/** A lobby has been deleted. */
		DeletedLobby,

		/** Setup a game. */
		SetupGame, 
		/** Start a game. */
		StartGame, 
		/** Get all cards ids. */
		GetCardsIds, 
		/** Get the player that can move. */
		GetTurnPlayer, 
		/** States that it contains a game request. */
		GameRequest, 
		/** Game is over. */
		EndGame
	}

	/**
	 * Direction of the request.
	 */
	public enum RequestAction {

		/** The Ask. */
		Ask,
		/** The Reply. */
		Reply
	}

	/**
	 * Result after joining a lobby.
	 */
	public enum LobbyJoiningResult {

		/** Failed. */
		Failed,
		/** Accepted. */
		Accepted,
		/** Wrong password. */
		WrongPassword,
		/** Full lobby. */
		FullLobby,
		/** Unaccepted username. */
		UnacceptedUsername,
		/** Not found. */
		NotFound
	}

	/**
	 * Result after leaving a lobby.
	 */
	public enum LobbyLeavingResult {

		/** Accepted. */
		Accepted,
		/** Failed. */
		Failed
	}

	/**
	 * Result after creating a lobby.
	 */
	public enum LobbyCreationResult {

		/** Failed. */
		Failed,
		/** Accepted. */
		Accepted
	}

	/**
	 * Result after changing the status of a player.
	 */
	public enum StatusChangeResult {

		/** Accepted. */
		Accepted,
		/** Failed. */
		Failed
	}

	/**
	 * Gets the request type.
	 * 
	 * @return the request type
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * Gets the request action.
	 * 
	 * @return the request action
	 */
	public RequestAction getRequestAction() {
		return requestAction;
	}

	/**
	 * Sets the request action.
	 * 
	 * @param requestAction
	 *            the new request action
	 */
	public void setRequestAction(RequestAction requestAction) {
		this.requestAction = requestAction;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * Gets the casted content.
	 * 
	 * @param <T>
	 *            the generic type
	 * @return the casted content
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCastedContent() {
		return (T) content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param content
	 *            the new content
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	/**
	 * Sets the request type.
	 * 
	 * @param type
	 *            the new request type
	 */
	public void setRequestType(RequestType type) {
		requestType = type;
	}

	/**
	 * Gets the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player name.
	 * 
	 * @param player
	 *            the new player name
	 */
	public void setPlayerName(Player player) {
		this.player = player;
	}
}
