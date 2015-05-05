package fvsl.memory.client.ui;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class MainPage extends Page {
	private JTextField txtUsername;

	public MainPage(){
		super();
		setLayout(new FlowLayout()); //GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		JPanel createLobbyPanel = new JPanel();
		panel.add(createLobbyPanel);
		createLobbyPanel.setLayout(new BoxLayout(createLobbyPanel, BoxLayout.Y_AXIS));
		
	  
		
		txtUsername = new JTextField();
		createLobbyPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnCreateLobby = new JButton("Create Lobby");
		createLobbyPanel.add(btnCreateLobby);
		
		JPanel joinLobbyPanel = new JPanel();
		panel.add(joinLobbyPanel);
		joinLobbyPanel.setLayout(new BoxLayout(joinLobbyPanel, BoxLayout.Y_AXIS));
		
		JList listLobbies = new JList();
		joinLobbyPanel.add(listLobbies);
		
		JButton btnJoinLobby = new JButton("Join Lobby");
		joinLobbyPanel.add(btnJoinLobby);
	}
	
	@Override
	protected void loadComponents() {
	}

}
