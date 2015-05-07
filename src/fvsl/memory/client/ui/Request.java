package fvsl.memory.client.ui;

import java.io.Serializable;

import fvsl.memory.client.ui.Request.RequestAction;

public class Request implements Serializable {
	
	private int requestType;
	private int requestAction;
	private Object content;
	
	public Request(int requestAction, int requestType, Object content) {
		super();
		this.requestAction = requestAction;
		this.requestType = requestType;
		this.content = content;
	}
	
	public Request(int requestAction) {
		this.requestAction = requestAction;
	}
	
	public Request() {
		// TODO Auto-generated constructor stub
	}

	public abstract class RequestType{
		public final static int GetLobbies = 0;
		public final static int JoinLobby = 1;
		public final static int CreateLobby = 2;
		public final static int Handshake = 3;
		public final static int FuckAllTheBitches = 4;
	}
	
	public abstract class RequestAction{
		public final static int Ask = 0;
		public final static int Reply = 1;
	}

	/**
	 * @return the requestType
	 */
	public int getRequestType() {
		return requestType;
	}

	/**
	 * @return the requestAction
	 */
	public int getRequestAction() {
		return requestAction;
	}

	/**
	 * @param requestAction the requestAction to set
	 */
	public void setRequestAction(int requestAction) {
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

	public void setRequestType(int type) {
		requestType = type;
	}
}
