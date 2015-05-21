package fvsl.memory.client.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.SwingUtilities;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.pages.game.GamePageView;
import fvsl.memory.client.pages.lobby.LobbyPageView;
import fvsl.memory.client.pages.main.MainPageView;
import fvsl.memory.common.entities.GameRequest;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Request;
import fvsl.memory.common.entities.Request.RequestAction;
import fvsl.memory.common.entities.Request.RequestType;
import fvsl.memory.common.settings.Settings;
import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIUpdaterRunnable.
 */
public class GUIUpdaterRunnable implements Runnable {

	protected ObjectInputStream streamFromServer;
	protected ObjectOutputStream streamToServer;

	protected Socket serverSocket;
	private volatile Page page;
	private boolean isRunning;

	/**
	 * Instantiates a new GUI updater runnable.
	 *
	 * @param page the page
	 */
	public GUIUpdaterRunnable(Page page) {
		this.page = page;
		setRunning(true);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		System.out.println(StringResources.createdUpRun);

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

		while (isRunning()) {

			try {

				Request request = null;
				try {
					System.out.println(StringResources.w8UpReq);
					request = (Request) streamFromServer.readObject();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					return;
				} catch (EOFException e) {
					e.printStackTrace();
					break;
				}

				System.out.println(StringResources.recivedUpReq.toString() + request.getRequestType());

				if (request.getRequestAction() == RequestAction.Ask) {
					if (request.getRequestType() == RequestType.UpdateLobbyList) {

						if (page instanceof MainPageView) {
							final MainPageView mpw = (MainPageView) page;

							if (mpw != null) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										mpw.updateLobbies();
									}
								});
							}
						}
					} else if (request.getRequestType() == RequestType.UpdatePlayersList) {
						if (page instanceof LobbyPageView) {
							final LobbyPageView lpw = (LobbyPageView) page;

							if (lpw != null) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										lpw.updatePlayers();
									}
								});
							}
						}
					} else if (request.getRequestType() == RequestType.DeletedLobby) {
						if (page instanceof LobbyPageView) {
							final LobbyPageView lpw = (LobbyPageView) page;

							if (lpw != null) {

								final Lobby lobby = request.getCastedContent();

								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										lpw.respondToDeletedLobby(lobby);
									}
								});

							}
						}
					} else if (request.getRequestType() == RequestType.SetupGame) {
						if (page instanceof LobbyPageView) {
							final LobbyPageView lpw = (LobbyPageView) page;

							if (lpw != null) {

								final Lobby lobby = request.getCastedContent();

								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										lpw.respondToStartGame(lobby);
									}
								});

							}
						}
					} else if (request.getRequestType() == RequestType.GameRequest) {
						if (page instanceof GamePageView) {
							final GamePageView gpw = (GamePageView) page;

							if (gpw != null) {

								final GameRequest gameRequest = request.getCastedContent();

								System.out.println(StringResources.gameReq.toString() + gameRequest.getAction());

								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										gpw.respondToGameRequest(gameRequest);
									}
								});

							}

						}
					} else if (request.getRequestType() == RequestType.StartGame) {
						if (page instanceof GamePageView) {
							final GamePageView gpw = (GamePageView) page;

							if (gpw != null) {

								final Lobby lobby = request.getCastedContent();

								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										gpw.startGame(lobby);
									}
								});

							}

						}
					} else if (request.getRequestType() == RequestType.EndGame) {
						if (page instanceof GamePageView) {
							final GamePageView gpw = (GamePageView) page;

							if (gpw != null) {

								final String id = request.getCastedContent();

								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										gpw.endGame(id);
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

	/**
	 * Close.
	 *
	 * @param closeSocket the close socket
	 */
	public void close(boolean closeSocket) {
		try {
			if (streamFromServer != null)
				streamFromServer.close();
			if (streamToServer != null)
				streamToServer.close();
			if (closeSocket && serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * Checks if is running.
	 *
	 * @return true, if is running
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Sets the running.
	 *
	 * @param isRunning the new running
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
