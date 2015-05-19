package fvsl.memory.client.pages.game;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.CardButton;
import fvsl.memory.common.entities.GameRequest;
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
	private boolean turnButton=true;
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
		JPanel pannelloSinistra=new JPanel();
		pannello.add(pannelloSinistra,BorderLayout.WEST);
		cardsPanel= new JPanel();
		pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.PAGE_AXIS));
		pannello.add(cardsPanel,BorderLayout.CENTER);
		pannelloSinistra.add(Box.createRigidArea(new Dimension(0,210)));
		pannelloSinistra.add(turnTable);
		pannelloSinistra.add(Box.createRigidArea(new Dimension(0,300)));
		cardsPanel.setLayout(new GridLayout(0,5,10,5));
		//cardsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//cardsPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		buttons = new Vector<CardButton>();
		for(int i=0;i<model.getLobby().getNumberOfPairs()*2;i++){
			CardButton button = new CardButton();
			buttons.add(button);
			cardsPanel.add(button);
		}

	}


	@Override
	protected void setUpListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadData() {
		model = new GamePageModel();
		model.setLobby(bufferLobby);
		controller= new GamePageController();
		model.setCards(controller.getCardsFromServer(Application.player, model.getLobby().getId()));
	}

	@Override
	protected void populateViews() {
		for (int i = 0; i < buttons.size(); i++){
			buttons.get(i).setCard(model.getCards().get(i));
		}
	}


	public void respondToGameRequest(GameRequest gameRequest) {
		// TODO Auto-generated method stub

	}

}