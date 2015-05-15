package fvsl.memory.client.pages.main;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fvsl.memory.client.entities.Lobby;
import fvsl.memory.client.entities.Player;
import fvsl.memory.client.entities.Request.LobbyJoiningResult;
import fvsl.memory.client.pages.Page;
import fvsl.memory.client.shell.Global;
import fvsl.memory.client.sockets.GUIUpdaterRunnable;

public class MainPageView extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161665227369448294L;

	private static final Logger log = Logger.getLogger( MainPageView.class.getName() );

	private MainPageModel model;
	private MainPageController controller;

	private GUIUpdaterRunnable updater;
	private Thread updaterThread;

	private JTextField txtUsername;
	private JTextField txtPassword;
	private JButton btnCreateLobby;
	private JButton btnJoinLobby;
	private JList<Lobby> listLobbies;

	public MainPageView(){
		super();

		updater = new GUIUpdaterRunnable(this);
		updaterThread = new Thread(updater);
		updaterThread.start();

	}

	@Override
	protected void loadComponents() {
		setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JPanel createLobbyPanel = new JPanel();
		panel.add(createLobbyPanel);
		createLobbyPanel.setLayout(new BoxLayout(createLobbyPanel,BoxLayout.PAGE_AXIS));
		txtUsername = new JTextField();
		createLobbyPanel.add(Box.createRigidArea(new Dimension(55,25)));
		createLobbyPanel.add(txtUsername);
		createLobbyPanel.add(Box.createRigidArea(new Dimension(55,400)));
		txtUsername.setColumns(10);
		txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtUsername.getPreferredSize().height) );

		btnCreateLobby = new JButton("Create Lobby");
		createLobbyPanel.add(btnCreateLobby);
		btnCreateLobby.setAlignmentX(CENTER_ALIGNMENT);
		//btnCreateLobby.setBounds(100,400,150,50);
		JPanel joinLobbyPanel = new JPanel();
		panel.add(joinLobbyPanel);
		joinLobbyPanel.setLayout(new BoxLayout(joinLobbyPanel,BoxLayout.PAGE_AXIS));
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55,25)));
		listLobbies = new JList<Lobby>();
		joinLobbyPanel.add(listLobbies);
		listLobbies.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));
		txtPassword = new JTextField();
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55,5)));
		joinLobbyPanel.add(txtPassword);
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55,50)));
		txtPassword.setBounds(100, 340, 270, 25);
		txtPassword.setColumns(10);
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtPassword.getPreferredSize().height) );

		btnJoinLobby = new JButton("Join Lobby");
		joinLobbyPanel.add(btnJoinLobby);
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55,100)));
	}

	@Override
	protected void loadData(){
		//Spostare su controller (parzialmente) + richiesta al server
		model = new MainPageModel();
		controller = new MainPageController();
		model.setPlayer(new Player("Anonymous player")); //Default user name
		model.setLobbies(controller.getLobbiesFromServer(model.getPlayer()));
	}

	public void updateLobbies(){
		System.out.println("Updating lobbies");
		model.setLobbies(controller.getLobbiesFromServer(model.getPlayer()));
		listLobbies.setListData(model.getLobbies().toArray(new Lobby[model.getLobbies().size()]));
	}

	@Override
	public void populateViews(){
		listLobbies.setListData(model.getLobbies().toArray(new Lobby[model.getLobbies().size()]));
		listLobbies.setSelectedIndex(0);
		txtUsername.setText(model.getPlayer().getName());
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
				model.getPlayer().setName(txtUsername.getText());
				Global.player = model.getPlayer();
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
				if (model.getSelectedLobby() != null){
					attemptToJoinLobby();
				}
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
		LobbyJoiningResult result = controller.requestLobbyJoining(model.getPlayer(), model.getSelectedLobby(), model.getPassword());
		if (result == LobbyJoiningResult.Accepted){
			log.log(Level.INFO, model.getPlayer().getName() + " successfully joined the lobby " + model.getSelectedLobby(), model.getSelectedLobby());
			controller.loadLobbyPage(model.getSelectedLobby());
		} else {
			log.log(Level.WARNING, model.getPlayer().getName() + " was unable to join the lobby " + model.getSelectedLobby() + ": " + result.toString(), result);
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

	@Override
	protected void bufferize(Object o) {
		// TODO Auto-generated method stub

	}

	/*
	protected void finalize() throws Throwable {
		  super.finalize();

		  updaterThread.interrupt();
	}
	 */
}
