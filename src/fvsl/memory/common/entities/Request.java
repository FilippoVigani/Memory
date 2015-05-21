package fvsl.memory.common.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Request.
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
	 * @param player the player
	 * @param requestAction the request action
	 * @param requestType the request type
	 * @param content the content
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
	 * @param requestAction the request action
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
		
		/** The Get lobbies. */
		GetLobbies, 
 /** The Join lobby. */
 JoinLobby, 
 /** The Create lobby. */
 CreateLobby, 
 /** The Delete lobby. */
 DeleteLobby, 
 /** The Get possible players numbers. */
 GetPossiblePlayersNumbers, 
 /** The Get possible pairs numbers. */
 GetPossiblePairsNumbers, 
 /** The Get possible timer numbers. */
 GetPossibleTimerNumbers, 
 /** The Get connected players. */
 GetConnectedPlayers, 
 /** The Set player status ready. */
 SetPlayerStatusReady, 
 /** The Leave lobby. */
 LeaveLobby,

		/** The Update lobby list. */
		UpdateLobbyList, /** The Update players list. */
 UpdatePlayersList, /** The Deleted lobby. */
 DeletedLobby,

		/** The Setup game. */
		SetupGame, /** The Start game. */
 StartGame, /** The Get cards ids. */
 GetCardsIds, /** The Get turn player. */
 GetTurnPlayer, /** The Game request. */
 GameRequest, /** The End game. */
 EndGame
	}

	/**
	 * The Enum RequestAction.
	 */
	public enum RequestAction {
		
		/** The Ask. */
		Ask, 
 /** The Reply. */
 Reply
	}

	/**
	 * The Enum LobbyJoiningResult.
	 */
	public enum LobbyJoiningResult {
		
		/** The Failed. */
		Failed, 
 /** The Accepted. */
 Accepted, 
 /** The Wrong password. */
 WrongPassword, 
 /** The Full lobby. */
 FullLobby, 
 /** The Unaccepted username. */
 UnacceptedUsername, 
 /** The Not found. */
 NotFound
	}

	/**
	 * The Enum LobbyLeavingResult.
	 */
	public enum LobbyLeavingResult {
		
		/** The Accepted. */
		Accepted, 
 /** The Failed. */
 Failed
	}

	/**
	 * The Enum LobbyCreationResult.
	 */
	public enum LobbyCreationResult {
		
		/** The Failed. */
		Failed, 
 /** The Accepted. */
 Accepted
	}

	/**
	 * The Enum StatusChangeResult.
	 */
	public enum StatusChangeResult {
		
		/** The Accepted. */
		Accepted, 
 /** The Failed. */
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
	 * @param requestAction the new request action
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
	 * @param <T> the generic type
	 * @return the casted content
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCastedContent() {
		return (T) content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	/**
	 * Sets the request type.
	 *
	 * @param type the new request type
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
	 * @param player the new player name
	 */
	public void setPlayerName(Player player) {
		this.player = player;
	}
}
