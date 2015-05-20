package fvsl.memory.client.pages.game;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.GameRequest.GameRequestAction;
import fvsl.memory.common.entities.Lobby;

public class GamePageView extends Page {
	public GamePageView(Lobby lobby) {
		super(lobby);
	}

	private JTable playersTable;
	private Lobby bufferLobby;

	private JPanel containerPanel;
	private JPanel scorePanel;
	private JPanel cardsPanel;
	private JPanel tablePanel;

	private JLabel playerNameLabel;
	private JPanel playerNamePanel;

	private GamePageModel model;
	private GamePageController controller;

	private Vector<CardButton> buttons;

	@Override
	protected void bufferize(Object o) {
		bufferLobby = (Lobby) o;
	}

	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub

		playersTable = new JTable();
		tablePanel = new JPanel();

		containerPanel = new JPanel();
		add(containerPanel);
		containerPanel.setLayout(new BorderLayout());

		scorePanel = new JPanel();
		containerPanel.add(scorePanel, BorderLayout.WEST);
		cardsPanel = new JPanel();
		containerPanel.add(cardsPanel, BorderLayout.CENTER);

		playerNameLabel = new JLabel();
		playerNamePanel = new JPanel();
		playerNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
		playerNamePanel.setBorder((new TitledBorder(new EtchedBorder(), "Player")));
		playerNamePanel.add(playerNameLabel);
		tablePanel.setBorder((new TitledBorder(new EtchedBorder(), "Scoreboard")));
		tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		tablePanel.add(new JScrollPane(playersTable));
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
		scorePanel.add(playerNamePanel);
		scorePanel.add(Box.createVerticalGlue());
		scorePanel.add(tablePanel);
		scorePanel.add(Box.createVerticalGlue());

		// scorePanel.add(Box.createRigidArea(new Dimension(0,300)));

		int columns = 5;
		int rows = bufferLobby.getNumberOfPairs() * 2 / columns;

		cardsPanel.setLayout(new GridLayout(rows, columns, 5, 5));
		// cardsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		// cardsPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		buttons = new Vector<CardButton>();
		for (int i = 0; i < bufferLobby.getNumberOfPairs() * 2; i++) {
			CardButton button = new CardButton();
			buttons.add(button);
			// button.setPreferredSize(new Dimension(120, 120));
			button.setPreferredSize(new Dimension(500 / columns, 380 / rows));
			cardsPanel.add(button);

		}

	}

	@Override
	protected void setUpListeners() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardButtonPressed((CardButton) e.getSource());
			}
		};

		for (CardButton button : buttons) {
			button.addActionListener(listener);
		}

	}

	private void cardButtonPressed(CardButton button) {
		Card card = button.getCard();
		if (card.getId() != null) {
			// if
			// (Application.player.getName().equals(model.getTurnPlayer().getName()))
			controller.attemptToTurnCard(model.getLobby().getId(), card);
		}
	}

	@Override
	protected void loadData() {
		model = new GamePageModel();
		model.setLobby(bufferLobby);
		controller = new GamePageController();
		model.setCards(controller.getCardsFromServer(model.getLobby().getId()));
		model.setTurnPlayer(controller.getTurnPlayerFromServer(model.getLobby().getId()));
	}

	@Override
	protected void populateViews() {
		for (int i = 0; i < buttons.size(); i++) {
			if (i < model.getCards().size()) {
				buttons.get(i).setCard(model.getCards().get(i));
			}
		}
		playerNameLabel.setText(Application.player.getName());
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		playersTable.setModel(tableModel);
	}

	public void respondToGameRequest(GameRequest gameRequest) {
		if (gameRequest.getId().equals(model.getLobby().getId())) {
			if (gameRequest.getAction() == GameRequestAction.TurnCard) {
				Card card = gameRequest.getCard();
				getCardButtonByCardId(card.getId()).setCard(card);
			} else if (gameRequest.getAction() == GameRequestAction.FoldCard) {
				Card card = gameRequest.getCard();
				getCardButtonByCardId(card.getId()).setCard(new Card(card.getId(), null));
			} else if (gameRequest.getAction() == GameRequestAction.LosePlayerTurn) {
				Player nextPlayer = gameRequest.getNextPlayer();
				Integer playerPoints = gameRequest.getPlayerPoints();
				model.getTurnPlayer().setScore(playerPoints);
				model.setTurnPlayer(model.getLobby().getConnectedPlayerByName(nextPlayer.getName()));
				refreshTable();
			} else if (gameRequest.getAction() == GameRequestAction.WinPlayerTurn) {
				Integer playerPoints = gameRequest.getPlayerPoints();
				model.getTurnPlayer().setScore(playerPoints);
				refreshTable();
			}
		}
	}

	private void refreshTable() {
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		playersTable.setModel(tableModel);
	}

	private CardButton getCardButtonByCardId(String cardId) {
		for (CardButton cardButton : buttons) {
			if (cardButton.getCard().getId().equals(cardId)) {
				return cardButton;
			}
		}
		return null;
	}

	protected class PlayersTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		private Vector<Player> list = new Vector<Player>();

		private String[] columnNames = { "Player", "Score", "Turn" };

		public PlayersTableModel(Vector<Player> list) {
			this.list = list;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return list.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Player player = list.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return player.getName();
			case 1:
				return player.getScore();
			case 2:
				return model.getTurnPlayer().getName().equals(player.getName());
			}
			return null;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Integer.class;
			case 2:
				return Boolean.class;
			}
			return null;
		}
	}
}