package fvsl.memory.client.pages.game;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Card;
import fvsl.memory.common.entities.GameRequest;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.GameRequest.GameRequestAction;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * The Class GamePageView.
 */
public class GamePageView extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2004601937124829532L;

	/**
	 * Instantiates a new game page view.
	 *
	 * @param lobby the lobby
	 */
	public GamePageView(Lobby lobby) {
		super(lobby);

		controller = new GamePageController();
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

	private int numberOfCardsTurnedInThisRound;

	private Vector<CardButton> buttons;

	private JLabel timerLabel;
	private volatile Long remaining;
	private final long interval = 10;
	private boolean timerRunning;
	private boolean timerPaused;

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
		JPanel timerAndCardsPanel = new JPanel();
		timerAndCardsPanel.setLayout(new BoxLayout(timerAndCardsPanel, BoxLayout.Y_AXIS));
		JPanel cardsPanelContainer = new JPanel(new FlowLayout());

		JPanel timerPanel = new JPanel();
		;
		// timerPanel.setLayout();
		timerLabel = new JLabel();
		timerLabel.setFont(new Font(StringResources.textStyle.toString(), Font.BOLD, 40));
		timerLabel.setText(StringResources.timerStart.toString());
		timerPanel.add(timerLabel);

		timerAndCardsPanel.add(timerPanel);
		cardsPanelContainer.add(cardsPanel);
		timerAndCardsPanel.add(cardsPanelContainer);
		containerPanel.add(timerAndCardsPanel, BorderLayout.CENTER);
		playerNameLabel = new JLabel();
		playerNamePanel = new JPanel();
		playerNameLabel.setFont(new Font(StringResources.textStyle.toString(), Font.BOLD, 24));
		playerNamePanel.setBorder((new TitledBorder(new EtchedBorder(), StringResources.player.toString())));
		playerNamePanel.add(playerNameLabel);
		tablePanel.setBorder((new TitledBorder(new EtchedBorder(), StringResources.scoreboard.toString())));
		tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		tablePanel.add(new JScrollPane(playersTable));
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
		scorePanel.add(playerNamePanel);
		scorePanel.add(Box.createVerticalGlue());
		scorePanel.add(tablePanel);
		scorePanel.add(Box.createVerticalGlue());
		// scorePanel.add(Box.createRigidArea(new Dimension(0,300)));

		int columns = 5;
		int rows = bufferLobby.getNumberOfPairs() * 2 / columns + 1;

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

	private void turnTimeout() {
		controller.reportTurnTimeout(model.getLobby().getId());
	}

	@Override
	protected void loadData() {
		model = new GamePageModel();
		model.setLobby(bufferLobby);
		model.setCards(controller.getCardsFromServer(model.getLobby().getId()));
		model.setTurnPlayer(controller.getTurnPlayerFromServer(model.getLobby().getId()));
		remaining = 0L;
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

	private void startTimer() {
		long duration = (long) (model.getLobby().getTurnTimer() * 1000);
		synchronized (remaining) {
			remaining = duration;
		}
		timerRunning = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (timerRunning) {
					if (!timerPaused) {
						synchronized (remaining) {

							remaining -= interval;
							if (remaining < 0) {
								remaining = 0L;
								pauseTimer();
								SwingUtilities.invokeLater(new Runnable() {

									@Override
									public void run() {
										turnTimeout();
									}
								});
							}

							// int minutes = (int)(remaining/60000);
						}
						final int seconds = (int) ((remaining) / 1000);
						final int decimal = (int) (((remaining) % 1000) / 10);
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								timerLabel.setText(String.format("%02d", seconds) + ":" + String.format("%02d", decimal));
							}
						});

						try {
							Thread.sleep(interval);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	private void refreshTimer() {
		timerPaused = false;
		synchronized (remaining) {
			remaining = (long) (model.getLobby().getTurnTimer() * 1000);
		}
	}

	private void stopTimer() {
		timerRunning = false;
	}

	private void pauseTimer() {
		timerPaused = true;
	}

	/**
	 * Respond to game request.
	 *
	 * @param gameRequest the game request
	 */
	public void respondToGameRequest(GameRequest gameRequest) {
		if (gameRequest.getId().equals(model.getLobby().getId())) {
			if (gameRequest.getAction() == GameRequestAction.TurnCard) {
				Card card = gameRequest.getCard();
				getCardButtonByCardId(card.getId()).setCard(card);
				numberOfCardsTurnedInThisRound++;
				if (numberOfCardsTurnedInThisRound >= 2) {
					pauseTimer();
				}
			} else if (gameRequest.getAction() == GameRequestAction.FoldCard) {
				Card card = gameRequest.getCard();
				getCardButtonByCardId(card.getId()).setCard(new Card(card.getId(), null));
			} else if (gameRequest.getAction() == GameRequestAction.LosePlayerTurn) {
				numberOfCardsTurnedInThisRound = 0;
				Player nextPlayer = gameRequest.getNextPlayer();
				Integer playerPoints = gameRequest.getPlayerPoints();
				model.getLobby().getConnectedPlayerByName(gameRequest.getPlayer().getName()).setScore(playerPoints);
				model.setTurnPlayer(model.getLobby().getConnectedPlayerByName(nextPlayer.getName()));
				refreshTable();
				refreshTimer();
			} else if (gameRequest.getAction() == GameRequestAction.WinPlayerTurn) {
				numberOfCardsTurnedInThisRound = 0;
				Integer playerPoints = gameRequest.getPlayerPoints();
				model.getLobby().getConnectedPlayerByName(gameRequest.getPlayer().getName()).setScore(playerPoints);
				refreshTable();
				refreshTimer();
			} else if (gameRequest.getAction() == GameRequestAction.PlayerLeaveGame) {
				model.getLobby().getConnectedPlayers().remove(model.getLobby().getConnectedPlayerByName(gameRequest.getPlayer().getName()));
				refreshTable();
			}
		}
	}

	/**
	 * Start game.
	 *
	 * @param lobby the lobby
	 */
	public void startGame(Lobby lobby) {
		if (lobby.getId().equals(model.getLobby().getId())) {
			startTimer();
		}
	}

	/**
	 * End game.
	 *
	 * @param id the id
	 */
	public void endGame(String id) {
		if (id.equals(model.getLobby().getId())) {
			stopTimer();
			controller.goToScoreboardPage(model.getLobby());
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
		private String[] columnNames = { StringResources.player.toString(), StringResources.score.toString(), StringResources.turn.toString() };

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

	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	public GamePageController getController() {
		return controller;
	}

	/**
	 * Sets the controller.
	 *
	 * @param controller the new controller
	 */
	public void setController(GamePageController controller) {
		this.controller = controller;
	}

	@Override
	protected void onExit() {
		if (model.getTurnPlayer().getName().equals(Application.player.getName())) {
			controller.reportTurnTimeout(model.getLobby().getId());
		}
		controller.leaveGame(model.getLobby().getId());
	}

}