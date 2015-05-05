package fvsl.memory.client.ui;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainPage extends Page {
	private JTextField txtUsername;
	private MainPageViewModel viewModel;
	private JTextField txtPassword;
	private JButton btnCreateLobby;
	private JButton btnJoinLobby;
	private JList<Lobby> listLobbies;
	
	public MainPage(){
		super();
	}
	
	@Override
	protected void loadComponents() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel createLobbyPanel = new JPanel();
		panel.add(createLobbyPanel);
		createLobbyPanel.setLayout(new BoxLayout(createLobbyPanel, BoxLayout.Y_AXIS));
		
		txtUsername = new JTextField();
		createLobbyPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		btnCreateLobby = new JButton("Create Lobby");
		createLobbyPanel.add(btnCreateLobby);
		
		JPanel joinLobbyPanel = new JPanel();
		panel.add(joinLobbyPanel);
		joinLobbyPanel.setLayout(new BoxLayout(joinLobbyPanel, BoxLayout.Y_AXIS));
		
		listLobbies = new JList<Lobby>();
		joinLobbyPanel.add(listLobbies);
		
		txtPassword = new JTextField();
		joinLobbyPanel.add(txtPassword);
		txtPassword.setColumns(10);
		
		btnJoinLobby = new JButton("Join Lobby");
		joinLobbyPanel.add(btnJoinLobby);
	}
	
	@Override
	protected void loadData(){
		//Spostare su controller (parzialmente) + richiesta al server
		viewModel = new MainPageViewModel();
		viewModel.setLobbies(MockFactory.getMockLobbiesList());
		viewModel.setPlayerName("Anonymous Player"); //Default user name
	}
	
	@Override
	protected void populateViews(){
		listLobbies.setListData(viewModel.getLobbies().toArray(new Lobby[MockFactory.getMockLobbiesList().size()]));
		listLobbies.setSelectedIndex(0);
		txtUsername.setText(viewModel.getPlayerName());
		txtUsername.selectAll();
	}
	
	@Override
	protected void setUpListeners(){
		txtUsername.getDocument().addDocumentListener(new DocumentListener() { 
			  public void changedUpdate(DocumentEvent e) {
				  	notifyProperty(); 
				  } 
				  public void removeUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				  public void insertUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				 
				  public void notifyProperty() { 
				     viewModel.setPlayerName(txtUsername.getText());
				  } 
		});
		
		txtPassword.getDocument().addDocumentListener(new DocumentListener() { 
			  public void changedUpdate(DocumentEvent e) {
				  	notifyProperty(); 
				  } 
				  public void removeUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				  public void insertUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				 
				  public void notifyProperty() { 
				     viewModel.setPassword(txtPassword.getText());
				  } 
		});
		
		listLobbies.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listLobbies.getValueIsAdjusting()==false){
					System.out.println("Selezione lista cambiata " + listLobbies.getSelectedValue());
					viewModel.setSelectedLobby(listLobbies.getSelectedValue());
				}
			}
		});
		
		btnJoinLobby.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                joinLobby(viewModel.getPlayerName(), viewModel.getSelectedLobby(), viewModel.getPassword());
            }
        });
	}

	protected void joinLobby(String playerName, Lobby selectedLobby, String password) {
		//Richiesta a server + spostare in un controller
		System.out.println(playerName + " accede alla lobby " + selectedLobby.getName());
	}

	/**
	 * @return the txtUsername
	 */
	public JTextField getTxtUsername() {
		return txtUsername;
	}

	/**
	 * @return the viewModel
	 */
	public MainPageViewModel getViewModel() {
		return viewModel;
	}

}
