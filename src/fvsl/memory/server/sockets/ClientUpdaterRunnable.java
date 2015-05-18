package fvsl.memory.server.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import fvsl.memory.common.entities.Request;
import fvsl.memory.server.db.ServerData;

public class ClientUpdaterRunnable implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	protected boolean isStopped;

	protected volatile ServerData serverData;

	protected volatile Request request = null;

	public ClientUpdaterRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.serverData = serverData;
	}

	public void run(){
		try {
			streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

			streamToClient.flush();

			System.out.println("Created output stream for updater serverside");

			streamFromClient = new ObjectInputStream(clientSocket.getInputStream());

			System.out.println("Created input stream for updater serverside");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (!isStopped()){
			synchronized (this) {
				if (request != null){
					System.out.println("New update request found! - " + request.getRequestType());
					try {
						streamToClient.writeObject(request);
						streamToClient.flush();
					} catch (SocketException e) {
						clean(false);
						return;
					} catch (IOException e) {
						e.printStackTrace();
					} 
					setRequest(null);
				}
			}
		}
	}

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public synchronized void setRequest(Request request) {
		this.request = request;
	}

	private void clean(boolean closeSocket){
		try {
			streamFromClient.close();
			streamToClient.close();
			if (closeSocket){
				clientSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the isStopped
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * @param isStopped the isStopped to set
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

}
