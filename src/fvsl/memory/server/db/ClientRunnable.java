package fvsl.memory.server.db;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fvsl.memory.client.ui.MockFactory;
import fvsl.memory.client.ui.Request;
import fvsl.memory.client.ui.Request.RequestAction;
import fvsl.memory.client.ui.Request.RequestType;

public class ClientRunnable implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	private boolean connectionOpened;
	
	private ServerData serverData;

	public ClientRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		this.clientSocket = clientSocket;
		this.serverText   = serverText;
		this.serverData = serverData;
	}

	@Override
	public void run() {

		try {
			streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

			streamToClient.flush();

			System.out.println("Created output stream serverside");

			streamFromClient = new ObjectInputStream(clientSocket.getInputStream());

			//streamToClient.writeObject(new Request(RequestAction.Ask, RequestType.Handshake, null));
			streamToClient.flush();
			
			System.out.println("Created input stream serverside");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true){

			try {

				Request request = null;
				try {
					System.out.println("Waiting for request...");
					request = (Request)streamFromClient.readObject();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 

				System.out.println("Request received...");

				//Processing request - DA MIGLIORARE
				Request reply = new Request(RequestAction.Reply);
				if (request != null){
					if (request.getRequestAction() == RequestAction.Ask){
						System.out.print("Request ask ");
						if (request.getRequestType() == RequestType.GetLobbies){
							System.out.println("lobbies");
							reply.setContent(serverData.getLobbies());
							reply.setRequestType(RequestType.GetLobbies);
						}
					}
				}

				//Returning results
				streamToClient.reset();
				streamToClient.writeObject(reply);
				streamToClient.flush();

				System.out.print("Request fulfilled...");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			/*finally {
				try {
					System.out.println("Closing connection");
					streamFromClient.close();
					streamToClient.close();
					clientSocket.close();
				} catch (IOException ioEx) {
					System.err.println("Can't close connection, shutting down.");
					System.err.println(ioEx);
					System.exit(1);
				}
			}*/
			
		}

	}

}
