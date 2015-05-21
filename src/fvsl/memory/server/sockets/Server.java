package fvsl.memory.server.sockets;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import fvsl.memory.common.settings.Settings;
import fvsl.memory.common.util.StringResources;
import fvsl.memory.server.db.ServerData;
import fvsl.memory.server.util.MessageConsole;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466080193597906474L;
	private static final int MAX_CLIENTS = 100;
	protected int serverPort;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;

	private final ExecutorService pool;

	protected volatile ServerData serverData;

	/**
	 * Instantiates a new server.
	 * 
	 * @param port
	 *            the port
	 */
	public Server(int port) {
		this(port, new ServerData());
	}

	/**
	 * Instantiates a new server.
	 * 
	 * @param port
	 *            the port
	 * @param serverData
	 *            the server data
	 */
	public Server(int port, ServerData serverData) {
		super();
		this.serverPort = port;
		this.serverData = serverData;
		pool = Executors.newFixedThreadPool(MAX_CLIENTS);
	}

	/**
	 * Inits the view.
	 */
	public void initView() {
		setTitle(StringResources.sConsole.toString());
		setVisible(true);
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel middlePanel = new JPanel();
		// middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (),
		// "Server console" ) );

		add(middlePanel);

		JTextArea tArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(tArea);
		tArea.setEditable(false);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		middlePanel.add(scroll);
		scroll.setBounds(0, 0, 595, 372);
		// scroll.setBorder(new TitledBorder ( new EtchedBorder (),
		// "Server console" ));

		MessageConsole mc = new MessageConsole(tArea);
		mc.redirectOut(null, System.out);
		mc.redirectErr(Color.RED, System.out);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		System.out.println("" + StringResources.serverStartedOnPort + serverPort);

		while (!isStopped()) {
			try {
				if (serverPort == Settings.PORT) {
					pool.execute(new ClientRunnable(serverSocket.accept(), StringResources.multyTS.toString(), serverData));
				} else if (serverPort == Settings.UPDATE_PORT) {
					ClientUpdaterRunnable updater = new ClientUpdaterRunnable(serverSocket.accept(), StringResources.upServ.toString(), serverData);
					serverData.getClientUpdaters().add(updater);
					pool.execute(updater);
				}
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println(StringResources.sStop);
					return;
				}
				throw new RuntimeException(StringResources.errorAcptCC.toString(), e);
			}
			/*
			 * if (clientSocket == null){
			 * System.out.println("Client socket is null"); } else {
			 * System.out.println("New client on port " + serverPort); if
			 * (this.serverPort == Global.PORT){ ClientRunnable runnable = new
			 * ClientRunnable(clientSocket, "Multithreaded Server", serverData);
			 * new Thread(runnable).start(); } else if (this.serverPort ==
			 * Global.UPDATE_PORT){ ClientUpdaterRunnable runnable = new
			 * ClientUpdaterRunnable(clientSocket, "Updater Server",
			 * serverData); synchronized (serverData.getClientUpdaters()) {
			 * serverData.getClientUpdaters().add(runnable); }
			 * System.out.println("Client updater added."); new
			 * Thread(runnable).start(); } }
			 */
		}
		System.out.println(StringResources.sStop);
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	/**
	 * Stop.
	 */
	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException(StringResources.errorCloseS.toString(), e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException(StringResources.cantOpnePort.toString() + serverPort, e);
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		Server s = new Server(Settings.PORT);
		s.setDefaultCloseOperation(EXIT_ON_CLOSE);
		s.initView();

		Server sUpdater = new Server(Settings.UPDATE_PORT, s.serverData);
		new Thread(sUpdater).start();
		s.run();
	}
}
