package fvsl.memory.client.pages.lobby;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.entities.Request.StatusChangeResult;
import fvsl.memory.client.pages.Page;

import java.util.ArrayList;



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

	public LobbyPageView(Lobby lobby){
		super(lobby);	
	} 

	private JButton readyButton;
	private JButton backButton;
	private JLabel lobbyNameLabel;
	private JLabel numberOfPairsLabel;
	private JLabel timerLabel;
	private JTable playersTable;


	@Override
	protected void loadComponents() {

		setLayout(new GridLayout(1, 0, 0, 0));
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(1,1,0,50));

		JPanel pannelloSinistra=new JPanel();
		//pannelloSinistra.setLayout(new BoxLayout(pannelloSinistra, BoxLayout.Y_AXIS));
		pannello.add(pannelloSinistra);
		JPanel	pannelloDestra= new JPanel();
		//pannelloDestra.setLayout(new BoxLayout(pannelloDestra, BoxLayout.Y_AXIS));
		pannello.add(pannelloDestra);
		pannelloSinistra.setLayout(new GridLayout(5,1,25,25));
		pannelloDestra.setLayout(new GridLayout(8,2,0,0));
		pannelloDestra.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));


		readyButton= new JButton("PRONTO");
		backButton= new JButton("torna indietro");


		lobbyNameLabel = new JLabel();
		numberOfPairsLabel = new JLabel();
		timerLabel = new JLabel();

		pannelloSinistra.add(lobbyNameLabel);
		pannelloSinistra.add(numberOfPairsLabel);
		pannelloSinistra.add(timerLabel);
		pannelloSinistra.add(readyButton);

		playersTable = new JTable();
		pannelloDestra.add(playersTable);
		pannelloDestra.add(backButton);
	}

	@Override
	protected void setUpListeners() {
		readyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setStatusReady(model.getLobby());
			}
		});
		
		backButton.addActionListener(new ActionListener(){
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
		numberOfPairsLabel.setText(model.getLobby().getNumberOfPairs() + " pairs");	
		timerLabel.setText(model.getLobby().getTurnTimer() + " seconds.");
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		playersTable.setModel(tableModel);
	}

	@Override
	protected void bufferize(Object o) {
		lobbyBuffer = (Lobby)o;
	}
	
	public void updatePlayers(){
		model.getLobby().setConnectedPlayers(controller.getPlayersOfLobbyFromServer(model.getLobby()));
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		playersTable.setModel(tableModel);
	}

	protected class PlayersTableModel extends AbstractTableModel{
		private static final long serialVersionUID = 1L;

		private ArrayList<Player> list = new ArrayList<Player>();

		private String[] columnNames = { "Player", "Status"}; 

		public PlayersTableModel(ArrayList<Player> list){
			this.list = list;
		} 

		@Override 
		public String getColumnName(int columnIndex){
			return columnNames[columnIndex];
		} 

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			//return list.size();
			return model.getLobby().getNumberOfPlayers();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Player player = null;
			if (rowIndex > list.size()-1){
				player = new Player("~Free slot~");
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
		public Class<?> getColumnClass(int columnIndex){
			switch (columnIndex){
			case 0: 
				return String.class;
			case 1: 
				return Boolean.class;
			}
			return null;
		}
	}
}
