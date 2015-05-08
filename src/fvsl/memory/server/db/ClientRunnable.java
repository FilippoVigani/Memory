package fvsl.memory.server.db;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import fvsl.memory.client.ui.Lobby;
import fvsl.memory.client.ui.MockFactory;
import fvsl.memory.client.ui.Player;
import fvsl.memory.client.ui.Request;
import fvsl.memory.client.ui.Request.LobbyJoiningResult;
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
						else if (request.getRequestType() == RequestType.JoinLobby){
							ArrayList<Object> contents = (ArrayList<Object>)request.getContent();
							String player = request.getPlayerName(); //Player dovrebbe essere un oggetto! da modificare
							int lobbyID = ((Lobby)contents.get(0)).getId();
							Lobby lobby = null;
							boolean found = false;
							for (int i = 0; !found && i < serverData.getLobbies().size(); i++){
								lobby = serverData.getLobbies().get(i);
								found = lobby.getId() == lobbyID;
							}
							
							String password = (String)contents.get(1);
							
							//Checks and returns result
							if (player == null || player.isEmpty()){
								reply.setContent(LobbyJoiningResult.UnacceptedUsername);
							} else if (!found || lobby == null){
								reply.setContent(LobbyJoiningResult.NotFound);
							} else if (!lobby.checkPassword(password)){
								reply.setContent(LobbyJoiningResult.WrongPassword);
							} else if (lobby.getConnectedPlayers().size() >= lobby.getNumberOfPlayers()){
								reply.setContent(LobbyJoiningResult.FullLobby);
							} else {
								reply.setContent(LobbyJoiningResult.Accepted);
								synchronized (lobby) {
									lobby.getConnectedPlayers().add(new Player(player));
								}
							}
							
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
