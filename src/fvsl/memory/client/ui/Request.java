package fvsl.memory.client.ui;

import java.io.Serializable;

public class Request implements Serializable {
	
	private RequestType requestType;
	private RequestAction requestAction;
	private Object content;
	
	public Request(RequestAction requestAction, RequestType requestType, Object content) {
		super();
		this.requestAction = requestAction;
		this.requestType = requestType;
		this.content = content;
	}
	
	public enum RequestType{
		GetLobbies,
		JoinLobby,
		CreateLobby,
		Handshake,
		FuckAllTheBitches
	}
	
	public enum RequestAction{
		Ask,
		Reply
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
}
