package fvsl.memory.server.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import fvsl.memory.common.entities.GameRequest;
import fvsl.memory.common.entities.GameRequest.GameRequestAction;
import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;
import fvsl.memory.common.entities.Request.LobbyLeavingResult;
import fvsl.memory.common.entities.Request.RequestAction;
import fvsl.memory.common.entities.Request.RequestType;
import fvsl.memory.common.entities.Request.StatusChangeResult;
import fvsl.memory.common.util.StringResources;
import fvsl.memory.server.db.GameState;
import fvsl.memory.server.db.ServerData;

public class ClientRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;
	protected ObjectOutputStream streamToClient = null;
	protected ObjectInputStream streamFromClient = null;
	protected boolean isStopped;

	protected volatile ServerData serverData;

	public ClientRunnable(Socket clientSocket, String serverText, ServerData serverData) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.serverData = serverData;
	}

	@Override
	public void run() {

		while (!isStopped()) {
			// Opening streams
			try {
				streamToClient = new ObjectOutputStream(clientSocket.getOutputStream());

				System.out.println(StringResources.createdOS);

				streamFromClient = new ObjectInputStream(clientSocket.getInputStream());

				streamToClient.flush();

				System.out.println(StringResources.createdIS);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (!isStopped()) {

				try {
					Request request = null;
					try {
						// System.out.println("Waiting for request...");
						request = (Request) streamFromClient.readObject();

					} catch (ClassNotFoundException e) {
						// e.printStackTrace();
					} catch (EOFException e) {
						break;
					} catch (SocketException e) {
						System.out.println(StringResources.socketErr);
						clean();
						return;
					}

					System.out.print(StringResources.reqRec);

					// Processing request - DA MIGLIORARE
					Request reply = null;
					if (request != null) {
						if (request.getRequestAction() == RequestAction.Ask) {
							System.out.println(request.getRequestType());
							switch (request.getRequestType()) {
							case GetLobbies:
								reply = getLobbies(request);
								break;
							case JoinLobby:
								reply = joinLobby(request);
								break;
							case CreateLobby:
								reply = createLobby(request);
								break;
							case GetConnectedPlayers:
								reply = getConnectedPlayers(request);
								break;
							case SetPlayerStatusReady:
								reply = setPlayerStatusReady(request);
								break;
							case LeaveLobby:
								reply = leaveLobby(request);
								break;
							case GetCardsIds:
								reply = getCardsIds(request);
								break;
							case GetTurnPlayer:
								reply = getTurnPlayer(request);
								break;
							case GameRequest:
								gameRequest((GameRequest) request.getContent());
								clean();
								return;
							default:
								break;
							}
						}
					}

					// Returning results
					streamToClient.writeObject(reply);
					streamToClient.flush();
					System.out.println(StringResources.reqFf);
					clean();
					setStopped(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void gameRequest(GameRequest request) {

		GameState game = serverData.getGameById(request.getId());
		System.out.println(StringResources.gamePerf.toString() + game.isPerformingAction());
		if (!game.isPerformingAction() && game.isStarted()) {
			System.out.println("Game request: " + request.getAction());
			if (request.getAction() == GameRequestAction.TurnCard) {
				synchronized (game.getId()) {
					game.setPerformingAction(true);
					Player player = request.getPlayer();
					if (game.getTurnPlayer().getName().equals(player.getName())) { // It's
																					// the
																					// turn
																					// of
																					// the
																					// player,
																					// proceed
						Integer turnNumber = game.getTurnNumber();
						if (!game.getCardById(request.getCard().getId()).isTurned()) { // Card
																						// hasn't
																						// already
																						// been
																						// turned
							Card turnedCard = game.turnCard(request.getCard().getId());

							GameRequest reply = new GameRequest(game.getId(), GameRequestAction.TurnCard);
							reply.setPlayer(player);
							reply.setCard(turnedCard);

							notifyUpdate(RequestType.GameRequest, reply);

							if (game.getTurnNumber() != turnNumber) {

								// Attendo per permettere di visualizzare la
								// carta
								try {
									Thread.sleep(1500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								if (player.getName().equals(game.getTurnPlayer().getName())) { // Player
																								// won
																								// turn
									GameRequest playerWonTurnRequest = new GameRequest(game.getId(), GameRequestAction.WinPlayerTurn);
									playerWonTurnRequest.setPlayer(player);
									playerWonTurnRequest.setNextPlayer(game.getTurnPlayer());
									System.out.println("punt " + game.getPlayerPoints(player));
									playerWonTurnRequest.setPlayerPoints(game.getPlayerPoints(player));
									notifyUpdate(RequestType.GameRequest, playerWonTurnRequest);
								} else { // Player lost turn
									if (game.getCardsToBeFolded()[0] != null) {
										GameRequest foldCard1 = new GameRequest(game.getId(), GameRequestAction.FoldCard);
										foldCard1.setPlayer(player);
										foldCard1.setCard(game.getCardsToBeFolded()[0]);
										notifyUpdate(RequestType.GameRequest, foldCard1);
										if (game.getCardsToBeFolded()[1] != null) {
											GameRequest foldCard2 = new GameRequest(game.getId(), GameRequestAction.FoldCard);
											foldCard2.setPlayer(player);
											foldCard2.setCard(game.getCardsToBeFolded()[1]);
											notifyUpdate(RequestType.GameRequest, foldCard2);
										}
									}

									GameRequest playerLostTurnRequest = new GameRequest(game.getId(), GameRequestAction.LosePlayerTurn);
									playerLostTurnRequest.setPlayer(player);
									playerLostTurnRequest.setNextPlayer(game.getTurnPlayer());

									System.out.println("punt " + game.getPlayerPoints(player));
									playerLostTurnRequest.setPlayerPoints(game.getPlayerPoints(player));
									notifyUpdate(RequestType.GameRequest, playerLostTurnRequest);
								}
							}
						}
					}
					game.setPerformingAction(false);
				}
			} else if (request.getAction() == GameRequestAction.PlayerTurnTimeout) {
				synchronized (game.getId()) {
					game.setPerformingAction(true);
					Player player = request.getPlayer();
					if (game.getTurnPlayer().getName().equals(player.getName())) {
						game.endTurn();

						if (game.getCardsToBeFolded()[0] != null) {
							GameRequest foldCard1 = new GameRequest(game.getId(), GameRequestAction.FoldCard);
							foldCard1.setPlayer(player);
							foldCard1.setCard(game.getCardsToBeFolded()[0]);
							notifyUpdate(RequestType.GameRequest, foldCard1);
							if (game.getCardsToBeFolded()[1] != null) {
								GameRequest foldCard2 = new GameRequest(game.getId(), GameRequestAction.FoldCard);
								foldCard2.setPlayer(player);
								foldCard2.setCard(game.getCardsToBeFolded()[1]);
								notifyUpdate(RequestType.GameRequest, foldCard2);
							}
						}

						GameRequest playerLostTurnRequest = new GameRequest(game.getId(), GameRequestAction.LosePlayerTurn);
						playerLostTurnRequest.setPlayer(player);
						playerLostTurnRequest.setNextPlayer(game.getTurnPlayer());
						playerLostTurnRequest.setPlayerPoints(game.getPlayerPoints(player));
						notifyUpdate(RequestType.GameRequest, playerLostTurnRequest);
					}
					game.setPerformingAction(false);
				}
			} else if (request.getAction() == GameRequestAction.PlayerLeaveGame) {
				synchronized (game.getId()) {
					game.setPerformingAction(true);
					game.kickPlayer(request.getPlayer());
					GameRequest playerKicked = new GameRequest(game.getId(), GameRequestAction.PlayerLeaveGame);
					playerKicked.setPlayer(request.getPlayer());
					notifyUpdate(RequestType.GameRequest, playerKicked);
					game.setPerformingAction(false);
				}
			}
			if (game.isGameOver()) {
				notifyUpdate(RequestType.EndGame, game.getId());
			}
		}
	}

	private void clean() {
		try {
			streamFromClient.close();
			streamToClient.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Request getLobbies(Request request) {
		Request reply = new Request(RequestAction.Reply);
		synchronized (serverData.getLobbies()) {
			reply.setContent(serverData.getLobbies());
		}
		reply.setRequestType(RequestType.GetLobbies);
		return reply;
	}

	private Request joinLobby(Request request) {
		Request reply = new Request(RequestAction.Reply);
		ArrayList<Object> contents = request.getCastedContent();
		Player player = request.getPlayer();
		String lobbyID = ((Lobby) contents.get(0)).getId();
		Lobby lobby = serverData.getLobbyById(lobbyID);

		String password = (String) contents.get(1);

		// Checks and returns result
		if (lobby == null) {
			reply.setContent(LobbyJoiningResult.NotFound);
		} else {
			synchronized (serverData.getLobbies()) {
				if (player == null || player.getName() == null || player.getName().isEmpty() || lobby.getConnectedPlayerByName(player.getName()) != null) {
					reply.setContent(LobbyJoiningResult.UnacceptedUsername);
				} else if (!lobby.checkPassword(password)) {
					reply.setContent(LobbyJoiningResult.WrongPassword);
				} else if (lobby.getConnectedPlayers().size() >= lobby.getNumberOfPlayers()) {
					reply.setContent(LobbyJoiningResult.FullLobby);
				} else {
					reply.setContent(LobbyJoiningResult.Accepted);
					player.setReady(false);
					lobby.getConnectedPlayers().add(player);
				}
			}
		}
		notifyUpdate(RequestType.UpdatePlayersList);
		return reply;
	}

	private Request createLobby(Request request) {
		Request reply = new Request(RequestAction.Reply);
		ArrayList<Object> contents = request.getCastedContent();
		Player player = request.getPlayer();
		Lobby srcLobby = (Lobby) contents.get(0);
		String password = (String) contents.get(1);

		System.out.println(StringResources.contentTaken);

		String newId = UUID.randomUUID().toString();

		srcLobby.setId(newId);
		srcLobby.setPassword(password);
		srcLobby.setOwner(player);

		synchronized (serverData.getLobbies()) {
			serverData.getLobbies().add(srcLobby);
		}

		// }

		reply.setContent(newId);
		System.out.println(StringResources.createdLo.toString() + newId);

		notifyUpdate(RequestType.UpdateLobbyList);
		return reply;
	}

	private Request getConnectedPlayers(Request request) {
		Request reply = new Request(RequestAction.Reply);

		Lobby srcLobby = request.getCastedContent();
		String lobbyID = srcLobby.getId();
		Lobby lobby = serverData.getLobbyById(lobbyID);
		if (lobby != null) {
			Player player = lobby.getConnectedPlayerByName(request.getPlayer().getName());
			if (lobby != null && player != null) { // Restituisce la lista solo
													// se il player che la
													// richiede è connesso
				reply.setContent(lobby.getConnectedPlayers());
			}
		}
		return reply;
	}

	private Request setPlayerStatusReady(Request request) {
		Request reply = new Request(RequestAction.Reply);
		reply.setContent(StatusChangeResult.Failed);
		Player srcPlayer = request.getPlayer();
		Lobby srcLobby = request.getCastedContent();
		Lobby lobby = serverData.getLobbyById(srcLobby.getId());
		if (lobby != null) {
			Player player = lobby.getConnectedPlayerByName(srcPlayer.getName());
			if (player != null) {
				player.setReady(true);
				reply.setContent(StatusChangeResult.Accepted);
			}
		}
		if (checkIfAllAreReady(lobby)) {
			startGame(lobby);
		} else {
			notifyUpdate(RequestType.UpdatePlayersList);
		}
		return reply;
	}

	private void startGame(Lobby lobby) {
		synchronized (serverData.getGames()) {
			serverData.getGames().add(new GameState(lobby));
		}
		synchronized (serverData.getLobbies()) {
			serverData.getLobbies().remove(lobby);
		}
		notifyUpdate(RequestType.SetupGame, lobby);
		System.out.println(StringResources.setUpGame);

		final Lobby l = lobby;

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(StringResources.startGame);
				synchronized (serverData.getGameById(l.getId()).getId()) {
					serverData.getGameById(l.getId()).setStarted(true);
				}
				notifyUpdate(RequestType.StartGame, l);
			}
		}.start();

	}

	private boolean checkIfAllAreReady(Lobby lobby) {
		boolean allReady = lobby.getNumberOfPlayers() == lobby.getConnectedPlayers().size();
		for (int i = 0; allReady && i < lobby.getConnectedPlayers().size(); i++) {
			allReady = lobby.getConnectedPlayers().get(i).isReady();
		}
		return allReady;
	}

	private Request leaveLobby(Request request) {
		Request reply = new Request(RequestAction.Reply);
		reply.setContent(LobbyLeavingResult.Failed);
		Player srcPlayer = request.getPlayer();
		Lobby srcLobby = request.getCastedContent();
		Lobby lobby = serverData.getLobbyById(srcLobby.getId());
		if (lobby != null) {
			Player player = lobby.getConnectedPlayerByName(srcPlayer.getName());
			if (player != null) {
				synchronized (lobby) {
					lobby.getConnectedPlayers().remove(player);
				}
				reply.setContent(LobbyLeavingResult.Accepted);
				if (lobby.getOwner() != null) {
					if (player.getName().equals(lobby.getOwner().getName())) {
						System.out.println(StringResources.destroyedLo);
						synchronized (serverData.getLobbies()) {
							serverData.getLobbies().remove(lobby);
						}
						notifyUpdate(RequestType.UpdateLobbyList);
						notifyUpdate(RequestType.DeletedLobby, lobby);
					}
				}
			}
		} else {
			reply.setContent(LobbyLeavingResult.Accepted);
		}
		notifyUpdate(RequestType.UpdatePlayersList);
		return reply;
	}

	private Request getCardsIds(Request request) {
		Request reply = new Request(RequestAction.Reply);
		reply.setRequestType(RequestType.GetCardsIds);

		String gameId = request.getCastedContent();

		Vector<String> ids = new Vector<String>();

		for (Card card : serverData.getGameById(gameId).getCards()) {
			ids.add(card.getId());
		}

		reply.setContent(ids);
		return reply;
	}

	private Request getTurnPlayer(Request request) {
		Request reply = new Request(RequestAction.Reply);
		reply.setRequestType(RequestType.GetTurnPlayer);

		String gameId = request.getCastedContent();

		reply.setContent(serverData.getGameById(gameId).getTurnPlayer());

		return reply;
	}

	private void notifyUpdate(RequestType requestType) {
		notifyUpdate(requestType, null);
	}

	private void notifyUpdate(RequestType requestType, Object content) {
		for (ClientUpdaterRunnable runnable : serverData.getClientUpdaters()) {
			if (runnable == null) {
				serverData.getClientUpdaters().remove(runnable);
			} else {
				synchronized (runnable.getRequests()) {
					runnable.getRequests().add(new Request(null, RequestAction.Ask, requestType, content));
				}
			}
		}
	}

	/**
	 * @return the isStopped
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * @param isStopped
	 *            the isStopped to set
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

}
