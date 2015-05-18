package fvsl.memory.server.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.UUID;

import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;
import fvsl.memory.common.entities.Request.LobbyLeavingResult;
import fvsl.memory.common.entities.Request.RequestAction;
import fvsl.memory.common.entities.Request.RequestType;
import fvsl.memory.common.entities.Request.StatusChangeResult;
import fvsl.memory.server.db.ServerData;

public class ClientRunnable implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	protected boolean isStopped;

	protected volatile ServerData serverData;

	public ClientRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.serverData = serverData;
	}

	@Override
	public void run() {

		while (!isStopped()){
			//Opening streams
			try {
				streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

				System.out.println("Created output stream serverside");

				streamFromClient = new ObjectInputStream(clientSocket.getInputStream());

				streamToClient.flush();

				System.out.println("Created input stream serverside");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (!isStopped()){

				try {
					Request request = null;
					try {
						//System.out.println("Waiting for request...");
						request = (Request)streamFromClient.readObject();

					} catch (ClassNotFoundException e) {
						//e.printStackTrace();
					} catch (EOFException e) {
						break;
					} catch (SocketException e){
						System.out.println("Socket error, closing");
						clean();
						return;
					}

					System.out.print("Request received ");

					//Processing request - DA MIGLIORARE
					Request reply = null;
					if (request != null){
						if (request.getRequestAction() == RequestAction.Ask){
							System.out.println(request.getRequestType());
							switch (request.getRequestType()) {
							case GetLobbies:
								reply = getLobbies(request);
								break;
							case JoinLobby:
								reply = joinLobby(request);
								break;
							case CreateLobby:
								reply = createLobby(request);
								break;
							case GetConnectedPlayers:
								reply = getConnectedPlayers(request);
								break;
							case SetPlayerStatusReady:
								reply = setPlayerStatusReady(request);
								break;
							case LeaveLobby: 
								reply = leaveLobby(request);
								break;
							default:
								break;
							}
						}
					}

					//Returning results
					streamToClient.writeObject(reply);
					streamToClient.flush();
					System.out.println("Request fulfilled...");
					clean();
					setStopped(true);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}

	private void clean(){
		try {
			streamFromClient.close();
			streamToClient.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Request getLobbies(Request request){
		Request reply = new Request(RequestAction.Reply);
		synchronized (serverData.getLobbies()) {
			reply.setContent(serverData.getLobbies());
		}
		reply.setRequestType(RequestType.GetLobbies);
		return reply;
	}

	private Request joinLobby(Request request){
		Request reply = new Request(RequestAction.Reply);
		ArrayList<Object> contents = request.getCastedContent();
		Player player = request.getPlayer(); 
		String lobbyID = ((Lobby)contents.get(0)).getId();
		Lobby lobby = serverData.getLobbyById(lobbyID);

		String password = (String)contents.get(1);

		//Checks and returns result
		if (lobby == null){
			reply.setContent(LobbyJoiningResult.NotFound);
		} else {
			synchronized (serverData.getLobbies()) {
				if (player == null || player.getName() == null || player.getName().isEmpty() || lobby.getConnectedPlayerByName(player.getName()) != null){
					reply.setContent(LobbyJoiningResult.UnacceptedUsername);
				} else if (!lobby.checkPassword(password)){
					reply.setContent(LobbyJoiningResult.WrongPassword);
				} else if (lobby.getConnectedPlayers().size() >= lobby.getNumberOfPlayers()){
					reply.setContent(LobbyJoiningResult.FullLobby);
				} else {
					reply.setContent(LobbyJoiningResult.Accepted);
					player.setReady(false);
					lobby.getConnectedPlayers().add(player);
				}
			}
		}
		notifyUpdate(RequestType.UpdatePlayersList);
		return reply;
	}

	private Request createLobby(Request request){
		Request reply = new Request(RequestAction.Reply);
		ArrayList<Object> contents = request.getCastedContent();
		Player player = request.getPlayer();
		Lobby srcLobby = (Lobby)contents.get(0);
		String password = (String)contents.get(1);

		System.out.println("Preso contenuto");


		String newId = UUID.randomUUID().toString();
		/*
		Lobby lobby = new Lobby(newId, srcLobby.getName(), srcLobby.getNumberOfPlayers(), srcLobby.getNumberOfPairs(), srcLobby.getTurnTimer(), password);

		System.out.println("Creato oggetto");

		lobby.setOwner(player);


		synchronized (serverData.getLobbies()) {
			System.out.println("get lobbies");
			serverData.getLobbies().add(lobby);
			System.out.println("aggiunta lobby");
		}*/

		srcLobby.setId(newId);
		srcLobby.setPassword(password);
		srcLobby.setOwner(player);

		synchronized (serverData.getLobbies()) {
			serverData.getLobbies().add(srcLobby);
		}

		//}

		reply.setContent(newId);
		System.out.println("Lobby creata con id " + newId);


		notifyUpdate(RequestType.UpdateLobbyList);
		return reply;
	}

	private Request getConnectedPlayers(Request request){
		Request reply = new Request(RequestAction.Reply);

		Lobby srcLobby = request.getCastedContent();
		String lobbyID = srcLobby.getId();
		Lobby lobby = serverData.getLobbyById(lobbyID);
		if (lobby != null){
			Player player = lobby.getConnectedPlayerByName(request.getPlayer().getName());
			if (lobby != null && player != null){ //Restituisce la lista solo se il player che la richiede è connesso
				reply.setContent(lobby.getConnectedPlayers());
			}
		}
		return reply;
	}

	private Request setPlayerStatusReady(Request request){
		Request reply = new Request(RequestAction.Reply);
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
		notifyUpdate(RequestType.UpdatePlayersList);
		return reply;
	}

	private Request leaveLobby(Request request){
		Request reply = new Request(RequestAction.Reply);
		reply.setContent(LobbyLeavingResult.Failed);
		Player srcPlayer = request.getPlayer();
		Lobby srcLobby = request.getCastedContent();
		Lobby lobby = serverData.getLobbyById(srcLobby.getId());
		if (lobby != null){
			Player player = lobby.getConnectedPlayerByName(srcPlayer.getName());
			if (player != null){
				synchronized (lobby) {
					lobby.getConnectedPlayers().remove(player);
				}
				reply.setContent(LobbyLeavingResult.Accepted);
				if (lobby.getOwner() != null){
					if (player.getName().equals(lobby.getOwner().getName())){
						System.out.println("Owner of lobby left, removing lobby.");
						serverData.getLobbies().remove(lobby);
						notifyUpdate(RequestType.UpdateLobbyList);
					}
				}
			}
		}
		notifyUpdate(RequestType.UpdatePlayersList);
		return reply;
	}

	private void notifyUpdate(RequestType requestType){
		for (ClientUpdaterRunnable runnable : serverData.getClientUpdaters()){
			if (runnable == null) {serverData.getClientUpdaters().remove(runnable);}
			runnable.setRequest(new Request(null, RequestAction.Ask, requestType, null));
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
