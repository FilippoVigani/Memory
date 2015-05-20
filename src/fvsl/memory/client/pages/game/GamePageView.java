package fvsl.memory.client.pages.game;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.GameRequest;
import fvsl.memory.common.entities.GameRequest.GameRequestAction;
import fvsl.memory.common.entities.Lobby;

public class GamePageView extends Page {
	public GamePageView(Lobby lobby){
		super(lobby);
	}

	private JTable turnTable;
	private Lobby bufferLobby;
	private JPanel cardsPanel;

	private GamePageModel model;
	private GamePageController controller;

	private Vector<CardButton> buttons;

	@Override
	protected void bufferize(Object o) {
		bufferLobby = (Lobby)o;
	}
	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub

		boolean turnButton=true;
		String[] colunmNames= {"giocatore","punteggio","pronto"};
		Object[][] data={
				{"stefano", "100",turnButton},
				{"io", "100",turnButton},
				{"podf", "100",turnButton}
		};
		JTable turnTable = new JTable(data,colunmNames);
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new BorderLayout());
		JPanel scorePanel=new JPanel();
		pannello.add(scorePanel,BorderLayout.WEST);
		cardsPanel= new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
		pannello.add(cardsPanel,BorderLayout.CENTER);
		scorePanel.add(Box.createRigidArea(new Dimension(0,210)));
		scorePanel.add(turnTable);
		scorePanel.add(Box.createRigidArea(new Dimension(0,300)));
		
		int columns = 5;
		int rows = bufferLobby.getNumberOfPairs()*2/columns;
		
		cardsPanel.setLayout(new GridLayout(rows, columns, 5, 5));
		//cardsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//cardsPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		buttons = new Vector<CardButton>();
		for(int i=0;i<bufferLobby.getNumberOfPairs()*2;i++){
			CardButton button = new CardButton();
			buttons.add(button);
			//button.setPreferredSize(new Dimension(120, 120));
			button.setPreferredSize(new Dimension(500/columns, 400/rows));
			cardsPanel.add(button);
			
		}

	}


	@Override
	protected void setUpListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardButtonPressed((CardButton)e.getSource());
			}
		};

		for (CardButton button : buttons){
			button.addActionListener(listener);
		}

	}

	private void cardButtonPressed(CardButton button){
		Card card = button.getCard();
		if (card.getId() != null){
			//if (Application.player.getName().equals(model.getTurnPlayer().getName()))
			controller.attemptToTurnCard(model.getLobby().getId(), card);
		}
	}

	@Override
	protected void loadData() {
		model = new GamePageModel();
		model.setLobby(bufferLobby);
		controller= new GamePageController();
		model.setCards(controller.getCardsFromServer(model.getLobby().getId()));
		model.setTurnPlayer(controller.getTurnPlayerFromServer(model.getLobby().getId()));
	}

	@Override
	protected void populateViews() {
		for (int i = 0; i < buttons.size(); i++){
			if (i < model.getCards().size()){
				buttons.get(i).setCard(model.getCards().get(i));
			}
		}
	}


	public void respondToGameRequest(GameRequest gameRequest) {
		if (gameRequest.getId().equals(model.getLobby().getId())){
			if (gameRequest.getAction() == GameRequestAction.TurnCard){
				Card card = gameRequest.getCard();
				getCardButtonByCardId(card.getId()).setCard(card);
			} else if (gameRequest.getAction() == GameRequestAction.FoldCard){
				Card card = gameRequest.getCard();
				getCardButtonByCardId(card.getId()).setCard(new Card(card.getId(), null));
			}
		}
	}
	
	private CardButton getCardButtonByCardId(String cardId){
		for (CardButton cardButton : buttons){
			if (cardButton.getCard().getId().equals(cardId)){
				return cardButton;
			}
		}
		return null;
	}

}