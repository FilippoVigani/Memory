package fvsl.memory.server.db;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import fvsl.memory.common.entities.*;

public class Game {
	
	private Random rng;
	
	private String id;
	private volatile Vector<Card> cards;
	private volatile Vector<Player> players;
	private volatile Hashtable<Player, Integer> score;
	private volatile Hashtable<Player, Integer> guessStreak;
	
	private volatile int turnNumber;
	
	private volatile Card[] turnedCards;
	
	private volatile Player turnPlayer;
	
	public Game(Lobby lobby){
		this.id = lobby.getId();
		turnNumber = 0;
		rng = new Random(System.nanoTime());
		players = lobby.getConnectedPlayers();
		score = new Hashtable<Player, Integer>();
		guessStreak = new Hashtable<Player, Integer>();
		cards = new Vector<Card>();
		
		turnedCards = new Card[2];
		
		ArrayList<String> cardValues = new ArrayList<String>(Mapper.getMapper().getCardsMap().keySet());
		
		for (int i = 0; i < lobby.getNumberOfPairs() && cardValues.size() > 0; i++){
			String cardValue = cardValues.get(rng.nextInt(cardValues.size()+1));
			
			cards.insertElementAt((new Card(UUID.randomUUID().toString(),cardValue)), rng.nextInt(cards.size()+1));
			cards.insertElementAt((new Card(UUID.randomUUID().toString(),cardValue)), rng.nextInt(cards.size()+1));
			cardValues.remove(cardValue);
		}
		
		for (Player player : players){
			score.put(player, 0);
			guessStreak.put(player, 0);
		}
		
		turnPlayer = players.get(rng.nextInt(players.size()));
	}
	
	//returns the card value
	public Card turnCard(String cardId){
		Card card = getCardById(cardId);
		card.setTurned(true);
		if (turnedCards[0] == null){
			turnedCards[0] = card;
		} else {
			turnedCards[1] = card;
			endTurn();
		}
		
		return card;
	}
	
	public Card getCardById(String cardId){
		for (int i = 0; i < cards.size(); i++){
			if (cardId.equals(cards.get(i).getId())){
				return cards.get(i);
			}
		}
		return null;
	}
	
	public void endTurn(){
		if (turnedCards[0] != null && turnedCards[1] != null && 
				turnedCards[0].getValue().equals(turnedCards[1].getValue())){
			//Adding points (could be more complicated)
			guessStreak.put(turnPlayer, guessStreak.get(turnPlayer) + 1);
			score.put(turnPlayer, score.get(turnPlayer) + guessStreak.get(turnPlayer));
		} else {
			guessStreak.put(turnPlayer, 0);
			if (turnedCards[0] != null){
				turnedCards[0].setTurned(false);
			}
			if (turnedCards[1] != null){
				turnedCards[1].setTurned(false);
			}
		}
		
		turnedCards = new Card[0];
		
		turnPlayer = players.get((players.indexOf(turnPlayer)+1) % players.size());
		
		turnNumber++;
	}
	
	public Integer getPlayerPoints(Player player){
		return score.get(getPlayerByName(player.getName()));
	}

	private Player getPlayerByName(String name) {
		for (Player player : players){
			if (player.getName().equals(name)){
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
	 * @param turnPlayer the turnPlayer to set
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
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * @param turnNumber the turnNumber to set
	 */
	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
}
