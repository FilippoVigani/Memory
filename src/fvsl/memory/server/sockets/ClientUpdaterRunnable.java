package fvsl.memory.server.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fvsl.memory.server.db.ServerData;

public class ClientUpdaterRunnable extends ClientRunnable {

	public ClientUpdaterRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		super(clientSocket, serverText, serverData);
	}
	
	@Override
	public void run(){
		try {
			streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

			streamToClient.flush();

			System.out.println("Created output stream for updater serverside");
			
			synchronized (serverData.getClientsOutputStreams()) {
				serverData.getClientsOutputStreams().add(streamToClient);
				System.out.println("Added stream to list of streams");
			}	

			streamFromClient = new ObjectInputStream(clientSocket.getInputStream());
			
			System.out.println("Created input stream for updater serverside");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
		}
	}

}
