package fvsl.memory.client.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.SwingUtilities;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.pages.lobby.LobbyPageView;
import fvsl.memory.client.pages.main.MainPageView;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Request;
import fvsl.memory.common.entities.Request.RequestAction;
import fvsl.memory.common.entities.Request.RequestType;
import fvsl.memory.common.settings.Settings;

public class GUIUpdaterRunnable implements Runnable{

	protected ObjectInputStream streamFromServer;
	protected ObjectOutputStream streamToServer;

	protected Socket serverSocket;
	private volatile Page page;

	public GUIUpdaterRunnable(Page page){
		this.page = page;
	}

	@Override
	public void run() {
		
		System.out.println("Created gui updater runnable");

		try {
			serverSocket = new Socket(InetAddress.getLocalHost(), Settings.UPDATE_PORT);
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

		while (!Thread.currentThread().isInterrupted() && !serverSocket.isClosed()){

			try {

				Request request = null;
				try {
					System.out.println("Waiting for update request...");
					request = (Request)streamFromServer.readObject();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					return;
				} catch (EOFException e) {
					e.printStackTrace();
					break;
				} 

				System.out.println("Received update request " + request.getRequestType());

				if (request.getRequestAction() == RequestAction.Ask){
					if (request.getRequestType() == RequestType.UpdateLobbyList){

						if (page instanceof MainPageView){
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
					} else if (request.getRequestType() == RequestType.UpdatePlayersList){
						if (page instanceof LobbyPageView){
							final LobbyPageView lpw = (LobbyPageView)page;

							
							if (lpw != null){
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										lpw.updatePlayers();
									}
								});
							}
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		close(false);
	}

	public void close(boolean closeSocket){
		try {
			if (streamFromServer != null)
				streamFromServer.close();
			if (streamToServer != null)
				streamToServer.close();
			if (closeSocket && serverSocket != null){
				serverSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}
	
}
