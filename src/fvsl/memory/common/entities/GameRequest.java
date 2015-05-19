package fvsl.memory.common.entities;

import java.io.Serializable;

import fvsl.memory.common.entities.GameRequest.GameRequestAction;

public class GameRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5323430997124137633L;
	
	private String id;
	private Player player;
	private Player nextPlayer;
	private Card card;
	private Integer playerPoints;
	private GameRequestAction action;

	public enum GameRequestAction{
		TurnCard,
		LosePlayerTurn,
		WinPlayerTurn
		
	}

	public GameRequest(String id, GameRequestAction action) {
		this.id = id;
		this.action = action;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the nextPlayer
	 */
	public Player getNextPlayer() {
		return nextPlayer;
	}

	/**
	 * @param nextPlayer the nextPlayer to set
	 */
	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * @return the action
	 */
	public GameRequestAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(GameRequestAction action) {
		this.action = action;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the playerPoints
	 */
	public Integer getPlayerPoints() {
		return playerPoints;
	}

	/**
	 * @param playerPoints the playerPoints to set
	 */
	public void setPlayerPoints(Integer playerPoints) {
		this.playerPoints = playerPoints;
	}
}
