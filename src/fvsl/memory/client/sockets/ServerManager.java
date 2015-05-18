package fvsl.memory.client.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request;
import fvsl.memory.common.entities.Request.LobbyCreationResult;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;
import fvsl.memory.common.entities.Request.LobbyLeavingResult;
import fvsl.memory.common.entities.Request.RequestAction;
import fvsl.memory.common.entities.Request.RequestType;
import fvsl.memory.common.entities.Request.StatusChangeResult;

public class ServerManager {

	private static InetAddress host;
	private static final int PORT = 17829;
	private ObjectInputStream streamFromServer;
	private ObjectOutputStream streamToServer;
	Socket socket = null;


	public ServerManager(){
	}

	public void connect(){
		try
		{
			host = InetAddress.getLocalHost();
		}
		catch(UnknownHostException uhEx)
		{
			System.err.println("Non trovo indirizzo calcolatore ospite!");
			System.err.println(uhEx);
			System.exit(1);
		}

		try 
		{
			socket = new Socket(host, PORT);

			System.out.println("Connected to " + host.getHostAddress());

			streamFromServer = new ObjectInputStream(socket.getInputStream()); 

			System.out.println("Created input stream clientside");

			streamToServer = new ObjectOutputStream(socket.getOutputStream()); 

			streamToServer.flush();

			System.out.println("Created output stream clientside");

		}
		catch(IOException ioEx)
		{
			System.err.println("IOException:" + ioEx.getMessage());
		}   
	}

	public void closeConnection(){
		try {
			streamFromServer.close();
			streamToServer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Vector<Lobby> requestLobbies(Player player) throws Exception{
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetLobbies, null));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Request sent");
		Vector<Lobby> list = null;
		try {
			Request obj = (Request)streamFromServer.readObject();
			list = obj.getCastedContent();
			System.out.println("Something has been received: " + obj.getRequestType()+ " " +obj.getRequestAction());
			if (list == null){
				list = new Vector<Lobby>();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return list;
	}
	
	public LobbyJoiningResult requestJoinLobby(Player player, Lobby lobby, String password) throws Exception{
		connect();
		try {
			ArrayList<Object> content = new ArrayList<Object>();
			content.add(lobby);
			content.add(password);
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.JoinLobby, content));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		LobbyJoiningResult result = LobbyJoiningResult.Failed;
		
		try {
			Request obj = (Request)streamFromServer.readObject();
			result = obj.getCastedContent();
			System.out.println("Something has been received: " + obj.getRequestType()+ " " +obj.getRequestAction() + " " + result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}
	
	public LobbyLeavingResult requestLeaveLobby(Player player, Lobby lobby) throws Exception{
		connect();
		try {		
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.LeaveLobby, lobby));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		LobbyLeavingResult result = LobbyLeavingResult.Failed;
		
		try {
			Request obj = (Request)streamFromServer.readObject();
			result = obj.getCastedContent();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}
	
	public LobbyCreationResult requestCreateLobby(Player player, Lobby lobby, String password) throws Exception{
		connect();
		try {
			ArrayList<Object> content = new ArrayList<Object>();
			content.add(lobby);
			content.add(password);
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.CreateLobby, content));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		LobbyCreationResult result = LobbyCreationResult.Failed;
		
		try {
			Request obj = (Request)streamFromServer.readObject();
			String lobbyId = obj.getCastedContent();
			if (!(lobbyId == null || lobbyId.isEmpty())){
				result = LobbyCreationResult.Accepted;
				lobby.setId(lobbyId);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	public Vector<Player> requestConnectedPlayers(Player player, Lobby lobby) throws Exception{
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetConnectedPlayers, lobby));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Vector<Player> result = null;
		
		try {
			Request obj = (Request)streamFromServer.readObject();
			result = obj.getCastedContent();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	public StatusChangeResult requestSetStatusReady(Player player, Lobby lobby) {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.SetPlayerStatusReady, lobby));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		StatusChangeResult result = StatusChangeResult.Failed;
		
		try {
			Request obj = (Request)streamFromServer.readObject();
			result = obj.getCastedContent();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}
}