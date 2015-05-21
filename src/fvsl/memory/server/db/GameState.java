package fvsl.memory.server.db;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import fvsl.memory.common.entities.*;
import fvsl.memory.common.util.Mapper;

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

	public Card getCardById(String cardId) {
		for (int i = 0; i < cards.size(); i++) {
			if (cardId.equals(cards.get(i).getId())) {
				return cards.get(i);
			}
		}
		return null;
	}

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
		
		if (totalCardsTurned == cards.size()){
			endGame();
		}
	}
	
	public void endGame(){
		setGameOver(true);
		System.out.println("GAME " + getId() + " is over!");
	}

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
	 * @return the cards
	 */
	public Vector<Card> getCards() {
		return cards;
	}

	/**
	 * @return the players
	 */
	public Vector<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the turnPlayer
	 */
	public Player getTurnPlayer() {
		return turnPlayer;
	}

	/**
	 * @param turnPlayer
	 *            the turnPlayer to set
	 */
	public void setTurnPlayer(Player turnPlayer) {
		this.turnPlayer = turnPlayer;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the turnNumber
	 */
	public synchronized int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * @return the cardsToBeFolded
	 */
	public Card[] getCardsToBeFolded() {
		return cardsToBeFolded;
	}

	/**
	 * @param cardsToBeFolded
	 *            the cardsToBeFolded to set
	 */
	public void setCardsToBeFolded(Card[] cardsToBeFolded) {
		this.cardsToBeFolded = cardsToBeFolded;
	}

	/**
	 * @return the isPerformingAction
	 */
	public synchronized Boolean isPerformingAction() {
		return isPerformingAction;
	}

	/**
	 * @param isPerformingAction
	 *            the isPerformingAction to set
	 */
	public synchronized void setPerformingAction(Boolean isPerformingAction) {
		this.isPerformingAction = isPerformingAction;
	}

	/**
	 * @return the isStarted
	 */
	public synchronized Boolean isStarted() {
		return isStarted;
	}

	/**
	 * @param isStarted the isStarted to set
	 */
	public synchronized void setStarted(Boolean isStarted) {
		this.isStarted = isStarted;
	}

	/**
	 * @return the isGameOver
	 */
	public synchronized Boolean isGameOver() {
		return isGameOver;
	}

	/**
	 * @param isGameOver the isGameOver to set
	 */
	public synchronized void setGameOver(Boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
}
