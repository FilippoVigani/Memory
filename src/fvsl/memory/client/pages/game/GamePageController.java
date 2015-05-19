package fvsl.memory.client.pages.game;

import java.util.Vector;

import fvsl.memory.client.pages.PageListeners;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;

public class GamePageController extends PageListeners{

	public Vector<Card> getCardsFromServer(Player player, String gameId){
		Vector<Card> cards = new Vector<Card>();
		for (String id : Application.getServerManager().getCardsIds(player, gameId)){
			cards.add(new Card(id, null));
		}
		return cards;
	}

	public void attemptToTurnCard(String gameId, Card card) {
		try {
			Application.getServerManager().requestToTurnCard(Application.player, gameId, card);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
