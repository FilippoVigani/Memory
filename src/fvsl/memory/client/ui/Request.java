package fvsl.memory.client.ui;

import java.io.Serializable;

import fvsl.memory.client.ui.Request.RequestAction;

public class Request implements Serializable {
	
	private String playerName;
	private RequestAction requestAction;
	private RequestType requestType;
	private Object content;
	
	public Request(String playerName, RequestAction requestAction, RequestType requestType, Object content) {
		super();
		this.playerName = playerName;
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
		GetPossiblePlayersNumbers,
		GetPossiblePairsNumbers,
		GetPossibleTimerNumbers
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
	 * @param content the content to set
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	public void setRequestType(RequestType type) {
		requestType = type;
	}



	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}



	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
