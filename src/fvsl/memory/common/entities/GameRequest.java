package fvsl.memory.common.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GameRequest. Represents a two-way request within the context of a game
 */
/**
 * @author Filippo Vigani
 *
 */
public class GameRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5323430997124137633L;

	/**
	 * Id of the game (or lobby)
	 */
	private String id;
	private Player player;
	private Player nextPlayer;
	private Card card;
	private Integer playerPoints;
	private GameRequestAction action;

	/**
	 * The Action representing the request
	 */
	public enum GameRequestAction {

		/** A card has been turned. */
		TurnCard,
		/** A player lost the turn. */
		LosePlayerTurn,
		/** A player won the turn. */
		WinPlayerTurn,
		/** A card has to be folded. */
		FoldCard,
		/** A player didn't turn two cards in time. */
		PlayerTurnTimeout,
		/** A player should leave the game. */
		PlayerLeaveGame
	}

	/**
	 * Instantiates a new game request.
	 * 
	 * @param id
	 *            the id
	 * @param action
	 *            the action
	 */
	public GameRequest(String id, GameRequestAction action) {
		this.id = id;
		this.action = action;
	}

	/**
	 * Gets the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player.
	 * 
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the next player.
	 * 
	 * @return the next player
	 */
	public Player getNextPlayer() {
		return nextPlayer;
	}

	/**
	 * Sets the next player.
	 * 
	 * @param nextPlayer
	 *            the new next player
	 */
	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	/**
	 * Gets the card.
	 * 
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * Sets the card.
	 * 
	 * @param card
	 *            the new card
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * Gets the action.
	 * 
	 * @return the action
	 */
	public GameRequestAction getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 * 
	 * @param action
	 *            the new action
	 */
	public void setAction(GameRequestAction action) {
		this.action = action;
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
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the player points.
	 * 
	 * @return the player points
	 */
	public Integer getPlayerPoints() {
		return playerPoints;
	}

	/**
	 * Sets the player points.
	 * 
	 * @param playerPoints
	 *            the new player points
	 */
	public void setPlayerPoints(Integer playerPoints) {
		this.playerPoints = playerPoints;
	}
}
