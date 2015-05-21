package fvsl.memory.client.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.GameRequest;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request;
import fvsl.memory.common.entities.GameRequest.GameRequestAction;
import fvsl.memory.common.entities.Request.LobbyCreationResult;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;
import fvsl.memory.common.entities.Request.LobbyLeavingResult;
import fvsl.memory.common.entities.Request.RequestAction;
import fvsl.memory.common.entities.Request.RequestType;
import fvsl.memory.common.entities.Request.StatusChangeResult;
import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerManager.
 */
public class ServerManager {

	private static InetAddress host;
	private static final int PORT = 17829;
	private ObjectInputStream streamFromServer;
	private ObjectOutputStream streamToServer;

	/** The socket. */
	Socket socket = null;

	/**
	 * Instantiates a new server manager.
	 */
	public ServerManager() {
	}

	/**
	 * Connect.
	 */
	public void connect() {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException uhEx) {
			System.err.println(StringResources.addressNotFound);
			System.err.println(uhEx);
			System.exit(1);
		}

		try {
			socket = new Socket(host, PORT);

			System.out.println(StringResources.conTo + host.getHostAddress());

			streamFromServer = new ObjectInputStream(socket.getInputStream());

			System.out.println(StringResources.createdISCS);

			streamToServer = new ObjectOutputStream(socket.getOutputStream());

			streamToServer.flush();

			System.out.println(StringResources.createdOSCS);

		} catch (IOException ioEx) {
			System.err.println(StringResources.IOExc + ioEx.getMessage());
		}
	}

	/**
	 * Close connection.
	 */
	public void closeConnection() {
		try {
			streamFromServer.close();
			streamToServer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Request to turn card.
	 * 
	 * @param player
	 *            the player
	 * @param gameId
	 *            the game id
	 * @param card
	 *            the card
	 * @throws Exception
	 *             the exception
	 */
	public void requestToTurnCard(Player player, String gameId, Card card) throws Exception {
		connect();
		try {
			streamToServer.reset();
			GameRequest request = new GameRequest(gameId, GameRequestAction.TurnCard);
			request.setCard(card);
			request.setPlayer(player);
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GameRequest, request));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		closeConnection();
	}

	/**
	 * Request lobbies.
	 * 
	 * @param player
	 *            the player
	 * @return the vector
	 * @throws Exception
	 *             the exception
	 */
	public Vector<Lobby> requestLobbies(Player player) throws Exception {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetLobbies, null));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(StringResources.reqSent);
		Vector<Lobby> list = null;
		try {
			Request obj = (Request) streamFromServer.readObject();
			list = obj.getCastedContent();
			System.out.println(StringResources.SomethingRec.toString() + obj.getRequestType() + " " + obj.getRequestAction());
			if (list == null) {
				list = new Vector<Lobby>();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return list;
	}

	/**
	 * Request join lobby.
	 * 
	 * @param player
	 *            the player
	 * @param lobby
	 *            the lobby
	 * @param password
	 *            the password
	 * @return the lobby joining result
	 * @throws Exception
	 *             the exception
	 */
	public LobbyJoiningResult requestJoinLobby(Player player, Lobby lobby, String password) throws Exception {
		connect();
		try {
			ArrayList<Object> content = new ArrayList<Object>();
			content.add(lobby);
			content.add(password);
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.JoinLobby, content));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		LobbyJoiningResult result = LobbyJoiningResult.Failed;

		try {
			Request obj = (Request) streamFromServer.readObject();
			result = obj.getCastedContent();
			System.out.println(StringResources.SomethingRec.toString() + obj.getRequestType() + " " + obj.getRequestAction() + " " + result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	/**
	 * Request leave lobby.
	 * 
	 * @param player
	 *            the player
	 * @param lobby
	 *            the lobby
	 * @return the lobby leaving result
	 * @throws Exception
	 *             the exception
	 */
	public LobbyLeavingResult requestLeaveLobby(Player player, Lobby lobby) throws Exception {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.LeaveLobby, lobby));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		LobbyLeavingResult result = LobbyLeavingResult.Failed;

		try {
			Request obj = (Request) streamFromServer.readObject();
			result = obj.getCastedContent();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	/**
	 * Request create lobby.
	 * 
	 * @param player
	 *            the player
	 * @param lobby
	 *            the lobby
	 * @param password
	 *            the password
	 * @return the lobby creation result
	 * @throws Exception
	 *             the exception
	 */
	public LobbyCreationResult requestCreateLobby(Player player, Lobby lobby, String password) throws Exception {
		connect();
		try {
			ArrayList<Object> content = new ArrayList<Object>();
			content.add(lobby);
			content.add(password);
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.CreateLobby, content));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		LobbyCreationResult result = LobbyCreationResult.Failed;

		try {
			Request obj = (Request) streamFromServer.readObject();
			String lobbyId = obj.getCastedContent();
			if (!(lobbyId == null || lobbyId.isEmpty())) {
				result = LobbyCreationResult.Accepted;
				lobby.setId(lobbyId);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	/**
	 * Request connected players.
	 * 
	 * @param player
	 *            the player
	 * @param lobby
	 *            the lobby
	 * @return the vector
	 * @throws Exception
	 *             the exception
	 */
	public Vector<Player> requestConnectedPlayers(Player player, Lobby lobby) throws Exception {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetConnectedPlayers, lobby));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Vector<Player> result = null;

		try {
			Request obj = (Request) streamFromServer.readObject();
			result = obj.getCastedContent();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	/**
	 * Request set status ready.
	 * 
	 * @param player
	 *            the player
	 * @param lobby
	 *            the lobby
	 * @return the status change result
	 */
	public StatusChangeResult requestSetStatusReady(Player player, Lobby lobby) {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.SetPlayerStatusReady, lobby));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		StatusChangeResult result = StatusChangeResult.Failed;

		try {
			Request obj = (Request) streamFromServer.readObject();
			result = obj.getCastedContent();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return result;
	}

	/**
	 * Request cards ids.
	 * 
	 * @param player
	 *            the player
	 * @param gameId
	 *            the game id
	 * @return the vector
	 */
	public Vector<String> requestCardsIds(Player player, String gameId) {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetCardsIds, gameId));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(StringResources.reqSent);
		Vector<String> list = null;
		try {
			Request obj = (Request) streamFromServer.readObject();
			list = obj.getCastedContent();
			System.out.println(StringResources.SomethingRec.toString() + obj.getRequestType() + " " + obj.getRequestAction());
			if (list == null) {
				list = new Vector<String>();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return list;
	}

	/**
	 * Request turn player.
	 * 
	 * @param player
	 *            the player
	 * @param gameId
	 *            the game id
	 * @return the player
	 */
	public Player requestTurnPlayer(Player player, String gameId) {
		connect();
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GetTurnPlayer, gameId));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(StringResources.reqSent);
		try {
			Request obj = (Request) streamFromServer.readObject();
			player = obj.getCastedContent();
			System.out.println(StringResources.SomethingRec.toString() + obj.getRequestType() + " " + obj.getRequestAction());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
		return player;
	}

	/**
	 * Request turn timeout.
	 * 
	 * @param player
	 *            the player
	 * @param gameId
	 *            the game id
	 */
	public void requestTurnTimeout(Player player, String gameId) {
		connect();
		GameRequest gameRequest = new GameRequest(gameId, GameRequestAction.PlayerTurnTimeout);
		gameRequest.setPlayer(player);
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GameRequest, gameRequest));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(StringResources.reqSent);
		closeConnection();
	}

	/**
	 * Request to leave game.
	 * 
	 * @param player
	 *            the player
	 * @param gameId
	 *            the game id
	 */
	public void requestToLeaveGame(Player player, String gameId) {
		connect();
		GameRequest gameRequest = new GameRequest(gameId, GameRequestAction.PlayerLeaveGame);
		gameRequest.setPlayer(player);
		try {
			streamToServer.reset();
			streamToServer.writeObject(new Request(player, RequestAction.Ask, RequestType.GameRequest, gameRequest));
			streamToServer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(StringResources.reqSent);
		closeConnection();
	}
}
