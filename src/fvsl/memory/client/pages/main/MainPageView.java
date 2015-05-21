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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.shell.Application;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.entities.Request.LobbyJoiningResult;
import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * The Class MainPageView.
 */
public class MainPageView extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161665227369448294L;

	private static final Logger log = Logger.getLogger(MainPageView.class.getName());

	private MainPageModel model;
	private MainPageController controller;

	private JTextField txtUsername;
	private JTextField txtPassword;
	private JButton btnCreateLobby;
	private JButton btnJoinLobby;
	private JList<Lobby> listLobbies;

	/**
	 * Instantiates a new main page view.
	 */
	public MainPageView() {
		super();
		controller = new MainPageController();
	}

	@Override
	protected void loadComponents() {
		setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout());
		JPanel createLobbyPanel = new JPanel();
		panel.add(createLobbyPanel, BorderLayout.WEST);
		createLobbyPanel.setLayout(new BoxLayout(createLobbyPanel, BoxLayout.PAGE_AXIS));
		JPanel buttonPanel = new JPanel();
		txtUsername = new JTextField();
		createLobbyPanel.add(Box.createRigidArea(new Dimension(55, 25)));
		createLobbyPanel.add(txtUsername);
		createLobbyPanel.add(Box.createRigidArea(new Dimension(55, 500)));
		// txtUsername.setColumns(10);
		// txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE,100) );
		txtUsername.setBorder((new TitledBorder(new EtchedBorder(), StringResources.namePl.toString())));
		btnCreateLobby = new JButton(StringResources.createLo.toString());
		buttonPanel.setLayout(new BorderLayout());
		panel.add(buttonPanel, BorderLayout.SOUTH);
		// createLobbyPanel.add(btnCreateLobby);
		btnCreateLobby.setAlignmentX(CENTER_ALIGNMENT);
		// btnCreateLobby.setBounds(100,400,150,50);
		JPanel joinLobbyPanel = new JPanel();
		panel.add(joinLobbyPanel, BorderLayout.CENTER);
		joinLobbyPanel.setLayout(new BoxLayout(joinLobbyPanel, BoxLayout.PAGE_AXIS));
		// joinLobbyPanel.setBorder(new TitledBorder ( new EtchedBorder (),
		// "Lobbies" ));
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55, 25)));
		listLobbies = new JList<Lobby>();
		listLobbies.setBorder((new TitledBorder(new EtchedBorder(), StringResources.lobbies.toString())));
		joinLobbyPanel.add(listLobbies);
		listLobbies.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		txtPassword = new JTextField();
		txtPassword.setBorder((new TitledBorder(new EtchedBorder(), StringResources.psw.toString())));
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55, 5)));
		joinLobbyPanel.add(txtPassword);
		joinLobbyPanel.add(Box.createRigidArea(new Dimension(55, 50)));
		txtPassword.setBounds(100, 340, 270, 25);
		txtPassword.setColumns(10);
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtPassword.getPreferredSize().height));

		btnJoinLobby = new JButton(StringResources.joinLo.toString());
		buttonPanel.add(btnCreateLobby, BorderLayout.WEST);

		buttonPanel.add(btnJoinLobby, BorderLayout.CENTER);
		// joinLobbyPanel.add(btnJoinLobby);
		// joinLobbyPanel.add(Box.createRigidArea(new Dimension(55,100)));
	}

	@Override
	protected void loadData() {
		// Spostare su controller (parzialmente) + richiesta al server
		model = new MainPageModel();
		model.setPlayer(Application.player);
		if (model.getPlayer() == null) {
			model.setPlayer(new Player(StringResources.anonymousPl.toString())); // Default
																					// user
			// name
		}
		model.setLobbies(controller.getLobbiesFromServer(model.getPlayer()));
	}

	/**
	 * Update lobbies.
	 */
	public void updateLobbies() {
		System.out.println(StringResources.updateLo.toString());
		model.setLobbies(controller.getLobbiesFromServer(model.getPlayer()));
		listLobbies.setListData(model.getLobbies().toArray(new Lobby[model.getLobbies().size()]));
	}

	/* (non-Javadoc)
	 * @see fvsl.memory.client.pages.Page#populateViews()
	 */
	@Override
	public void populateViews() {
		listLobbies.setListData(model.getLobbies().toArray(new Lobby[model.getLobbies().size()]));
		listLobbies.setSelectedIndex(0);
		txtUsername.setText(model.getPlayer().getName());
		txtUsername.selectAll();
	}

	@Override
	protected void setUpListeners() {
		txtUsername.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				notifyProperty();
			}

			public void notifyProperty() {
				model.getPlayer().setName(txtUsername.getText());
				Application.player = model.getPlayer();
			}
		});

		txtPassword.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				notifyProperty();
			}

			@Override
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
				if (listLobbies.getValueIsAdjusting() == false) {
					log.log(Level.FINE, StringResources.updateLo.toString() + listLobbies.getSelectedValue());
					model.setSelectedLobby(listLobbies.getSelectedValue());
				}
			}
		});

		btnJoinLobby.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getSelectedLobby() != null) {
					attemptToJoinLobby();
				}
			}
		});

		btnCreateLobby.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadCreateLobbyPage();
			}
		});
	}

	protected void loadCreateLobbyPage() {
		controller.loadCreateLobbyPage();
	}

	protected void attemptToJoinLobby() {
		LobbyJoiningResult result = controller.requestLobbyJoining(model.getPlayer(), model.getSelectedLobby(), model.getPassword());
		if (result == LobbyJoiningResult.Accepted) {
			log.log(Level.INFO, model.getPlayer().getName() + StringResources.succjoinLo.toString() + model.getSelectedLobby(), model.getSelectedLobby());
			controller.loadLobbyPage(model.getSelectedLobby());
		} else {
			log.log(Level.WARNING, model.getPlayer().getName() + StringResources.unJoinLo.toString() + model.getSelectedLobby() + ": " + result.toString(), result);
			JOptionPane.showMessageDialog(container, StringResources.unJoinLo.toString() + result.toString());
		}
	}

	/**
	 * Gets the txt username.
	 *
	 * @return the txt username
	 */
	public JTextField getTxtUsername() {
		return txtUsername;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public MainPageModel getModel() {
		return model;
	}

	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	public MainPageController getController() {
		return controller;
	}

	@Override
	protected void bufferize(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onExit() {
		// TODO Auto-generated method stub

	}

}
