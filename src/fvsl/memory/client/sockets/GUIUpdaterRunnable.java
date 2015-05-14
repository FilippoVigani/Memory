package fvsl.memory.client.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.SwingUtilities;

import fvsl.memory.client.entities.Request;
import fvsl.memory.client.entities.Request.RequestAction;
import fvsl.memory.client.entities.Request.RequestType;
import fvsl.memory.client.pages.Page;
import fvsl.memory.client.pages.main.MainPageView;
import fvsl.memory.client.shell.Global;

public class GUIUpdaterRunnable implements Runnable{

	protected ObjectInputStream streamFromServer;
	protected ObjectOutputStream streamToServer;

	protected Socket serverSocket;
	private Page page;

	public GUIUpdaterRunnable(Page page){
		this.page = page;
	}

	@Override
	public void run() {

		System.out.println("Created gui updater runnable");
		
		try {
			serverSocket = new Socket(InetAddress.getLocalHost(), Global.UPDATE_PORT);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			streamFromServer = new ObjectInputStream(serverSocket.getInputStream());		
			streamToServer = new ObjectOutputStream(serverSocket.getOutputStream()); 
			streamToServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		while (!Thread.currentThread().isInterrupted()){

			try {

				Request request = null;
				try {
					System.out.println("Waiting for update request...");
					request = (Request)streamFromServer.readObject();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (EOFException e) {
					e.printStackTrace();
					break;
				} 
				
				System.out.println("Received update request " + request.getRequestType());

				if (request.getRequestAction() == RequestAction.Ask){
					if (request.getRequestType() == RequestType.UpdateLobbyList){
						
						final MainPageView mpw = (MainPageView)page;
						
						if (mpw != null){
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									mpw.updateLobbies();
								}
							});
						}	
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		try {
			streamToServer.close();
			streamFromServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
