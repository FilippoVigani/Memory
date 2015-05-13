package fvsl.memory.client.entities;

import java.io.Serializable;

public class Request implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8527944560427371477L;
	
	private Player player;
	private RequestAction requestAction;
	private RequestType requestType;
	private Object content;
	
	public Request(Player player, RequestAction requestAction, RequestType requestType, Object content) {
		super();
		this.player = player;
		this.requestType = requestType;
		this.requestAction = requestAction;
		this.content = content;
	}

	public Request(RequestAction requestAction) {
		this.requestAction = requestAction;
	}
	
	public Request() {
		// TODO Auto-generated constructor stub
	}

	public enum RequestType{
		GetLobbies,
		JoinLobby,
		CreateLobby,
		DeleteLobby,
		GetPossiblePlayersNumbers,
		GetPossiblePairsNumbers,
		GetPossibleTimerNumbers, 
		GetConnectedPlayers, 
		SetPlayerStatusReady,
		LeaveLobby
	}
	
	public enum RequestAction{
		Ask,
		Reply
	}
	
	public enum LobbyJoiningResult{
		Failed,
		Accepted,
		WrongPassword,
		FullLobby,
		UnacceptedUsername,
		NotFound
	}
	
	public enum LobbyLeavingResult{
		Accepted,
		Failed
	}
	
	public enum LobbyCreationResult{
		Failed,
		Accepted
	}
	
	public enum StatusChangeResult{
		Accepted,
		Failed
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @return the requestAction
	 */
	public RequestAction getRequestAction() {
		return requestAction;
	}

	/**
	 * @param requestAction the requestAction to set
	 */
	public void setRequestAction(RequestAction requestAction) {
		this.requestAction = requestAction;
	}

	/**
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}
	
	/**
	 * @return the content casted to the type it is assigned to
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCastedContent(){
		return (T)content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	public void setRequestType(RequestType type) {
		requestType = type;
	}



	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayerName(Player player) {
		this.player = player;
	}
}
