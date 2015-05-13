package fvsl.memory.server.db;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.entities.Request;
import fvsl.memory.client.entities.Request.LobbyJoiningResult;
import fvsl.memory.client.entities.Request.RequestAction;
import fvsl.memory.client.entities.Request.RequestType;

public class ClientRunnable implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	
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

			streamToClient.flush();
			
			System.out.println("Created input stream serverside");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true){

			try {

				Request request = null;
				try {
					//System.out.println("Waiting for request...");
					request = (Request)streamFromClient.readObject();

				} catch (ClassNotFoundException e) {
					//e.printStackTrace();
				} catch (EOFException e) {
					break;
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
							ArrayList<Object> contents = request.getCastedContent();
							Player player = request.getPlayer(); 
							String lobbyID = ((Lobby)contents.get(0)).getId();
							Lobby lobby = null;
							boolean found = false;
							for (int i = 0; !found && i < serverData.getLobbies().size(); i++){
								lobby = serverData.getLobbies().get(i);
								found = lobby.getId().equals(lobbyID);
							}
							
							String password = (String)contents.get(1);
							
							//Checks and returns result
							if (player == null || player.getName() == null || player.getName().isEmpty()){
								reply.setContent(LobbyJoiningResult.UnacceptedUsername);
							} else if (!found || lobby == null){
								reply.setContent(LobbyJoiningResult.NotFound);
							} else if (!lobby.checkPassword(password)){
								reply.setContent(LobbyJoiningResult.WrongPassword);
							} else if (lobby.getConnectedPlayers().size() >= lobby.getNumberOfPlayers()){
								reply.setContent(LobbyJoiningResult.FullLobby);
							} else {
								reply.setContent(LobbyJoiningResult.Accepted);
								player.setReady(false);
								synchronized (lobby) {
									lobby.getConnectedPlayers().add(player);
								}
							}
							
						} else if (request.getRequestType() == RequestType.CreateLobby){
							ArrayList<Object> contents = request.getCastedContent();
							Player player = request.getPlayer();
							Lobby lobby = (Lobby)contents.get(0);
							String password = (String)contents.get(1);
							
							synchronized (serverData.getLobbies()) {
								String newId = UUID.randomUUID().toString();
								lobby.setId(newId);
								lobby.setPassword(password); //Meh...
								lobby.setOwner(player);
								serverData.getLobbies().add(lobby);
								reply.setContent(newId);
								System.out.println("Lobby creata con id " + newId);
							}
						} else if (request.getRequestType() == RequestType.GetConnectedPlayers){
							//Qui dovrebbe cercare se il player che manda la richiesta �
							//contenuto nella lista di player connessi
							Lobby l = request.getCastedContent();
							String lobbyID = l.getId();
							Lobby lobby = null;
							boolean found = false;
							for (int i = 0; !found && i < serverData.getLobbies().size(); i++){
								lobby = serverData.getLobbies().get(i);
								found = lobby.getId().equals(lobbyID);
							}
							if (found){
								reply.setContent(lobby.getConnectedPlayers());
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
		}

	}

}
