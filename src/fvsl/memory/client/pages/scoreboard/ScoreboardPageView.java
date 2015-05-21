package fvsl.memory.client.pages.scoreboard;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import fvsl.memory.client.pages.Page;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.util.StringResources;

import java.awt.GridLayout;
import java.util.Vector;

public class ScoreboardPageView extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9192421170975614105L;

	public ScoreboardPageView(Lobby lobby) {
		super();
		
	}
	
	private Lobby bufferLobby;
	@Override
	protected void bufferize(Object o) {
		bufferLobby = (Lobby) o;
	}
	private JButton backButton;
	private JTable scoreTable;
	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		scoreTable=new JTable();
		panel.add(new JScrollPane(scoreTable));
		panel.add
		
	
	}

	@Override
	protected void setUpListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void populateViews() {
		// TODO Auto-generated method stub

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
			}
			return null;
		}
	}
}