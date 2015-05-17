package fvsl.memory.server.sockets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.io.*;
import java.net.*;
import java.nio.channels.SeekableByteChannel;
import java.util.concurrent.*;

import javax.sql.PooledConnection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import fvsl.memory.common.settings.Settings;
import fvsl.memory.common.util.StringResources;
import fvsl.memory.server.db.ServerData;
import fvsl.memory.server.util.MessageConsole;

public class Server extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466080193597906474L;
	private static final int MAX_CLIENTS = 100;
	protected int          serverPort;
	protected ServerSocket serverSocket = null;
	protected boolean      isStopped    = false;
	protected Thread       runningThread= null;
	
	private final ExecutorService pool;

	protected volatile ServerData serverData;

	public Server(int port){	
		this(port, new ServerData());
	}
	
	public Server(int port, ServerData serverData){	
		super();
		this.serverPort = port;
		this.serverData = serverData;
		pool = Executors.newFixedThreadPool(MAX_CLIENTS);
	}

	public void initView(){
		setTitle("Server console");
		setVisible(true);
		setSize(500, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel middlePanel = new JPanel ();
	    //middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Server console" ) );
		
	    add(middlePanel);
	    
		JTextArea tArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(tArea);
		tArea.setEditable(false);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		middlePanel.add(scroll);
		scroll.setBounds(0,0,495,272);
		//scroll.setBorder(new TitledBorder ( new EtchedBorder (), "Server console" ));
		MessageConsole mc = new MessageConsole(tArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);
		mc.redirectOut(null, System.out);
	}
	
	public void run(){
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		System.out.println("" + StringResources.serverStartedOnPort + serverPort);

		while(!isStopped()){
			try {
				if (serverPort == Settings.PORT){
					pool.execute(new ClientRunnable(serverSocket.accept(), "Multithreaded Server", serverData));
				} else if (serverPort == Settings.UPDATE_PORT){
					ClientUpdaterRunnable updater = new ClientUpdaterRunnable(serverSocket.accept(), "Updater Server", serverData);
					serverData.getClientUpdaters().add(updater);
					pool.execute(updater);
				}
			} catch (IOException e) {
				if(isStopped()) {
					System.out.println("Server Stopped.") ;
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			/*
			if (clientSocket == null){ 
				System.out.println("Client socket is null");
			} else {
				System.out.println("New client on port " + serverPort);
				if (this.serverPort == Global.PORT){
					ClientRunnable runnable = new ClientRunnable(clientSocket, "Multithreaded Server", serverData);
					new Thread(runnable).start();
				} else if (this.serverPort == Global.UPDATE_PORT){
					ClientUpdaterRunnable runnable = new ClientUpdaterRunnable(clientSocket, "Updater Server", serverData);
					synchronized (serverData.getClientUpdaters()) {
						serverData.getClientUpdaters().add(runnable);
					}
					System.out.println("Client updater added.");
					new Thread(runnable).start();
				}
			}*/
		}
		System.out.println("Server Stopped.") ;
	}


	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop(){
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + serverPort, e);
		}
	}

	public static void main(String[] args) {

		Server s = new Server(Settings.PORT);
		s.setDefaultCloseOperation(EXIT_ON_CLOSE);
		s.initView();
		
		Server sUpdater = new Server(Settings.UPDATE_PORT, s.serverData);
		new Thread(sUpdater).start();
		s.run();
	}
}
