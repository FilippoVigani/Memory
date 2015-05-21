package fvsl.memory.server.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import fvsl.memory.common.entities.Request;
import fvsl.memory.common.util.StringResources;
import fvsl.memory.server.db.ServerData;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientUpdaterRunnable.
 */
public class ClientUpdaterRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	protected boolean isStopped;

	protected volatile ServerData serverData;

	protected volatile Vector<Request> requests;

	/**
	 * Instantiates a new client updater runnable.
	 * 
	 * @param clientSocket
	 *            the client socket
	 * @param serverText
	 *            the server text
	 * @param serverData
	 *            the server data
	 */
	public ClientUpdaterRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.serverData = serverData;
		requests = new Vector<Request>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

			streamToClient.flush();

			System.out.println(StringResources.createdUpOS);

			streamFromClient = new ObjectInputStream(clientSocket.getInputStream());

			System.out.println(StringResources.createdUpIS);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (!isStopped()) {
			synchronized (this) {
				if (requests.size() > 0) {
					System.out.println(StringResources.pollNewUpReq.toString() + requests.firstElement().getRequestType());
					try {
						streamToClient.writeObject(requests.firstElement());
						streamToClient.flush();
					} catch (SocketException e) {
						clean(false);
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					requests.remove(requests.firstElement());
				}
			}
		}
	}

	private void clean(boolean closeSocket) {
		try {
			streamFromClient.close();
			streamToClient.close();
			if (closeSocket) {
				clientSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks if is stopped.
	 * 
	 * @return true, if is stopped
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * Sets the stopped.
	 * 
	 * @param isStopped
	 *            the new stopped
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	/**
	 * Gets the requests.
	 * 
	 * @return the requests
	 */
	public Vector<Request> getRequests() {
		return requests;
	}

	/**
	 * Sets the requests.
	 * 
	 * @param requests
	 *            the new requests
	 */
	public void setRequests(Vector<Request> requests) {
		this.requests = requests;
	}

}
