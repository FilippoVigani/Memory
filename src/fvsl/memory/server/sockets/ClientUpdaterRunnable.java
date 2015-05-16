package fvsl.memory.server.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fvsl.memory.client.entities.Request;
import fvsl.memory.client.entities.StringResources;
import fvsl.memory.server.db.ServerData;

public class ClientUpdaterRunnable extends ClientRunnable {
	
	private volatile Request request = null;

	public ClientUpdaterRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		super(clientSocket, serverText, serverData);
	}
	
	@Override
	public void run(){
		try {
			streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

			streamToClient.flush();

			System.out.println(StringResources.createdUpOS.getArgoument());

			streamFromClient = new ObjectInputStream(clientSocket.getInputStream());
			
			System.out.println(StringResources.createdUpIS.getArgoument());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
			if (request != null){
				System.out.println(StringResources.newUpReq.getArgoument()+ request.getRequestType());
				try {
					streamToClient.reset();
					streamToClient.writeObject(request);
					streamToClient.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				synchronized (this) {
					request = null;
				}
			}
		}
	}

	/**
	 * @return the request
	 */
	public synchronized Request getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public synchronized void setRequest(Request request) {
		this.request = request;
	}

}
