package fvsl.memory.server.sockets;

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
import fvsl.memory.client.entities.Request.StatusChangeResult;
import fvsl.memory.server.db.ServerData;

public class ClientRunnable implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	
	protected volatile ServerData serverData;

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
							Lobby lobby = serverData.getLobbyById(lobbyID);
							
							String password = (String)contents.get(1);
							
							//Checks and returns result
							if (lobby == null){
								reply.setContent(LobbyJoiningResult.NotFound);
							} else if (player == null || player.getName() == null || player.getName().isEmpty() || lobby.getConnectedPlayerByName(player.getName()) != null){
								reply.setContent(LobbyJoiningResult.UnacceptedUsername);
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
							
							notifyUpdate(RequestType.UpdateLobbyList);
							
							
						} else if (request.getRequestType() == RequestType.GetConnectedPlayers){
							Lobby srcLobby = request.getCastedContent();
							String lobbyID = srcLobby.getId();
							Lobby lobby = serverData.getLobbyById(lobbyID);
							Player player = lobby.getConnectedPlayerByName(request.getPlayer().getName());
							if (lobby != null && player != null){ //Restituisce la lista solo se il player che la richiede è connesso
								reply.setContent(lobby.getConnectedPlayers());
							}
						} else if (request.getRequestType() == RequestType.SetPlayerStatusReady){
							reply.setContent(StatusChangeResult.Failed);
							Player srcPlayer = request.getPlayer();
							Lobby srcLobby = request.getCastedContent();
							Lobby lobby = serverData.getLobbyById(srcLobby.getId());
							if (lobby != null){
								Player player = lobby.getConnectedPlayerByName(srcPlayer.getName());
								if (player != null){
									player.setReady(true);
									reply.setContent(StatusChangeResult.Accepted);
								}
							}
						} else if (request.getRequestType() == RequestType.LeaveLobby){
							Player srcPlayer = request.getPlayer();
							Lobby srcLobby = request.getCastedContent();
							Lobby lobby = serverData.getLobbyById(srcLobby.getId());
							if (lobby != null){
								Player player = lobby.getConnectedPlayerByName(srcPlayer.getName());
								if (player != null){
									synchronized (lobby) {
										lobby.getConnectedPlayers().remove(player);
									}
								}
								if (player.getName().equals(lobby.getOwner().getName())){
									System.out.println("Owner of lobby left, removing lobby.");
									serverData.getLobbies().remove(lobby);
									notifyUpdate(RequestType.UpdateLobbyList);
								}
							}
							
						}
						
					}
				}

				//Returning results
				streamToClient.reset();
				streamToClient.writeObject(reply);
				streamToClient.flush();

				System.out.println("Request fulfilled...");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

	}
	
	private void notifyUpdate(RequestType requestType){
		for (ClientUpdaterRunnable runnable : serverData.getClientUpdaters()){
			if (runnable == null) {System.out.println("runnable null");}
			runnable.setRequest(new Request(null, RequestAction.Ask, requestType, null));
			
		}
	}

}
