package fvsl.memory.client.ui;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fvsl.memory.client.ui.Request.LobbyJoiningResult;

public class MainPageView extends Page {
	
	private static final Logger log = Logger.getLogger( MainPageView.class.getName() );
	
	private MainPageModel model;
	private MainPageController controller;
	
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JButton btnCreateLobby;
	private JButton btnJoinLobby;
	private JList<Lobby> listLobbies;
	
	public MainPageView(){
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
		txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtUsername.getPreferredSize().height) );
		
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
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtPassword.getPreferredSize().height) );
		
		btnJoinLobby = new JButton("Join Lobby");
		joinLobbyPanel.add(btnJoinLobby);
	}
	
	@Override
	protected void loadData(){
		//Spostare su controller (parzialmente) + richiesta al server
		model = new MainPageModel();
		controller = new MainPageController();
		model.setLobbies(controller.getLobbiesFromServer(model.getPlayerName()));
		//model.setLobbies(new ArrayList<Lobby>());
		model.setPlayerName("Anonymous Player"); //Default user name
	}
	
	@Override
	public void populateViews(){
		listLobbies.setListData(model.getLobbies().toArray(new Lobby[MockFactory.getMockLobbiesList().size()]));
		listLobbies.setSelectedIndex(0);
		txtUsername.setText(model.getPlayerName());
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
				     model.setPlayerName(txtUsername.getText());
				     Global.playerName = model.getPlayerName();
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
				     model.setPassword(txtPassword.getText());
				  } 
		});
		
		listLobbies.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listLobbies.getValueIsAdjusting()==false){
					log.log(Level.FINE, "Lobby selection changed " + listLobbies.getSelectedValue());
					model.setSelectedLobby(listLobbies.getSelectedValue());
				}
			}
		});
		
		btnJoinLobby.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                attemptToJoinLobby();
            }
        });
		
		btnCreateLobby.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                loadCreateLobbyPage();
            }
        });
	}
	
	protected void loadCreateLobbyPage() {
		controller.loadCreateLobbyPage();
	}

	protected void attemptToJoinLobby(){
		LobbyJoiningResult result = controller.requestLobbyJoining(model.getPlayerName(), model.getSelectedLobby(), model.getPassword());
		if (result == LobbyJoiningResult.Accepted){
			log.log(Level.INFO, model.getPlayerName() + " successfully joined the lobby " + model.getSelectedLobby(), model.getSelectedLobby());
			controller.loadLobbyPage(model.getSelectedLobby());
		} else {
			log.log(Level.WARNING, model.getPlayerName() + " was unable to join the lobby " + model.getSelectedLobby() + ": " + result.toString(), result);
			JOptionPane.showMessageDialog(container, "Unable to join lobby: " + result.toString());
		}
	}

	/**
	 * @return the txtUsername
	 */
	public JTextField getTxtUsername() {
		return txtUsername;
	}

	/**
	 * @return the model
	 */
	public MainPageModel getModel() {
		return model;
	}

	/**
	 * @return the controller
	 */
	public MainPageController getController() {
		return controller;
	}

}
