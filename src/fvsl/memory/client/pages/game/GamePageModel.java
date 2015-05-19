package fvsl.memory.client.pages.game;

import java.util.Vector;

import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.CardButton;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;

public class GamePageModel {

	private Lobby lobby;
	private Player turnPlayer;
	private Vector<Card> cards;
	
	/**
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * @param lobby the lobby to set
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
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
	 * @return the cards
	 */
	public Vector<Card> getCards() {
		return cards;
	}

	/**
	 * @param cards the cards to set
	 */
	public void setCards(Vector<Card> cards) {
		this.cards = cards;
	}

	
}
