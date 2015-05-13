package fvsl.memory.server.sockets;

import java.awt.TextArea;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;

import fvsl.memory.server.db.ServerData;

public class Server extends JFrame implements Runnable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6466080193597906474L;
	protected int          serverPort   = 17829;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;

    private ServerData serverData;
    
    public Server(int port){	
    	super();
        this.serverPort = port;
        serverData = new ServerData();
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            if (clientSocket == null) System.out.println("Client socket is null");
            
            new Thread(
                new ClientRunnable(
                    clientSocket, "Multithreaded Server", serverData)
            ).start();
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

		Server s = new Server(17829);
		s.setVisible(true);
		s.setDefaultCloseOperation(EXIT_ON_CLOSE);
		s.setSize(200, 100);
		TextArea tArea = new TextArea();
		s.add(tArea);
		
		new Thread(s).start();
	}
}
