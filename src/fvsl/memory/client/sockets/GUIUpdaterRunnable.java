package fvsl.memory.client.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fvsl.memory.client.entities.Request;

public class GUIUpdaterRunnable implements Runnable{

	protected ObjectInputStream streamFromServer;
	protected ObjectOutputStream streamToServer;
	
	protected Socket serverSocket;
	
	public GUIUpdaterRunnable(Socket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	@Override
	public void run() {

		try {
			streamFromServer = new ObjectInputStream(serverSocket.getInputStream());		
			streamToServer = new ObjectOutputStream(serverSocket.getOutputStream()); 
			streamToServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		while (true){

			try {

				Request request = null;
				try {
					//System.out.println("Waiting for request...");
					request = (Request)streamFromServer.readObject();

				} catch (ClassNotFoundException e) {
					//e.printStackTrace();
				} catch (EOFException e) {
					break;
				} 
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	}

}
