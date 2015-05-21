package fvsl.memory.server.db;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import fvsl.memory.common.entities.*;
import fvsl.memory.common.util.Mapper;

// TODO: Auto-generated Javadoc
/**
 * The Class GameState.
 */
public class GameState {

	private Random rng;

	private String id;
	private volatile Vector<Card> cards;
	private volatile Vector<Player> players;
	private volatile Hashtable<Player, Integer> score;
	private volatile Hashtable<Player, Integer> guessStreak;

	private Integer turnNumber;
	private Integer totalCardsTurned;
	private volatile Boolean isPerformingAction;
	private volatile Boolean isStarted;
	private volatile Boolean isGameOver;

	private volatile Card[] turnedCards;
	private volatile Card[] cardsToBeFolded;

	private volatile Player turnPlayer;

	/**
	 * Instantiates a new game state.
	 * 
	 * @param lobby
	 *            the lobby
	 */
	public GameState(Lobby lobby) {
		setPerformingAction(false);
		setStarted(false);
		setGameOver(false);
		this.id = lobby.getId();
		turnNumber = 0;
		totalCardsTurned = 0;
		rng = new Random(System.nanoTime());
		players = lobby.getConnectedPlayers();
		score = new Hashtable<Player, Integer>();
		guessStreak = new Hashtable<Player, Integer>();
		cards = new Vector<Card>();

		turnedCards = new Card[2];

		ArrayList<String> cardValues = new ArrayList<String>(Mapper.getMapper().getCardsMap().keySet());

		for (int i = 0; i < lobby.getNumberOfPairs() && cardValues.size() > 0; i++) {
			String cardValue = cardValues.get(rng.nextInt(cardValues.size()));

			cards.insertElementAt((new Card(UUID.randomUUID().toString(), cardValue)), rng.nextInt(cards.size() + 1));
			cards.insertElementAt((new Card(UUID.randomUUID().toString(), cardValue)), rng.nextInt(cards.size() + 1));
			cardValues.remove(cardValue);
		}

		for (Player player : players) {
			score.put(player, 0);
			guessStreak.put(player, 0);
		}

		turnPlayer = players.get(rng.nextInt(players.size()));
	}

	// returns the card value
	/**
	 * Turn card.
	 * 
	 * @param cardId
	 *            the card id
	 * @return the card
	 */
	public Card turnCard(String cardId) {
		Card card = getCardById(cardId);
		card.setTurned(true);
		totalCardsTurned++;

		System.out.println("GAME " + getId() + " - " + turnPlayer.getName() + " turned card " + cardId + "(" + card.getValue() + ")");

		if (turnedCards[0] == null) {
			turnedCards[0] = card;
		} else {
			turnedCards[1] = card;
			endTurn();
		}

		return card;
	}

	/**
	 * Gets the card by id.
	 * 
	 * @param cardId
	 *            the card id
	 * @return the card by id
	 */
	public Card getCardById(String cardId) {
		for (int i = 0; i < cards.size(); i++) {
			if (cardId.equals(cards.get(i).getId())) {
				return cards.get(i);
			}
		}
		return null;
	}

	/**
	 * End turn.
	 */
	public void endTurn() {
		boolean playerWonTurn = turnedCards[0] != null && turnedCards[1] != null && turnedCards[0].getValue().equals(turnedCards[1].getValue());

		if (playerWonTurn) { // Player won turn
			System.out.println("GAME " + getId() + " - " + turnPlayer.getName() + " WON turn " + turnNumber);
			guessStreak.put(turnPlayer, guessStreak.get(turnPlayer) + 1);
			score.put(turnPlayer, score.get(turnPlayer) + guessStreak.get(turnPlayer));
		} else {
			guessStreak.put(turnPlayer, 0);
			if (turnedCards[0] != null) {
				turnedCards[0].setTurned(false);
				totalCardsTurned--;
			}
			if (turnedCards[1] != null) {
				turnedCards[1].setTurned(false);
				totalCardsTurned--;
			}
		}

		cardsToBeFolded = turnedCards;
		turnedCards = new Card[2];

		System.out.println("GAME " + getId() + " - " + turnPlayer.getName() + " ended turn " + turnNumber);

		if (!playerWonTurn) {
			turnPlayer = players.get((players.indexOf(turnPlayer) + 1) % players.size());
		}

		synchronized (turnNumber) {
			turnNumber = turnNumber + 1;
		}

		if (totalCardsTurned == cards.size()) {
			endGame();
		}
	}

	/**
	 * End game.
	 */
	public void endGame() {
		setGameOver(true);
		System.out.println("GAME " + getId() + " is over!");
	}

	/**
	 * Gets the player points.
	 * 
	 * @param player
	 *            the player
	 * @return the player points
	 */
	public Integer getPlayerPoints(Player player) {
		return score.get(getPlayerByName(player.getName()));
	}

	private Player getPlayerByName(String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Kick player.
	 * 
	 * @param player
	 *            the player
	 */
	public void kickPlayer(Player player) {
		players.remove(getPlayerByName(player.getName()));
		if (players.size() <= 1) {
			endGame();
		}
	}

	/**
	 * Gets the cards.
	 * 
	 * @return the cards
	 */
	public Vector<Card> getCards() {
		return cards;
	}

	/**
	 * Gets the players.
	 * 
	 * @return the players
	 */
	public Vector<Player> getPlayers() {
		return players;
	}

	/**
	 * Gets the turn player.
	 * 
	 * @return the turn player
	 */
	public Player getTurnPlayer() {
		return turnPlayer;
	}

	/**
	 * Sets the turn player.
	 * 
	 * @param turnPlayer
	 *            the new turn player
	 */
	public void setTurnPlayer(Player turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the turn number.
	 * 
	 * @return the turn number
	 */
	public synchronized int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * Gets the cards to be folded.
	 * 
	 * @return the cards to be folded
	 */
	public Card[] getCardsToBeFolded() {
		return cardsToBeFolded;
	}

	/**
	 * Sets the cards to be folded.
	 * 
	 * @param cardsToBeFolded
	 *            the new cards to be folded
	 */
	public void setCardsToBeFolded(Card[] cardsToBeFolded) {
		this.cardsToBeFolded = cardsToBeFolded;
	}

	/**
	 * Checks if is performing action.
	 * 
	 * @return the boolean
	 */
	public synchronized Boolean isPerformingAction() {
		return isPerformingAction;
	}

	/**
	 * Sets the performing action.
	 * 
	 * @param isPerformingAction
	 *            the new performing action
	 */
	public synchronized void setPerformingAction(Boolean isPerformingAction) {
		this.isPerformingAction = isPerformingAction;
	}

	/**
	 * Checks if is started.
	 * 
	 * @return the boolean
	 */
	public synchronized Boolean isStarted() {
		return isStarted;
	}

	/**
	 * Sets the started.
	 * 
	 * @param isStarted
	 *            the new started
	 */
	public synchronized void setStarted(Boolean isStarted) {
		this.isStarted = isStarted;
	}

	/**
	 * Checks if is game over.
	 * 
	 * @return the boolean
	 */
	public synchronized Boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * Sets the game over.
	 * 
	 * @param isGameOver
	 *            the new game over
	 */
	public synchronized void setGameOver(Boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
}
