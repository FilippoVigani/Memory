package fvsl.memory.client.pages.lobby;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import fvsl.memory.client.pages.Page;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.util.StringResources;

import java.util.Vector;

public class LobbyPageView extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8017492250670854298L;
	private LobbyPageModel model;

	public LobbyPageController getController() {
		return controller;
	}

	public void setController(LobbyPageController controller) {
		this.controller = controller;
	}

	private LobbyPageController controller;
	private Lobby lobbyBuffer;

	public LobbyPageView(Lobby lobby) {
		super(lobby);
	}

	private JButton readyButton;
	private JButton backButton;
	private JLabel lobbyNameLabel;
	private JLabel numberOfPairsLabel;
	private JLabel timerLabel;
	private JTable playersTable;
	private JPanel tablePanel;
	private JPanel playersPanel;
	
	@Override
	protected void loadComponents() {

		setLayout(new GridLayout(1, 0, 0, 0));
		JPanel pannello = new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(1, 1, 10, 10));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		pannello.add(infoPanel);
		playersPanel = new JPanel();
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));
		pannello.add(playersPanel);
		playersPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		readyButton = new JButton("Ready");
		backButton = new JButton("Go Back");

		lobbyNameLabel = new JLabel();
		lobbyNameLabel.setFont(new Font(StringResources.textStyle.toString(), Font.BOLD, 24));
		numberOfPairsLabel = new JLabel();
		numberOfPairsLabel.setFont(new Font(StringResources.textStyle.toString(), Font.BOLD, 20));
		timerLabel = new JLabel();
		timerLabel.setFont(new Font(StringResources.textStyle.toString(), Font.BOLD, 20));
		//infoPanel.add(Box.createVerticalGlue());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder((new TitledBorder(new EtchedBorder(),StringResources.name.toString())));
		panel.add(Box.createHorizontalGlue());
		panel.add(lobbyNameLabel);
		panel.add(Box.createHorizontalGlue());
		infoPanel.add(panel);
		//infoPanel.add(Box.createVerticalGlue());
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder((new TitledBorder(new EtchedBorder(), StringResources.coupleNum.toString())));
		panel.add(Box.createHorizontalGlue());
		panel.add(numberOfPairsLabel);
		panel.add(Box.createHorizontalGlue());
		infoPanel.add(panel);
		//infoPanel.add(Box.createVerticalGlue());
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder((new TitledBorder(new EtchedBorder(), StringResources.timer.toString())));
		panel.add(Box.createHorizontalGlue());
		panel.add(timerLabel);
		panel.add(Box.createHorizontalGlue());
		infoPanel.add(panel);
		infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(backButton);
		infoPanel.add(Box.createVerticalGlue());

		infoPanel.setBorder((new TitledBorder(new EtchedBorder(), StringResources.infoLo.toString())));
		
		playersTable = new JTable();
		tablePanel = new JPanel();
		
		//playersPanel.add(Box.createRigidArea(new Dimension(55, 100)));
		tablePanel.setBorder((new TitledBorder(new EtchedBorder(), StringResources.connectedPl.toString())));
		tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		tablePanel.add(new JScrollPane(playersTable));
		tablePanel.add(readyButton);
		playersPanel.add(tablePanel);
		tablePanel.add(Box.createVerticalGlue());
		//playersPanel.add(Box.createRigidArea(new Dimension(55, 60)));
	}

	@Override
	protected void setUpListeners() {
		readyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setStatusReady(model.getLobby());
				readyButton.setEnabled(false);
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.backToMainPage(model.getLobby());

			}
		});
	}

	@Override
	protected void loadData() {
		controller = new LobbyPageController();
		model = new LobbyPageModel();
		model.setLobby(lobbyBuffer);
		model.getLobby().setConnectedPlayers(controller.getPlayersOfLobbyFromServer(model.getLobby()));
	}

	@Override
	protected void populateViews() {
		lobbyNameLabel.setText(model.getLobby().getName());
		numberOfPairsLabel.setText(model.getLobby().getNumberOfPairs() + StringResources.pairs.toString());
		timerLabel.setText(model.getLobby().getTurnTimer() + StringResources.sec.toString());
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		playersTable.setModel(tableModel);
	}

	@Override
	protected void bufferize(Object o) {
		lobbyBuffer = (Lobby) o;
	}

	public void updatePlayers() {
		model.getLobby().setConnectedPlayers(controller.getPlayersOfLobbyFromServer(model.getLobby()));
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		playersTable.setModel(tableModel);
	}

	public void respondToDeletedLobby(Lobby lobby) {
		if (model.getLobby().getId().equals(lobby.getId())) {
			controller.backToMainPage(model.getLobby());
		}
	}

	public void respondToStartGame(Lobby lobby) {
		if (model.getLobby().getId().equals(lobby.getId())) {
			controller.goToGamePage(model.getLobby());
		}
	}

	protected class PlayersTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		private Vector<Player> list = new Vector<Player>();

		private String[] columnNames = { StringResources.player.toString(), StringResources.rdy.toString() };

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
			// return list.size();
			return model.getLobby().getNumberOfPlayers();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Player player = null;
			if (rowIndex > list.size() - 1) {
				player = new Player(StringResources.freeSlot.toString());
			} else {
				player = list.get(rowIndex);
			}
			switch (columnIndex) {
			case 0:
				return player.getName();
			case 1:
				return player.isReady();
			}
			return null;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			}
			return null;
		}
	}
}
