package fvsl.memory.client.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.text.AbstractDocument.Content;

import fvsl.memory.client.ui.Request.LobbyJoiningResult;
import fvsl.memory.client.ui.Request.RequestAction;
import fvsl.memory.client.ui.Request.RequestType;

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
		/*
		finally
		{
			try
			{
				socket.close();
				System.out.println("Socket closed");
			}
			catch(IOException ioEx)
			{
				System.err.println("Impossibile chiudere connessione!");
				System.err.println("Può darsi non sia stata creata!");
				System.err.println("link è: " + ioEx.getMessage());
				System.exit(1);
			}
		}*/
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

	public ArrayList<Lobby> requestLobbies(String player){
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetLobbies, null));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Request sent");
		ArrayList<Lobby> list = null;
		try {
			Request obj = (Request)streamFromServer.readObject();
			list = (ArrayList<Lobby>)(obj.getContent());
			System.out.println("Something has been received: " + obj.getRequestType()+ " " +obj.getRequestAction());
			if (list == null){
				list = new ArrayList<Lobby>();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return list;
	}
	
	public LobbyJoiningResult requestJoinLobby(String player, Lobby lobby, String password){
		connect();
		try {
			streamToServer.reset();
			ArrayList<Object> content = new ArrayList<Object>();
			content.add(lobby);
			content.add(password);
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.JoinLobby, content));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		LobbyJoiningResult result = LobbyJoiningResult.Failed;
		
		try {
			Request obj = (Request)streamFromServer.readObject();
			result = (LobbyJoiningResult)obj.getContent();
			System.out.println("Something has been received: " + obj.getRequestType()+ " " +obj.getRequestAction() + " " + result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		closeConnection();
		
		return result;
	}
}
