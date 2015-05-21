package fvsl.memory.client.pages.game;

import java.util.Vector;

import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class GamePageModel.
 */
public class GamePageModel {

	private Lobby lobby;
	private Player turnPlayer;
	private Vector<Card> cards;

	/**
	 * Gets the lobby.
	 *
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * Sets the lobby.
	 *
	 * @param lobby the new lobby
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
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
	 * @param turnPlayer the new turn player
	 */
	public void setTurnPlayer(Player turnPlayer) {
		this.turnPlayer = turnPlayer;
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
	 * Sets the cards.
	 *
	 * @param cards the new cards
	 */
	public void setCards(Vector<Card> cards) {
		this.cards = cards;
	}

	/**
	 * Gets the card by id.
	 *
	 * @param cardId the card id
	 * @return the card by id
	 */
	public Card getCardById(String cardId) {
		for (Card card : cards) {
			if (card.getId().equals(cardId)) {
				return card;
			}
		}
		return null;
	}
}
