package fvsl.memory.client.pages.game;

import java.util.Vector;

import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class GamePageController.
 */
public class GamePageController extends PageListeners {

	/**
	 * Gets the cards from server.
	 * 
	 * @param gameId
	 *            the game id
	 * @return the cards from server
	 */
	public Vector<Card> getCardsFromServer(String gameId) {
		Vector<Card> cards = new Vector<Card>();
		for (String id : Application.getServerManager().requestCardsIds(Application.player, gameId)) {
			cards.add(new Card(id, null));
		}
		return cards;
	}

	/**
	 * Attempt to turn card.
	 * 
	 * @param gameId
	 *            the game id
	 * @param card
	 *            the card
	 */
	public void attemptToTurnCard(String gameId, Card card) {
		try {
			Application.getServerManager().requestToTurnCard(Application.player, gameId, card);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the turn player from server.
	 * 
	 * @param gameId
	 *            the game id
	 * @return the turn player from server
	 */
	public Player getTurnPlayerFromServer(String gameId) {
		return Application.getServerManager().requestTurnPlayer(Application.player, gameId);
	}

	/**
	 * Report turn timeout.
	 * 
	 * @param gameId
	 *            the game id
	 */
	public void reportTurnTimeout(String gameId) {
		Application.getServerManager().requestTurnTimeout(Application.player, gameId);
	}

	/**
	 * Go to scoreboard page.
	 * 
	 * @param lobby
	 *            the lobby
	 */
	public void goToScoreboardPage(Lobby lobby) {
		fireGoToScoreboardEvent(lobby);
	}

	/**
	 * Leave game.
	 * 
	 * @param gameId
	 *            the game id
	 */
	public void leaveGame(String gameId) {
		Application.getServerManager().requestToLeaveGame(Application.player, gameId);
	}
}
